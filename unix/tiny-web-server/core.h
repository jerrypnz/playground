#ifndef __CORE_H

#define __CORE_H

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>

typedef struct _server_setting_t {
    int         port;
    size_t      initial_worker_count;
} server_setting_t;

typedef struct _worker_t {
    pid_t       pid;
    int         ctl_sock_fd;
} worker_t;

typedef struct _server_ctx_t {
    int         listen_fd;
    size_t      worker_size;
    worker_t    *worker_list;
    server_setting_t    *setting;
} server_ctx_t;

typedef struct _worker_ctx_t {
    int             ctl_sock_fd;
    server_ctx_t    *server_ctx;
} worker_ctx_t;

typedef struct _worker_ctl_msg_t {
    int         cmd;
    int         data;
} worker_ctl_msg_t;


int worker_init(worker_ctx_t *wk_ctx, server_ctx_t *server_ctx, int ctl_sock_fd);
int worker_main(worker_ctx_t *ctx);

int server_init(server_ctx_t *ctx, server_setting_t *settings);
int server_event_cycle(server_ctx_t *ctx);
int server_destroy(server_ctx_t *ctx);

#endif /* end of include guard: __CORE_H */
