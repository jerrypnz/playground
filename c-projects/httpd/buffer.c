#include "buffer.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static  buf_page_t  *_alloc_buf_page(const size_t page_size) {
    buf_page_t      *buf;
    void            *mem;

    // Allocate the buffer node and the data buffer at the same time
    mem = malloc(sizeof(buf_page_t) + page_size);
    if (mem == NULL) {
        fprintf(stderr, "Error allocating memory for buffer queue");
        return NULL;
    }

    buf = (buf_page_t*) mem;

    buf->data = ((char*) mem + sizeof(buf_page_t));
    buf->page_size = page_size;
    buf->data_offset = 0;
    buf->data_size = 0;
    buf->_next = NULL;

    return buf;
}


buf_queue_t  *buf_create(size_t page_size, int init_capacity) {
    int     i;
    buf_queue_t     *q;
    buf_page_t      *buf;

    q = (buf_queue_t*) malloc(sizeof(buf_queue_t));
    if (q == NULL) {
        fprintf(stderr, "Error allocating memory for buffer queue");
        return NULL;
    }

    q->page_size = page_size;
    q->len = 0;
    q->_head = NULL;
    q->_tail = NULL;
    q->_free_list = NULL;

    if (init_capacity > 0) {

        for (i = 0; i < init_capacity; i++) {
            buf = _alloc_buf_page(q->page_size);
            if (buf == NULL)
                goto end;

            if (q->_free_list != NULL) {
                buf->_next = q->_free_list;
                q->_free_list = buf;
            } else {
                q->_free_list = buf;
            }
        }
    }

end:
    return q;
}


int  buf_destroy(buf_queue_t *buf_q) {
    buf_page_t  *buf, *it;

    for (it = buf_q->_head; it != NULL; ) {
        buf = it;
        it = it->_next;
        free(buf);
    }

    for (it = buf_q->_free_list; it != NULL;) {
        buf = it;
        it = it->_next;
        free(buf);
    }

    free(buf_q);

    return 0;
}


buf_page_t   *buf_alloc_new(buf_queue_t *buf_q) {
    buf_page_t      *buf;

    // Use the nodes from free list first.
    if (buf_q->_free_list != NULL) {
        buf = buf_q->_free_list;
        buf_q->_free_list = buf->_next;
        buf->data_offset = 0;
        buf->data_size = 0;
        buf->_next = NULL;
    } else {
        buf = _alloc_buf_page(buf_q->page_size);
        if (buf == NULL) {
            return NULL;
        }
    }

    if (buf_q->_head == NULL) {
        buf_q->_head = buf;
        buf_q->_tail = buf;
    } else {
        buf_q->_tail->_next = buf;
        buf_q->_tail =  buf;
    }

    buf_q->len++;

    return buf;
}


buf_page_t   *buf_get_head(buf_queue_t *buf_q) {
    return buf_q->_head;
}

buf_page_t   *buf_get_tail(buf_queue_t *buf_q) {
    return buf_q->_tail;
}

void    buf_remove_head(buf_queue_t *buf_q) {
    buf_page_t      *buf;

    if (buf_q->_head == NULL)
        return;

    // Remove from the buffer list
    buf = buf_q->_head;
    buf_q->_head = buf->_next;
    if (buf == buf_q->_tail) { // The last page is removed, so we must also set _tail to NULL
        buf_q->_tail = NULL;
    }

    // Insert the node to the free list for reusing
    buf->_next = buf_q->_free_list;
    buf_q->_free_list = buf;
}

void  buf_compact_page(buf_page_t *page) {
    char    *data_start;
    if (page->data_offset == 0) {
        // No need to compact
        return;
    }

    data_start = (char*)page->data + page->data_offset;
    memmove(page->data, data_start, page->data_size);
    page->data_offset = 0;
}
