#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/socket.h>

#define MAX_MSG_SIZE 512
#define PROC_SIZE 5

typedef struct {
    int sock_fd;
    pid_t pid;
} worker_t;


void worker_process_main(int sock_fd)
{
    char    msg[MAX_MSG_SIZE];
    ssize_t msg_len = 0;
    pid_t   pid = getpid();

    msg[0] = '\0';

    printf("Worker process running, pid:%u, socket fd:%d\n", pid, sock_fd);
    for(;;) {
        msg_len = recv(sock_fd, msg, MAX_MSG_SIZE, 0);
        if (msg_len < 0) {
            perror("Error receiving data from socket");
            exit(-1);
        } else if(msg_len == 0) {
            printf("Socket closed, exiting\n");
            close(sock_fd);
            exit(0);
        } 

        msg[msg_len] = '\0';

        printf("Message received. Size: %d\n", msg_len);
        printf("Message: %s\n", msg);
    }
}


int main(int argc, const char *argv[])
{
    worker_t workers[MAX_MSG_SIZE];

    pid_t   pid;
    int     tmp_fds[2];
    char    input_buffer[MAX_MSG_SIZE];
    char    *msg = 0;
    int     idx;
    int     i;
    int     exit_status;
    int     msg_len;
    int     read_len;

    input_buffer[0] = '\0';

    for (i = 0; i < PROC_SIZE; i++) {
        if (socketpair(AF_UNIX, SOCK_STREAM, 0, tmp_fds) < 0) {
            perror("Error opening sockets");
            return -1;
        }
        if ((pid = fork()) < 0) {
            perror("Fork error");
            return -1;
        } else if (pid == 0) {
            close(tmp_fds[0]);
            worker_process_main(tmp_fds[1]);
            exit(0);
        } else {
            close(tmp_fds[1]);
            workers[i].sock_fd = tmp_fds[0];
            workers[i].pid = pid;
        }
    }

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
            break;
        }

        input_buffer[1] = '\0';
        idx = atoi(input_buffer);
        if (idx < 0 || idx >= MAX_MSG_SIZE) {
            printf("Invalid process index: %d\n", idx);
        }
        msg = input_buffer + 2;
        msg_len = strlen(msg);
        printf("Sending message to process:%d, fd:%d, msg:%s, msg size:%d\n", workers[idx].pid, workers[idx].sock_fd, msg, msg_len);
        send(workers[idx].sock_fd, msg, msg_len, 0);
    }

    for (i = 0; i < PROC_SIZE; i++) {
        close(workers[i].sock_fd);
    }

    for (i = 0; i < PROC_SIZE; i++) {
        waitpid(workers[i].pid, &exit_status, 0);
        printf("worker[pid=%d] exit status: %d\n", workers[i].pid, WEXITSTATUS(exit_status));
    }

    return 0;
}
