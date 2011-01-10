#include "server.h"

struct foo_handler {
    handler_t   vtbl;
    int         data;
};

struct bar_handler {
    handler_t   vtbl;
    char        data[128];
};

int main(int argc, const char *argv[])
{
    
    return 0;
}
