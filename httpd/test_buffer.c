#include "buffer.h"
#include <stdio.h>
#include <string.h>
#include <assert.h>

buf_queue_t     *buf_q;
size_t          buf_size;
int             init_cap;

const char      *test_data[] = {
    "foobar",
    "hey, rock you!",
    "woooooohoooooooooo"
};

void assert_free_list_count(int expected_count);

void test_init_buffer() {
    assert(buf_size > 0);
    assert(init_cap >= 0);
    buf_q = buf_create(buf_size, init_cap);

    assert(buf_q != NULL);
    assert(buf_q->buf_size == buf_size);
    assert(buf_q->len == 0);
    assert(buf_q->_head == NULL);
    assert(buf_q->_tail == NULL);
    if (init_cap != 0) {
        assert(buf_q->_free_list != NULL);
        assert_free_list_count(init_cap);
    }
}


void test_allocate_new() {
    buf_node_t  *buf;
    int         i;

    for (i = 0; i < 3; i++) {
        buf = buf_alloc_new(buf_q);

        assert(buf != NULL);
        assert(buf->buf_size == buf_size);
        assert(buf->_next == NULL);
        assert(buf->data != NULL);
        assert(buf->data_offset == 0);
        assert(buf->data_size == 0);

        assert(buf_q->len == i + 1);

        strncpy((char*)buf->data, test_data[i], buf->buf_size);
        buf->data_size = strlen(test_data[i]) + 1;
    }

    assert_free_list_count(0);
}


void test_get_and_delete_buffer() {
    buf_node_t  *buf;
    int         i;
    size_t      str_len;

    for (i = 0; i < 3; i++) {
        str_len = strlen(test_data[i]);

        buf = buf_get_head(buf_q);

        assert(buf != NULL);
        assert(buf->buf_size == buf_size);
        assert(buf->data != NULL);
        assert(buf->data_offset == 0);
        assert(buf->data_size == str_len + 1); // Should count the '\0' at the end of the string
        assert(strncmp(test_data[i], (char*)buf->data, buf->buf_size) == 0);

        buf_remove_head(buf_q);
    }
    assert_free_list_count(3);
}


void assert_free_list_count(int expected_count) {
    int         count = 0;
    buf_node_t  *buf;

    for (buf = buf_q->_free_list; buf != NULL; buf = buf->_next)
        count++;
    printf("Expected free list size: %d, actual size: %d\n", expected_count, count);
    assert(count ==  expected_count);
}


int main(int argc, const char *argv[])
{
    buf_q = NULL;
    buf_size = 256;
    init_cap = 2;
    test_init_buffer();
    test_allocate_new();
    test_get_and_delete_buffer();
    return 0;
}
