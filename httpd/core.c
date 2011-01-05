#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "core.h"

#define MAX_MSG_SIZE 512

int worker_main(worker_ctx_t *wk_ctx) {
    char    msg[MAX_MSG_SIZE];
    ssize_t msg_len = 0;
    pid_t   pid = getpid();

    msg[0] = '\0';

    printf("Worker process running, pid:%u, socket fd:%d\n", pid, wk_ctx->ctl_sock_fd);
    for(;;) {
        msg_len = read(wk_ctx->ctl_sock_fd, msg, MAX_MSG_SIZE);
        if (msg_len < 0) {
            perror("Error receiving data from socket");
            exit(-1);
        } else if(msg_len == 0) {
            printf("Socket closed, exiting\n");
            close(wk_ctx->ctl_sock_fd);
            exit(0);
        } 

        msg[msg_len] = '\0';

        printf("Message received. Size: %d\n", msg_len);
        printf("Message: %s\n", msg);
    }
}


int worker_init(worker_ctx_t *wk_ctx, server_ctx_t *server_ctx, int ctl_sock_fd) {
    memset(wk_ctx, 0, sizeof(worker_ctx_t));
    wk_ctx->ctl_sock_fd = ctl_sock_fd;
    wk_ctx->server_ctx = server_ctx;
    return 0;
}


int server_init(server_ctx_t *server_ctx, server_setting_t *setting) {
    pid_t           pid;
    int             tmp_fds[2];
    int             i;
    worker_t        *wk_list, *wk_iter;
    worker_ctx_t    wk_ctx;

    memset(server_ctx, 0, sizeof(server_ctx_t));
    server_ctx->setting = setting;
    server_ctx->worker_size = setting->initial_worker_count;

    wk_list = (worker_t*) malloc(server_ctx->worker_size * sizeof(worker_t));
    if (wk_list == NULL) {
        perror("Error allcating memory");
        return -1;
    }
    memset(wk_list, 0, server_ctx->worker_size * sizeof(worker_t));

    server_ctx->worker_list = wk_list;

    for (i = 0; i < server_ctx->worker_size; i++) {
        if (socketpair(AF_UNIX, SOCK_STREAM, 0, tmp_fds) < 0) {
            perror("Error opening sockets");
            return -1;
        }

        if ((pid = fork()) < 0) {
            perror("error forking worker process");
            return -1;
        } else if (pid > 0) {
            wk_iter = server_ctx->worker_list + i;
            wk_iter->pid = pid;
            wk_iter->ctl_sock_fd = tmp_fds[0];
            close(tmp_fds[1]);
        } else {  /* child process */
            worker_init(&wk_ctx, server_ctx, tmp_fds[1]);
            close(tmp_fds[0]);
            worker_main(&wk_ctx); 
        }

    }

    return 0;
}

int server_event_cycle(server_ctx_t *server_ctx) {
    char    input_buffer[MAX_MSG_SIZE];
    char    *msg = 0;
    int     idx;
    int     msg_len;
    int     read_len;

    input_buffer[0] = '\0';
    for(;;) {
        input_buffer[0] = '\0';
        fgets(input_buffer, MAX_MSG_SIZE, stdin);
        if (input_buffer[0] == '\0') {
            printf("Exiting...\n");
            break;
        }

        read_len = strlen(input_buffer);
        input_buffer[read_len - 1] = '\0';
        if(read_len-1 < 3) {
            printf("Correct msg format: <worker_id> <msg>\n");
            continue;
        }

        input_buffer[1] = '\0';
        idx = atoi(input_buffer);
        if (idx < 0 || idx >= server_ctx->worker_size) {
            printf("Invalid process index: %d\n", idx);
            continue;
        }
        msg = input_buffer + 2;
        msg_len = strlen(msg);
        printf("Sending message to process:%d, fd:%d, msg:%s, msg size:%d\n", 
                server_ctx->worker_list[idx].pid, 
                server_ctx->worker_list[idx].ctl_sock_fd, 
                msg, 
                msg_len);
        write(server_ctx->worker_list[idx].ctl_sock_fd, msg, msg_len);
    }

    return 0;
}

int server_destroy(server_ctx_t *server_ctx) {
    int exit_status, i;

    for (i = 0; i < server_ctx->worker_size; i++) {
        close(server_ctx->worker_list[i].ctl_sock_fd);
    }

    for (i = 0; i < server_ctx->worker_size; i++) {
        waitpid(server_ctx->worker_list[i].pid, &exit_status, 0);
        printf("worker[pid=%d] exit status: %d\n", server_ctx->worker_list[i].pid, WEXITSTATUS(exit_status));
    }

    free(server_ctx->worker_list);

    return 0;
}


