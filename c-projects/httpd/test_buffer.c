#include "buffer.h"
#include <stdio.h>
#include <string.h>
#include <assert.h>

buf_queue_t     *buf_q;
size_t          page_size;
int             init_cap;

const char      *test_data[] = {
    "foobar",
    "hey, rock you!",
    "woooooohoooooooooo"
};

void assert_free_list_count(int expected_count);

void test_init_buffer() {
    assert(page_size > 0);
    assert(init_cap >= 0);
    buf_q = buf_create(page_size, init_cap);

    assert(buf_q != NULL);
    assert(buf_q->page_size == page_size);
    assert(buf_q->len == 0);
    assert(buf_q->_head == NULL);
    assert(buf_q->_tail == NULL);
    if (init_cap != 0) {
        assert(buf_q->_free_list != NULL);
        assert_free_list_count(init_cap);
    }
}


void test_allocate_new() {
    buf_page_t  *buf;
    int         i;

    for (i = 0; i < 3; i++) {
        buf = buf_alloc_new(buf_q);

        assert(buf != NULL);
        assert(buf->page_size == page_size);
        assert(buf->_next == NULL);
        assert(buf->data != NULL);
        assert(buf->data_offset == 0);
        assert(buf->data_size == 0);

        assert(buf_q->len == i + 1);

        strncpy((char*)buf->data, test_data[i], buf->page_size);
        buf->data_size = strlen(test_data[i]) + 1;
    }

    assert_free_list_count(0);
}


void test_get_buffer_tail() {
    buf_page_t  *buf;
    size_t      str_len;

    str_len = strlen(test_data[2]);
    buf = buf_get_tail(buf_q);
    assert(buf != NULL);
    assert(buf->page_size == page_size);
    assert(buf->data != NULL);
    assert(buf->data_offset == 0);
    assert(buf->data_size == str_len + 1); // Should count the '\0' at the end of the string
    assert(strncmp(test_data[2], (char*)buf->data, buf->page_size) == 0);
}

void test_get_and_delete_buffer() {
    buf_page_t  *buf;
    int         i;
    size_t      str_len;

    for (i = 0; i < 3; i++) {
        str_len = strlen(test_data[i]);

        buf = buf_get_head(buf_q);

        assert(buf != NULL);
        assert(buf->page_size == page_size);
        assert(buf->data != NULL);
        assert(buf->data_offset == 0);
        assert(buf->data_size == str_len + 1); // Should count the '\0' at the end of the string
        assert(strncmp(test_data[i], (char*)buf->data, buf->page_size) == 0);

        buf_remove_head(buf_q);
    }
    assert_free_list_count(3);
}


void test_compact_buffer() {
    buf_page_t  *buf;
    buf = buf_alloc_new(buf_q);

    assert(buf != NULL);
    assert(buf->page_size == page_size);
    assert(buf->_next == NULL);
    assert(buf->data != NULL);
    assert(buf->data_offset == 0);
    assert(buf->data_size == 0);

    buf->data_offset = 100;
    strncpy((char*)buf->data + 100, test_data[0], buf->page_size - 100);
    buf->data_size = strlen(test_data[0]) + 1;

    buf_compact_page(buf);
    assert(buf->data_offset == 0);
    assert(strncmp(test_data[0], (char*)buf->data, buf->page_size) == 0);
}


void assert_free_list_count(int expected_count) {
    int         count = 0;
    buf_page_t  *buf;

    for (buf = buf_q->_free_list; buf != NULL; buf = buf->_next)
        count++;
    printf("Expected free list size: %d, actual size: %d\n", expected_count, count);
    assert(count ==  expected_count);
}


int main(int argc, const char *argv[])
{
    buf_q = NULL;
    page_size = 256;
    init_cap = 2;
    test_init_buffer();
    test_allocate_new();
    test_get_buffer_tail();
    test_get_and_delete_buffer();
    printf("Testing compact buffer\n");
    test_compact_buffer();
    buf_destroy(buf_q);
    return 0;
}
