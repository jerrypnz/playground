#include "foo.h"


void apply_func(void* list, func fn, int len, size_t sz)
{
    for (int i = 0; i < len; i++) {
        void* elem = ((char*) list) + i*sz;
        fn(elem);
    }
}
