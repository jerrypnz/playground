#include <stdio.h>
#include "core.h"

int main(int argc, const char *argv[])
{
    server_ctx_t        server_ctx;
    server_setting_t    setting;

    setting.initial_worker_count = 5;
    setting.port = 8080;

    server_init(&server_ctx, &setting);
    server_event_cycle(&server_ctx);
    server_destroy(&server_ctx);
    return 0;
}
