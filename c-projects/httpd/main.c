#include "server.h"
#include "stacktrace.h"
#include <stdio.h>

int main(int argc, const char *argv[]) {
    server_t    *server;
    server = server_create(8080);

    print_stacktrace_on_error();

    if (server == NULL) {
        fprintf(stderr, "Error detecting during server creation");
        return -1;
    }

    if (server_main(server) < 0) {
        fprintf(stderr, "Error detecting during server running");
    }

    server_destroy(server);
    return 0;
}
