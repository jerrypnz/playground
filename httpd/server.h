#ifndef __SERVER_H
#define __SERVER_H

#include <netinet/in.h>

#define MAX_CONNECTIONS     2048

struct _server;
struct _handler;
struct _handler_chain;
struct _connection;
struct _request;
struct _response;

enum _server_state {
    SERV_STAT_RUNNING = 1,
    SERV_STAT_STOPPING,
    SERV_STAT_COMPLETE
};

struct _server {
    /* Configuration fields */
    unsigned short            port;

    /* Fields for the module implementation */
    int                 _listen_fd;
    int                 _epoll_fd;
    enum _server_state  _state;

    /* Linked list for handlers */
    struct _handler_chain     *_handler_head;
    struct _handler_chain     *_handler_tail;

    /* Linked list for active connections */
    struct _connection        *_conn_head;
    struct _connection        *_conn_tail;
};

struct _request {
    char                *path;
    struct _connection  *connection;
};

struct _response {
    
};

enum _conn_state {
    CONN_STATE_IDLE,
    CONN_STATE_PARSING,
    CONN_STATE_HANDLING,
    CONN_STATE_WRITING,
    CONN_STATE_COMPLETE,
    CONN_STATE_ERROR
};

struct _connection {
    int                 sock_fd;
    enum _conn_state    state;
    struct _server      *server;
    struct _request     req;
    struct _response    resp;
    struct sockaddr_in  remo_addr;

    /* Only for server module internal usage */
    struct _connection  *next;
    struct _connection  *prev;
};

typedef int (*fn_handler_init)    (const struct _handler *handler);
typedef int (*fn_handler_destroy) (const struct _handler *handler);
typedef int (*fn_handler_handle)  (const struct _handler *handler, const char* path, 
        const struct _request *req, const struct _response *resp);

/**
 * Interface for handler objects
 */
struct _handler {
    fn_handler_init     init;
    fn_handler_handle   handle;
    fn_handler_destroy  destroy;
};

struct _handler_chain {
    struct _handler           *handler_obj;
    struct _handler_chain     *next;
};

typedef struct _server      server_t;
typedef struct _handler     handler_t;
typedef struct _request     request_t;
typedef struct _response    response_t;
typedef struct _connection  connection_t;

server_t*   server_create(unsigned short port);
int         server_destroy(server_t *server);
int         server_main(server_t *server);
int         server_add_handler(server_t *server, handler_t *handler);

#define is_server_running(server) ((server)->state == SERV_STAT_RUNNING)

#endif /* __SERVER_H */
