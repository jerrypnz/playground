#ifndef __SERVER_H
#define __SERVER_H

#include <netinet/in.h>
#include "http.h"
#include "buffer.h"
#include "location.h"

#define MAX_CONNECTIONS     2048

typedef struct _server          server_t;
typedef struct _connection      connection_t;

enum _server_state {
    SERV_STAT_RUNNING = 1,
    SERV_STAT_STOPPING,
    SERV_STAT_COMPLETE
};

struct _server {
    /* Configuration fields */
    unsigned short            port;

    /* The sites */
    site_t                   *site;

    /* Fields for the module implementation */
    int                 _listen_fd;
    int                 _epoll_fd;
    enum _server_state  _state;

    /* Linked list for active connections */
    struct _connection        *_conn_head;
    struct _connection        *_conn_tail;
};

enum _conn_state {
    CONN_STATE_IDLE,
    CONN_STATE_PARSING,
    CONN_STATE_HANDLING,
    CONN_STATE_WRITING,
    CONN_STATE_ERROR
};


struct _connection {
    int                 sock_fd;
    enum _conn_state    state;
    http_parser_t       parser;
    struct _server      *server;
    struct _request     req;
    struct _response    resp;
    struct sockaddr_in  remo_addr;

    buf_queue_t         *read_buf_q;
    buf_queue_t         *write_buf_q;

    /* Only for server module internal usage */
    struct _connection  *next;
    struct _connection  *prev;
};

server_t*   server_create(unsigned short port);
int         server_destroy(server_t *server);
int         server_main(server_t *server);

#define is_server_running(server) ((server)->state == SERV_STAT_RUNNING)

#endif /* __SERVER_H */
