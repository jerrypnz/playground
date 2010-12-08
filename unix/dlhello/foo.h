#ifndef __FOO_H
#define __FOO_H 1 

#include <stdio.h>

typedef void (*func)(void* arg);

void apply_func(void* list, func fn, int len, size_t sz);

#endif
