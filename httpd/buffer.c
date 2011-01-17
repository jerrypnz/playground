#include "buffer.h"
#include <stdio.h>
#include <stdlib.h>

static  buf_node_t  *_alloc_buf_node(const int buf_size) {
    buf_node_t      *buf;
    void            *mem;

    // Allocate the buffer node and the data buffer at the same time
    mem = malloc(sizeof(buf_node_t) + buf_size);
    buf = (buf_node_t*) mem;
    if (buf == NULL) {
        fprintf(stderr, "Error allocating memory for buffer queue");
        return NULL;
    }

    buf->data = ((char*) mem + sizeof(buf_node_t));
    buf->buf_size = buf_size;
    buf->data_offset = 0;
    buf->data_size = 0;
    buf->_next = NULL;

    return buf;
}


buf_queue_t  *buf_create(size_t buf_size, int init_capacity) {
    int     i;
    buf_queue_t     *q;
    buf_node_t      *buf;

    q = (buf_queue_t*) malloc(sizeof(buf_queue_t));
    if (q == NULL) {
        fprintf(stderr, "Error allocating memory for buffer queue");
        return NULL;
    }

    q->buf_size = buf_size;
    q->len = 0;
    q->_head = NULL;
    q->_tail = NULL;
    q->_free_list = NULL;

    if (init_capacity > 0) {

        for (i = 0; i < init_capacity; i++) {
            buf = _alloc_buf_node(q->buf_size);
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
    int i;
    buf_node_t  *buf, *it;

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


buf_node_t   *buf_alloc_new(buf_queue_t *buf_q) {
    buf_node_t      *buf;

    // Use the nodes from free list first.
    if (buf_q->_free_list != NULL) {
        buf = buf_q->_free_list;
        buf_q->_free_list = buf_q->_free_list->_next;
        buf->data_offset = 0;
        buf->data_size = 0;
        buf->_next = NULL;
    } else {
        buf = _alloc_buf_node(buf_q->buf_size);
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


buf_node_t   *buf_get_head(buf_queue_t *buf_q) {
    return buf_q->_head;
}


void    buf_remove_head(buf_queue_t *buf_q) {
    buf_node_t      *buf;

    if (buf_q->_head == NULL)
        return;

    // Remove from the buffer list
    buf = buf_q->_head;
    buf_q->_head = buf->_next;

    // Insert the node to the free list for reusing
    buf->_next = buf_q->_free_list;
    buf_q->_free_list = buf;
}

