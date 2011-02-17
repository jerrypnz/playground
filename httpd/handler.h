#ifndef __HANDLER_H
#define __HANDLER_H

#include "http.h"

typedef struct _handler         handler_t;
typedef struct _filter          filter_t;
typedef struct _filter_chain    filter_chain_t;

typedef int (*fn_handler_init)    (handler_t *handler);
typedef int (*fn_handler_destroy) (handler_t *handler);
typedef int (*fn_handler_handle)  (handler_t *handler, const char* path, request_t *req, response_t *resp);

/**
 * Interface for handler objects
 */
struct _handler {
    fn_handler_init     init;
    fn_handler_handle   handle;
    fn_handler_destroy  destroy;
};

enum _filter_flags {
    FILTER_INPUT = 1,
    FILTER_OUTPUT = 1 << 1
};

struct _filter {
    handler_t        *handler;
    unsigned int     flag;
    filter_t         *next;
};

struct _filter_chain {
    filter_t    *head;
    filter_t    *tail;
};

enum _filter_return_code {
    FT_CONTINUE = 0,
    FT_STOP
};

void filter_chain_add(filter_chain_t *chain, filter_t *ft);
int  filter_chain_do_filter(filter_chain_t *chain, const char* path, request_t *req, response_t *resp);

#endif /* end of include guard: __HANDLER_H */
