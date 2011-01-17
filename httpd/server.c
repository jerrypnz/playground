#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <assert.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/epoll.h>
#include <netinet/in.h>

#include "server.h"
#include "http.h"

#define MAX_BACKLOG     20
#define MAX_EVENTS      20

static int _server_init(server_t *server);
static int _server_run(server_t *server);
static int _server_stop(server_t *server);
static int _accept_conn(server_t *server);

static int _dispatch_conn_event(server_t *server, struct epoll_event *ev);

static int _conn_handle_close_event(server_t *server, connection_t *conn);
static int _conn_handle_read_event(server_t *server, connection_t *conn);
static int _conn_handle_write_event(server_t *server, connection_t *conn);
static int _conn_handle(server_t *server, connection_t *conn);

static int _set_nonblocking(int fd);


server_t* server_create(unsigned short port) {
    server_t    *server;
    server = (server_t*) calloc(1, sizeof(server_t));
    if (server == NULL) {
        perror("Error allocating memory for server");
        return NULL;
    }
    server->port = port;
    return server;
}

int server_destroy(server_t *server) {
    struct _handler_chain   *handler_it, *handler_prev;
    fn_handler_destroy      destroy_func;
    
    // ------------ Destroy handlers ----------------------------
    for (handler_it = server->_handler_head; handler_it != NULL;) {
        handler_prev = handler_it;
        handler_it = handler_it->next;
        destroy_func = handler_prev->handler_obj->destroy;
        if (destroy_func != NULL) {
            destroy_func(handler_prev->handler_obj);
        }
        free(handler_prev);
    }

    // --------- Destroy server -------------------
    free(server);
    return 0;
}

int server_add_handler(server_t *server, handler_t *handler) {
    struct _handler_chain  *new_handler;

    new_handler = malloc(sizeof(struct _handler_chain));
    if (new_handler == NULL) {
        fprintf(stderr, "Error allocating memory");
        return -1;
    }

    new_handler->handler_obj = handler;
    new_handler->next = NULL;

    if (server->_handler_head == NULL) {
        server->_handler_head = new_handler;
        server->_handler_tail = new_handler;
        return 0;
    }

    server->_handler_tail->next = new_handler;
    server->_handler_tail = new_handler;
    return 0;
}


int  server_main(server_t *server) {
    int rc = 0;

    if (_server_init(server) == -1) {
        perror("Error detected during server initialization");
        return -1;
    }

    server->_state = SERV_STAT_RUNNING;

    for(;;) {
        switch (server->_state) {
            case SERV_STAT_RUNNING:
                printf("Server starts running on 0.0.0.0:%d\n", server->port);
                if (_server_run(server) == -1) {
                    perror("Error detected during server running");
                    server->_state = SERV_STAT_STOPPING;
                }
                break;

            case SERV_STAT_STOPPING:
                if (_server_stop(server) == -1) {
                    perror("Error detected when stopping server");
                }
                server->_state = SERV_STAT_COMPLETE;
                break;

            case SERV_STAT_COMPLETE:
                return rc;

            default:
                fprintf(stderr, "Invalid state: %d", server->_state);
                return -1;
        }

    }
}


static int  _server_init(server_t *server) {
    int                     listen_fd, epoll_fd;
    struct sockaddr_in      addr;
    struct epoll_event      ev;
    struct _handler_chain   *handler_it;
    fn_handler_init         init_func;

    // ---------- Create and bind listen socket fd --------------
    listen_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (listen_fd == -1) {
        perror("Error creating socket");
        return -1;
    }

    bzero(&addr, sizeof(struct sockaddr_in));
    addr.sin_addr.s_addr = INADDR_ANY;
    addr.sin_port = htons(server->port);

    if (bind(listen_fd, (struct sockaddr *)&addr, sizeof(struct sockaddr_in)) == -1) {
        perror("Error binding address");
        return -1;
    }

    // ------------ Start listening ------------------------------
    if (listen(listen_fd, MAX_BACKLOG) == -1) {
        perror("Error listening");
        return -1;
    }
    server->_listen_fd = listen_fd;

    // ------------ Init epoll ----------------------------------
    epoll_fd = epoll_create(10);
    if (epoll_fd == -1) {
        perror("Error initializing epoll");
        return -1;
    }

    ev.events = EPOLLIN;
    ev.data.fd = listen_fd;
    if (epoll_ctl(epoll_fd, EPOLL_CTL_ADD, listen_fd, &ev) == -1) {
        perror("Error adding listen socket fd to epoll");
        return -1;
    }

    server->_epoll_fd = epoll_fd;
    
    // ------------ Init handlers --------------------------------
    for(handler_it = server->_handler_head; handler_it != NULL; handler_it = handler_it->next) {
        init_func = handler_it->handler_obj->init;
        if (init_func != NULL)
            init_func(handler_it->handler_obj);
    }

    return 0;
}


static int _server_run(server_t *server) {
    int                 listen_fd, epoll_fd, nfds, i;
    struct epoll_event  events[MAX_EVENTS];

    epoll_fd = server->_epoll_fd;
    listen_fd = server->_listen_fd;

    for(;;) {
        nfds = epoll_wait(epoll_fd, events, MAX_EVENTS, -1);
        if (nfds == -1) {
            perror("Error detected during epoll waiting");
            return -1;
        }

        for (i = 0; i < nfds; i++) {
            if (events[i].data.fd == listen_fd) {
                _accept_conn(server);
            } else {
                _dispatch_conn_event(server, &events[i]);
            }
        }

    }
}


