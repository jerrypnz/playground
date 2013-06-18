#include "ioloop.h"
#include <stdio.h>
#include <string.h>
#include <errno.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/epoll.h>
#include <netinet/in.h>

static void connection_handler(ioloop_t *loop, int fd, unsigned int events, void *args);
static void echo_handler(ioloop_t *loop, int fd, unsigned int events, void *args);
static void send_welcome_message(ioloop_t *loop, void* args);

static void connection_handler(ioloop_t *loop, int listen_fd, unsigned int events, void *args) {
    socklen_t   addr_len;
    int         conn_fd;
    struct sockaddr_in  remo_addr;

    // -------- Accepting connection ----------------------------
    printf("Accepting new connection...\n");
    addr_len = sizeof(struct sockaddr_in);
    conn_fd = accept(listen_fd, (struct sockaddr*) &remo_addr, &addr_len);
    printf("Connection fd: %d...\n", conn_fd);
    if (conn_fd == -1) {
        perror("Error accepting new connection");
        return;
    }

    ioloop_add_handler(loop, conn_fd, EPOLLIN, echo_handler, NULL);
    ioloop_add_callback(loop, send_welcome_message, (void*)conn_fd);
}

static void send_welcome_message(ioloop_t *loop, void* args) {
    int fd = (int)args;
    char msg[] = "Welcome to echo server powered by libioloop!\n";
    write(fd, msg, sizeof(msg));
}


static void echo_handler(ioloop_t *loop, int fd, unsigned int events, void *args) {
    char    buffer[1024];
    int     nread;
    if (events & EPOLLIN) {
        nread = read(fd, buffer, 1024);
        if (nread == 0) {
            printf("Connection closed or error condition occurs: %d\n", fd);
            ioloop_remove_handler(loop, fd);
            close(fd);
            return;
        } else if (nread < 0 && errno != EAGAIN && errno != EWOULDBLOCK) {
            perror("Error reading");
            return;
        }
        buffer[nread] = '\0';
        printf("Read from client: %s", buffer);
        write(fd, buffer, nread);
    }
}

int main(int argc, char *argv[]) {
    ioloop_t               *loop;
    int                     listen_fd;
    struct sockaddr_in      addr;

    loop = ioloop_create(100);
    if (loop == NULL) {
        fprintf(stderr, "Error initializing ioloop");
        return -1;
    }

    // ---------- Create and bind listen socket fd --------------
    listen_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (listen_fd == -1) {
        perror("Error creating socket");
        return -1;
    }

    bzero(&addr, sizeof(struct sockaddr_in));
    addr.sin_addr.s_addr = INADDR_ANY;
    addr.sin_port = htons(9999);

    if (bind(listen_fd, (struct sockaddr *)&addr, sizeof(struct sockaddr_in)) == -1) {
        perror("Error binding address");
        return -1;
    }

    // ------------ Start listening ------------------------------
    if (listen(listen_fd, 10) == -1) {
        perror("Error listening");
        return -1;
    }

    ioloop_add_handler(loop, listen_fd, EPOLLIN, connection_handler, NULL);
    ioloop_start(loop);
    return 0;
}
