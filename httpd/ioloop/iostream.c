#include "iostream.h"
#include "ioloop.h"
#include "buffer.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <sys/epoll.h>
#include <unistd.h>

enum READ_OP_TYPES {
    READ_BYTES = 1,
    READ_UNTIL = 2
};

enum STREAM_STATE {
    NORMAL = 1,
    CLOSED = 2
};

struct _iostream {
    int         fd;
    int         state;
    ioloop_t    *ioloop;

    read_handler    read_callback;
    read_handler    stream_callback;
    write_handler   write_callback;
    error_handler   error_callback;
    close_handler   close_callback;

    int         read_type;
    size_t      read_bytes;
    char        *read_delimiter;

    unsigned int    events;

    buffer_t    *read_buf;
    size_t      read_buf_size;
    buffer_t    *write_buf;
    size_t      write_buf_size;

    int         sendfile_fd;
};


#define is_reading(stream) ((stream)->read_callback != NULL)
#define is_writing(stream) ((stream)->write_callback != NULL)
#define is_closed(stream)  ((stream)->state == CLOSED)

#define check_reading(stream)  \
    if (is_reading(stream)) {  \
        return -1;             \
    }

#define check_writing(stream)  \
    if (is_writing(stream)) {  \
        return -1;             \
    }

static void _handle_io_events(ioloop_t *loop, int fd, unsigned int events, void *args);
static void _handle_error(iostream_t *stream, unsigned int events);
static void _handle_read(iostream_t *stream);
static void _handle_write(iostream_t *stream);
static size_t _read_to_buffer(iostream_t *stream);
static int _read_from_buffer(iostream_t *stream);
static int _add_event(iostream_t *stream, unsigned int events);
static void _stream_callback_wrapper(void *data, size_t len, void *args);

iostream_t *iostream_create(ioloop_t *loop, int sockfd, size_t read_buf_size, size_t write_buf_size) {
    iostream_t  *stream;
    buffer_t    *in_buf, *out_buf;

    stream = (iostream_t*) calloc(1, sizeof(iostream_t));
    if (stream == NULL) {
        perror("Error allocating memory for IO stream");
        goto error;
    }
    bzero(stream, sizeof(iostream_t));

    in_buf = buffer_create(read_buf_size);
    if (in_buf == NULL ) {
        perror("Error creating read buffer");
        goto error;
    }
    out_buf = buffer_create(write_buf_size);
    if (out_buf == NULL) {
        perror("Error creating write buffer");
        goto error;
    }

    stream->events = 0;
    stream->write_buf = out_buf;
    stream->read_buf = in_buf;
    stream->fd = sockfd;
    stream->state = NORMAL;
    stream->ioloop = loop;
    stream->read_callback = NULL;
    stream->write_callback = NULL;
    stream->close_callback = NULL;
    stream->error_callback = NULL;
    stream->sendfile_fd = -1;

    if (_add_event(stream, EPOLLERR) < 0) {
        perror("Error add EPOLLERR event");
        goto error;
    }

    return stream;

error:
    buffer_destroy(in_buf);
    buffer_destroy(out_buf);
    free(stream);
    return NULL;
}

int iostream_close(iostream_t *stream) {
    ioloop_remove_handler(stream->ioloop, stream->fd);
    close(stream->fd);
    stream->close_callback(stream);
    stream->state = CLOSED;
    return 0;
}


int iostream_destroy(iostream_t *stream) {
    buffer_destroy(stream->read_buf);
    buffer_destroy(stream->write_buf);
    free(stream);
    return 0;
}


int iostream_read_bytes(iostream_t *stream, size_t sz, read_handler callback, read_handler stream_callback) {
    if (sz == 0) {
        return -1;
    }
    if (is_reading(stream)) {
        return -1;
    }
    stream->read_callback = callback;
    stream->stream_callback = stream_callback;
    stream->read_bytes = sz;
    stream->read_type = READ_BYTES;
    for (;;) {
        if (_read_from_buffer(stream)) {
            return 0;
        }
        if (is_closed(stream)) {
            return -1;
        }
        if (_read_to_buffer(stream) == 0) {
            break;
        }
    }
    _add_event(stream, EPOLLIN);
    return 0;
}


