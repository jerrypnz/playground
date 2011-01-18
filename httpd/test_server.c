#include "server.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>

typedef struct {
    handler_t   vtbl;
    int         data;
} foo_handler;

typedef struct {
    handler_t   vtbl;
    char        data[64];
} bar_handler;

int foo_handler_handle(handler_t *handler, const char *path, request_t *req, response_t *resp) {
    foo_handler  *this = (foo_handler*) handler;
    this->data = 0xCAFEBABE;
    printf("foo handler finished.\n");
    return 0;
}

foo_handler* create_foo_handler() {
    foo_handler *handler = (foo_handler*) malloc(sizeof(foo_handler));
    handler->vtbl.handle = foo_handler_handle;
    return handler;
}

int bar_handler_handle(handler_t *handler, const char *path, request_t *req, response_t *resp) {
    bar_handler *this = (bar_handler*) handler;
    strncpy(this->data, "0xCAFEBABE", 64);
    printf("bar handler finished.\n");
    return 0;
}

bar_handler* create_bar_handler() {
    bar_handler *handler = (bar_handler*) malloc(sizeof(bar_handler));
    handler->vtbl.handle = bar_handler_handle;
    return handler;
}

server_t    *server;
foo_handler *h1;
bar_handler *h2;

void init_server() {
    server = server_create(8081);
    h1 = create_foo_handler();
    h2 = create_bar_handler();
    server_add_handler(server, (handler_t*)h1);
    server_add_handler(server, (handler_t*)h2);
    assert(server->_handler_head != NULL);
    assert(server->_handler_head->handler_obj == (handler_t*)h1);
    assert(server->_handler_tail != NULL);
    assert(server->_handler_tail->handler_obj == (handler_t*)h2);
}

void call_handlers() {
    struct _handler_chain  *cur;
    cur = server->_handler_head;
    for(; cur != NULL; cur = cur->next) {
        cur->handler_obj->handle(cur->handler_obj, "test", NULL, NULL);
    }
    printf("foo handler data: 0x%X\n", h1->data);
    printf("bar handler data: %s\n", h2->data);
    assert(h1->data == 0xCAFEBABE);
    assert(strcmp("0xCAFEBABE", h2->data) == 0);
    server_destroy(server);
}

int main(int argc, const char *argv[]) {
    init_server();
    call_handlers();
    return 0;
}
