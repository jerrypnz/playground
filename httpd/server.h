#ifndef __SERVER_H
#define __SERVER_H


#define MAX_CONNECTIONS     2048

struct _server;
struct _handler;
struct _handler_chain;
struct _connection;
struct _request;
struct _response;

enum _server_state {
    SERV_STAT_RUNNING,
    SERV_STAT_STOPPING,
    SERV_STAT_COMPLETE
};

struct _server {
    /* Configuration fields */
    unsigned short            port;
    struct _handler_chain     *handler;
    struct _handler_chain     *_handler_tail;

    /* Fields for the module implementation */
    enum _server_state  _state;
    int                 _listen_fd;
    int                 _epoll_fd;
    struct _connection  *_connections[MAX_CONNECTIONS];
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
    struct _request     *req;
    struct _response    *resp;
    struct _server      *server;
};

typedef int (*fn_handler_init)    (struct _handler *handler);
typedef int (*fn_handler_destroy) (struct _handler *handler);
typedef int (*fn_handler_handle)  (struct _handler *handler, const char* path, 
        struct _request *req, struct _response *resp);

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

typedef struct _server     server_t;
typedef struct _handler    handler_t;
typedef struct _request    request_t;
typedef struct _response   response_t;
typedef struct _connection connection_t;

int add_handler(server_t *server, handler_t handler);
int server_main(server_t *server);

#define is_server_running(server) ((server)->state == SERV_STAT_RUNNING)

#endif /* __SERVER_H */