static int _server_stop(server_t *server) {
    /*TODO: Implement me*/
    return 0;
}


static int _set_nonblocking(int sockfd) {
    int opts;
    opts = fcntl(sockfd, F_GETFL);
    if (opts < 0) 
        return -1;
    opts |= O_NONBLOCK;
    if (fcntl(sockfd, F_SETFL, opts) < 0)
        return -1;
    return 0;
}


static int _accept_conn(server_t *server) {
    int                 conn_fd;
    socklen_t           addr_len;
    connection_t        *conn;
    struct epoll_event  ev;

    // -------- Allocating memory for the new connection --------
    conn = (connection_t*) calloc(1, sizeof(connection_t));
    if (conn == NULL) {
        perror("Error allocating memory for new connection");
        return -1;
    }

    // -------- Accepting connection ----------------------------
    printf("Accepting new connection...\n");
    addr_len = sizeof(struct sockaddr_in);
    conn_fd = accept(server->_listen_fd, (struct sockaddr*) &conn->remo_addr, &addr_len);
    printf("Connection fd: %d...\n", conn_fd);
    if (conn_fd == -1) {
        perror("Error accepting new connection");
        return -1;
    }

    // -------- Adding connection fd to epoll ------------------
    printf("Adding new connection to epoll...\n");
    if (_set_nonblocking(conn_fd) == -1) {
        perror("Error configuration connection to non-blocking");
        return -1;
    }

    ev.data.ptr = conn;
    ev.events = EPOLLIN | EPOLLET | EPOLLRDHUP;
    if (epoll_ctl(server->_epoll_fd, EPOLL_CTL_ADD, conn_fd, &ev) == -1) {
        perror("Error adding new connection to epoll");
        close(conn_fd);
        free(conn);
        return -1;
    }

    // -------- Init members -------------------
    conn->next = NULL;
    conn->prev = NULL;
    conn->sock_fd = conn_fd;
    conn->server = server;
    conn->state = CONN_STATE_IDLE;

    // Add the connection to the server's connection pool, using
    // fd as index.
    if (server->_conn_head == NULL) {
        server->_conn_head = conn;
        server->_conn_tail = conn;
    } else {
        server->_conn_tail->next = conn;
        conn->prev = server->_conn_tail;
        server->_conn_tail = conn;
    }

    printf("Ready to serve the client...\n");

    return 0;
}


#define EPOLL_CLOSE_EVENTS (EPOLLRDHUP | EPOLLHUP | EPOLLERR)
#define CONN_BUFF_SIZE      2048

static int _dispatch_conn_event(server_t *server, struct epoll_event *ev) {
    connection_t    *conn;

    conn = (connection_t*) ev->data.ptr;
    if (conn == NULL) {
        printf("Invalid event: connection ptr is NULL\n");
    }

    printf("New event[fd:%d, event:%d]\n", conn->sock_fd, ev->events);

    if (EPOLL_CLOSE_EVENTS & ev->events) {
        printf("Client closed connection or error detected\n");
        return _conn_handle_close_event(server, conn);
    }

    // read event and write event should not be raised at the same time.
    assert( (EPOLLIN & ev->events) ^ (EPOLLOUT & ev->events) );

    if (EPOLLIN & ev->events) {
        _conn_handle_read_event(server, conn);
    } else if (EPOLLOUT & ev->events) {
        _conn_handle_write_event(server, conn);
    }
    
    return 0;
}


static int _conn_handle_read_event(server_t *server, connection_t *conn) {
    int             nread, nconsumed;
    char            buffer[CONN_BUFF_SIZE];

    nread = read(conn->sock_fd, buffer, CONN_BUFF_SIZE);

    if (nread == 0) {
        fprintf(stderr, "Unexpected EOF\n");
    } else if (nread < 0) {
        perror("Error reading from client");
        return 1;
    }

    printf("Read %d bytes from client.\n", nread);

    return 0;
}


static int _conn_handle_write_event(server_t *server, connection_t *conn) {
    return 0;
}


static int _conn_handle_close_event(server_t *server, connection_t *conn){
    printf("Closing connection[fd: %d]\n", conn->sock_fd);

    if(epoll_ctl(server->_epoll_fd, EPOLL_CTL_DEL, conn->sock_fd, NULL)) {
        perror("Error removing fd from epoll");
    }

    close(conn->sock_fd);

    if (conn->prev != NULL)
        conn->prev->next = conn->next;
    if (conn->next != NULL) 
        conn->next->prev = conn->prev;

    free(conn);
    return 0;
}

static int _conn_handle(server_t *server, connection_t *conn) {

    switch (conn->state) {
        case CONN_STATE_IDLE:
            break;

        case CONN_STATE_PARSING:
            break;

        case CONN_STATE_HANDLING:
            break;

        case CONN_STATE_WRITING:
            break;

        case CONN_STATE_ERROR:
            break;
    }
    
}
