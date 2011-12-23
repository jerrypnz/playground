#include "buffer.h"
#include <unistd.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MIN(a, b) ((a) < (b) ? (a) : (b))

typedef unsigned char byte_t;

struct _buffer {
    byte_t     *data;
    size_t      size;
    size_t      capacity;
    int         head;
    int         tail;
};

__inline__ static void _do_write(buffer_t *buf, byte_t *data, size_t len);
__inline__ static void _do_read_to(buffer_t *buf, byte_t *target, size_t len);
__inline__ static void _do_consume(buffer_t *buf, size_t len, consumer_func func, void *args);
__inline__ static size_t _do_read_to_fd(buffer_t *buf, int to_fd, size_t len);
__inline__ static size_t _do_write_from_fd(buffer_t *buf, int from_fd, size_t len);


buffer_t *buffer_create(size_t size) {
    buffer_t    *buf;
    void        *mem;
    size_t      mem_sz;

    mem_sz = size + sizeof(buffer_t);
    mem = malloc(mem_sz);
    if (mem == NULL) {
        perror("Error allocating buffer memory");
        return NULL;
    }
    buf = (buffer_t*) mem;
    buf->capacity = size;
    buf->size = 0;
    buf->head = 0;
    buf->tail = 0;
    buf->data = (byte_t*) mem + sizeof(buffer_t);

    return buf;
}


int buffer_destroy(buffer_t *buf) {
    if (buf == NULL)
        return -1;
    free(buf);
    return 0;
}


int buffer_write(buffer_t *buf, void *data, size_t len) {
    size_t  cap;
    size_t  write_len;
    byte_t  *_data = (byte_t*) data;

    cap = buf->capacity - buf->size;
    if (len > cap) {
        return -1;
    }

    write_len = MIN(len, buf->capacity - buf->tail);
    _do_write(buf, _data, write_len);

    _data += write_len;
    write_len = len - write_len;
    if (write_len > 0) {
        _do_write(buf, _data, write_len);
    }
    return 0;
}


size_t buffer_write_from_fd(buffer_t *buf, int fd, size_t len) {
    size_t  write_len, n, total = 0;

    len = MIN(len, buf->capacity - buf->size);
    write_len = MIN(len, buf->capacity - buf->tail);
    n = _do_write_from_fd(buf, fd, write_len);

    // No more to read, just return
    if (n < write_len) {
        return n;
    }

    write_len = len - write_len;
    total += n;
    if (write_len > 0) {
        n = _do_write_from_fd(buf, fd, write_len);
        total += n;
    }
    return total;
}


size_t buffer_read_to(buffer_t *buf, size_t len, void *target, size_t capacity) {
    size_t      read_len, total = 0;
    byte_t      *_target = (byte_t*) target;

    len = MIN(buf->size, len);
    read_len = MIN(len, buf->capacity - buf->head);
    read_len = MIN(read_len, capacity);
    _do_read_to(buf, _target, read_len);

    total += read_len;
    _target += read_len;
    capacity -= read_len;
    read_len = MIN(capacity, len - read_len);

    if (read_len > 0) {
        _do_read_to(buf, _target, read_len);
        total += read_len;
    }

    return total;
}


size_t buffer_consume(buffer_t *buf, size_t len, consumer_func cb, void *args) {
    size_t      read_len, total = 0;

    len = MIN(buf->size, len);
    read_len = MIN(len, buf->capacity - buf->head);
    _do_consume(buf, read_len, cb, args);

    total += read_len;
    read_len = len - read_len;

    if (read_len > 0) {
        _do_consume(buf, read_len, cb, args);
        total += read_len;
    }

    return total;
}


size_t buffer_read_to_fd(buffer_t *buf, size_t len, int to_fd) {
    size_t     read_len, total;
    size_t     n;

    len = MIN(buf->size, len);
    total = 0;
    read_len = MIN(len, buf->capacity - buf->head);
    n = _do_read_to_fd(buf, to_fd, read_len);

    // If the first part is not written completely, we
    // just return and don't try the next part.
    if (n < read_len) {
        return n;
    }

    total += read_len;
    read_len = len - read_len;

    if (read_len > 0) {
        n = _do_read_to_fd(buf, to_fd, read_len);
        total += n;
    }

    return total;
}


__inline__ static void _do_write(buffer_t *buf, byte_t *data, size_t len) {
    memcpy(buf->data + buf->tail, data, len);
    buf->size += len;
    buf->tail = (buf->tail + len) % buf->capacity;
}

__inline__ static void _do_read_to(buffer_t *buf, byte_t *target, size_t len) {
    memcpy(target, buf->data + buf->head, len);
    buf->size -= len;
    buf->head = (buf->head + len) % buf->capacity;
}

__inline__ static void _do_consume(buffer_t *buf, size_t len, consumer_func func, void *args) {
    func(buf->data + buf->head, len, args);
    buf->size -= len;
    buf->head = (buf->head + len) % buf->capacity;
}

__inline__ static size_t _do_read_to_fd(buffer_t *buf, int to_fd, size_t len) {
    ssize_t     n, total = 0;
    while (len > 0) {
        n = write(to_fd, buf->data + buf->head, len);
        if (n < 0) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                break;
            }
        }
        len -= n;
        total += n;
        buf->size -= len;
        buf->head = (buf->head + len) % buf->capacity;
    }
    return total;
}


__inline__ static size_t _do_write_from_fd(buffer_t *buf, int from_fd, size_t len) {
    ssize_t     n, total = 0;
    while(total < len) {
        n = read(from_fd, buf->data + buf->tail, len);
        if (n < 0) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                break;
            }
        } else if (n == 0) {
            break;
        }
        total += n;
        buf->size += n;
        buf->tail = (buf->tail + len) % buf->capacity;
    }
    return total;
}