int iostream_read_until(iostream_t *stream, char *delimiter, read_handler callback) {
    if (is_reading(stream)) {
        return -1;
    }
    assert(*delimiter != '\0');
    stream->read_callback = callback;
    stream->stream_callback = NULL;
    stream->read_delimiter = delimiter;
    stream->read_type = READ_UNTIL;
    for (;;) {
        if (_read_from_buffer(stream)) {
            return 0;
        }
        if (is_closed(stream)) {
            return -1;
        }
        if (_read_to_buffer(stream) == 0) {
            break;
        }
    }
    _add_event(stream, EPOLLIN);
    return 0;
}


int iostream_write(iostream_t *stream, void *data, size_t len, write_handler callback) {
    return 0;
}


int iostream_set_error_handler(iostream_t *stream, error_handler callback) {
    stream->error_callback = callback;
    return 0;
}


int iostream_set_close_handler(iostream_t *stream, close_handler callback) {
    stream->close_callback = callback;
    return 0;
}

static void _handle_error(iostream_t *stream, unsigned int events) {
    stream->error_callback(stream, events);
    iostream_close(stream);
}

static void _handle_io_events(ioloop_t *loop, int fd, unsigned int events, void *args) {
    iostream_t      *stream = (iostream_t*) args;
    unsigned int    event;

    if (events | EPOLLIN) {
        _handle_read(stream);
    }
    if (events | EPOLLOUT) {
        _handle_write(stream);
    }
    if (events | EPOLLERR) {
        _handle_error(stream, events);
        return;
    }
    if (events | EPOLLHUP) {
        iostream_close(stream);
        return;
    }

    event = EPOLLERR;
    if (is_reading(stream)) {
        event |= EPOLLIN;
    }
    if (is_writing(stream)) {
        event |= EPOLLOUT;
    }
    stream->events = event;
    ioloop_update_handler(stream->ioloop, fd, stream->events, _handle_io_events, stream);
}

static void _handle_read(iostream_t *stream) {
    _read_to_buffer(stream);
    _read_from_buffer(stream);
}

static void _handle_write(iostream_t *stream) {

}

static int _add_event(iostream_t *stream, unsigned int event) {
    if ((stream->events & event) == 0) {
        stream->events |= event;
        return ioloop_update_handler(stream->ioloop, stream->fd, stream->events, _handle_io_events, stream);
    }
    return -1;
}

#define READ_SIZE 1024

static size_t _read_to_buffer(iostream_t *stream) {
    size_t  n;
    n = buffer_write_from_fd(stream->read_buf, stream->fd, READ_SIZE);
    if (n < 0) {
        iostream_close(stream);
        return -1;
    }
    stream->read_buf_size += n;
    return n;
}


#define LOCAL_BUFSIZE 4096

static int _read_from_buffer(iostream_t *stream) {
    int     res = 0, idx;
    char    local_buf[LOCAL_BUFSIZE];
    size_t  n;
    read_handler    callback;

    switch(stream->read_type) {
        case READ_BYTES:
            if (stream->stream_callback != NULL) {
                // Streaming mode, offer data
                buffer_consume(stream->read_buf, stream->read_bytes, _stream_callback_wrapper, stream);
                if (stream->read_bytes <= 0) {
                    callback = stream->read_callback;
                    stream->read_callback = NULL;
                    stream->read_bytes = 0;
                    // When streaming ends, call the read_callback with NULL to indicate the finish.
                    callback(stream, NULL, 0);
                    res = 1;
                }
            } else if (stream->read_buf_size >= stream->read_bytes) {
                // Normal mode, call read callback
                n = buffer_read_to(stream->read_buf, stream->read_bytes, local_buf, LOCAL_BUFSIZE);
                assert(n == stream->read_bytes);
                callback = stream->read_callback;
                stream->read_callback = NULL;
                stream->read_bytes = 0;
                stream->read_buf_size -= n;
                callback(stream, local_buf, n);
                res = 1;
            }
            break;

        case READ_UNTIL:
            idx = buffer_locate(stream->read_buf, stream->read_delimiter);
            if (idx > 0) {
                n = buffer_read_to(stream->read_buf, idx + strlen(stream->read_delimiter), local_buf, LOCAL_BUFSIZE);
                callback = stream->read_callback;
                stream->read_callback = NULL;
                stream->read_buf_size -= n;
                callback(stream, local_buf, n);
                res = 1;
            }
            break;
    }

    return res;
}


static void _stream_callback_wrapper(void *data, size_t len, void *args) {
    iostream_t  *stream = (iostream_t*) args;
    stream->read_bytes -= len;
    stream->read_buf_size -= len;
    stream->stream_callback(stream, data, len);
}

