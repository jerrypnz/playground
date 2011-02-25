#ifndef __BUFFER_H
#define __BUFFER_H

#include <stddef.h>


struct _buf_page {
    void                    *data;
    int                     data_offset;    // Starting offset of the effective data
    size_t                  data_size;      // Size of the effective data
    size_t                  page_size;       // Total size of the buffer

    struct _buf_page        *_next;
};


struct _buf_queue {
    size_t               page_size;
    int                  len;
    struct _buf_page     *_head;
    struct _buf_page     *_tail;

    struct _buf_page     *_free_list;
};

typedef struct _buf_page    buf_page_t;
typedef struct _buf_queue   buf_queue_t;

buf_queue_t  *buf_create(size_t page_size, int init_capacity);
int           buf_destroy(buf_queue_t *buf_queue);

buf_page_t   *buf_alloc_new(buf_queue_t *buf_queue);           

buf_page_t   *buf_get_head(buf_queue_t *buf_queue);
buf_page_t   *buf_get_tail(buf_queue_t *buf_queue);
void          buf_remove_head(buf_queue_t *buf_queue);
void          buf_compact_page(buf_page_t *page);

#endif /* end of include guard: __BUFFER_H */
