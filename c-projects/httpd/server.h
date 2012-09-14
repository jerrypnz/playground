#ifndef __SERVER_H
#define __SERVER_H

#include <netinet/in.h>
#include "http.h"
#include "buffer.h"
#include "location.h"

#define MAX_CONNECTIONS     2048

typedef struct _server          server_t;
typedef struct _connection      connection_t;
typedef struct _phase           phase_t;
typedef struct _phase_ctx       phase_ctx_t;

typedef enum _server_state {
    SERV_STAT_RUNNING = 1,
    SERV_STAT_STOPPING,
    SERV_STAT_COMPLETE
} server_state;

typedef enum _conn_phase {
    PHASE_NULL = 0, // Used for indicating the end of the phase chain
    PHASE_IDLE = 1,
    PHASE_PARSE,
    PHASE_FIND_LOC,
    PHASE_HANDLE,
    PHASE_ERROR,
    PHASE_WRITE,
    PHASE_CLEANUP
} conn_phase;


struct _phase {
    int             (*handle) (server_t *server, connection_t *conn);
    conn_phase      next_phase; 
};

struct _phase_ctx {
    conn_phase  current_phase;

    /*** 
     * Only used for saving the context within a single phase. 
     * It will be set to NULL when shifting to next phase, 
     * so the phase handler is responsable for cleaning up.
     ***/
    void        *data; 
};

struct _server {
    /* Configuration fields */
    unsigned short            port;

    /* The sites */
    site_t                   *site;

    /* Fields for the module implementation */
    int                      _listen_fd;
    int                      _epoll_fd;
    server_state             _state;

    /* Linked list for active connections */
    struct _connection        *_conn_head;
    struct _connection        *_conn_tail;
};

struct _connection {
    int                 sock_fd;
    struct sockaddr_in  remo_addr;
    int                 keep_alive;

    phase_ctx_t         phase_ctx;

    http_parser_t       parser;
    server_t            *server;
    request_t           req;

    buf_queue_t         *read_buf_q;
    buf_queue_t         *write_buf_q;

    /* Only for server module internal usage */
    struct _connection  *next;
    struct _connection  *prev;
};

extern phase_t      std_phases[];

server_t*   server_create(unsigned short port);
int         server_destroy(server_t *server);
int         server_main(server_t *server);

int         conn_read(connection_t *conn);
int         conn_write_to_buffer(connection_t *conn, const void* data, size_t data_len);
int         conn_do_write(connection_t *conn);

int         conn_toggle_write_event(connection_t *conn, unsigned short on);

#define is_server_running(server) ((server)->state == SERV_STAT_RUNNING)

#endif /* __SERVER_H */
