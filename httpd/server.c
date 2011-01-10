#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/epoll.h>
#include <netinet/in.h>

#include "server.h"

#define MAX_BACKLOG     20
#define MAX_EVENTS      20

/* public interface of the module */
int add_handler(server_t *server, handler_fn func);
int server_main(server_t *server);

static int _server_init(server_t *server);
static int _server_run(server_t *server);
static int _server_stop(server_t *server);
static int _server_destroy(server_t *server);
static int _accept_conn(server_t *server);
static int _handle_conn_event(server_t *server, int conn_fd);


int add_handler(server_t *server, handler_t handler) {
    struct _handler_chain  *new_handler;

    new_handler = malloc(sizeof(struct _handler_chain));
    if (new_handler == NULL) {
        fprintf(stderr, "Error allocating memory");
        return -1;
    }

    new_handler->handler_obj = handler;
    new_handler->next = NULL;

    if (server->handler == NULL) {
        server->handler = new_handler;
        server->_handler_tail = new_handler;
        return 0;
    }

    server->_handler_tail->next = new_handler;
    server->_handler_tail = new_handler;
}


int  server_main(server_t *server) {
    int rc;

    for(;;) {
        switch (server->_state) {
            case SERV_STAT_RUNNING:
                rc = _server_run(server);
                break;

            case SERV_STAT_STOPPING:
                rc = _server_stop(server);
                break;

            case SERV_STAT_COMPLETE:
                rc = _server_destroy(server);
                return;
        }

        if (rc == -1) {
            fprintf(stderr, "Fatal error detecting during server running, stopping server");
        }
    }
}


static int  _server_init(server_t *server) {
    int                 listen_fd, epoll_fd;
    struct sockaddr_in  addr;
    struct epoll_event  ev;

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

    // ------------ Start listening ------------------------------
    if (listen(listen_fd, MAX_BACKLOG) == -1) {
        perror("Error listening");
        return -1;
    }

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
                _handle_conn_event(server, events[i].data.fd);
            }
        }

    }
}


