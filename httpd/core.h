#ifndef __CORE_H
#define __CORE_H


#define MAX_CONNECTIONS     2048;

struct _server;
struct _handler;
struct _connection;
struct _request;
struct _response;

enum _server_state {
    RUNNING,
    STOPPING,
    COMPLETE
};

struct _server {
    int                 listen_fd;
    int                 epoll_fd;
    unsigned short      port;
    _server_state       state;
    struct _handler     *handler;
    struct _handler     *_handler_tail;
    struct _connection  *connections[MAX_CONNECTIONS];
};

struct _request {
    char            *path;
    connection_t    *connection;
};

struct _response {
    
};

enum _conn_state {
    IDLE,
    PARSING,
    HANDLING,
    WRITING,
    COMPLETE,
    ERROR
};

struct _connection {
    int                 sock_fd;
    _conn_state         state;
    struct _request     *req;
    struct _response    *resp;
    struct _server      *server;
};

typedef int (*handler_fn)(struct _handler *handler, const char* path, struct _request *req, struct _response *resp);

struct _handler {
    handler_fn          handle;
    struct _handler     *next;
};


typedef _server     server_t;
typedef _handler    handler_t;
typedef _request    request_t;
typedef _response   response_t;
typedef _connection connection_t;

int  add_handler(server_t *server, handler_fn func);

int  server_main(server_t *server);
int  server_init(server_t *server);
int  server_run(server_t *server);
int  server_stop(server_t *server);
int  server_destroy(server_t *server);

int  epoll_dispatch(server_t *server);
int  accept_conn(server_t *server);
int  handle_conn_event(server_t *server, int fd);

#define is_server_running(server) ((server)->state == RUNNING)

#endif /* __CORE_H */
