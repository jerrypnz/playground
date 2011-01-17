#ifndef __HTTP_H
#define __HTTP_H

#define REQUEST_BUFFER_SIZE     4096
#define MAX_HEADER_SIZE         25

#include <stddef.h>

enum _http_methods {
    HTTP_METHOD_EXTENDED = 0,
    HTTP_METHOD_GET = 1, 
    HTTP_METHOD_POST, 
    HTTP_METHOD_PUT, 
    HTTP_METHOD_DELETE,
    HTTP_METHOD_HEAD,
    HTTP_METHOD_TRACE,
    HTTP_METHOD_CONNECT,
    HTTP_METHOD_OPTIONS
};

enum _http_versions {
    HTTP_VERSION_UNKNOWN = -1,
    HTTP_VERSION_0_9 = 1,
    HTTP_VERSION_1_0,
    HTTP_VERSION_1_1
};

enum _parser_state {
    PARSER_STATE_BAD_REQUEST = -1,
    PARSER_STATE_COMPLETE = 0,
    PARSER_STATE_METHOD,
    PARSER_STATE_PATH,
    PARSER_STATE_QUERY_STR,
    PARSER_STATE_VERSION,
    PARSER_STATE_HEADER_NAME,
    PARSER_STATE_HEADER_COLON,
    PARSER_STATE_HEADER_VALUE,
    PARSER_STATE_HEADER_CR,
    PARSER_STATE_HEADER_LF,
    PARSER_STATE_HEADER_COMPLETE_CR,
    PARSER_STATE_HEADER_COMPLETE_LF,
    PARSER_STATE_HEADER_COMPLETE,
    PARSER_STATE_BODY
};

enum _return_status {
    STATUS_ERROR = -1,
    STATUS_COMPLETE = 0,
    STATUS_CONTINUE = 1
};

struct _http_header {
    char    *name;
    char    *value;
};

struct _http_parser {
    char                    *path;
    char                    *query_str;
    char                    *method;
    char                    *http_version;
    int                     content_len;

    struct _http_header     headers[MAX_HEADER_SIZE];
    int                     header_count;

    // All the fields' value is stored in this buffer 
    char                    _buffer[REQUEST_BUFFER_SIZE];
    int                     _buf_idx;

    // Current token, it points to an offset to _buffer
    char                    *_cur_tok;

    // Parser state
    enum _parser_state      _state;
};


typedef struct _http_parser     http_parser_t;
typedef struct _http_header     http_header_t;

int parser_init(http_parser_t *parser);
int parser_destroy(http_parser_t *parser);
int parser_reset(http_parser_t *parser);
int parser_do_header(http_parser_t *parser, const char *data, const size_t data_len, size_t *consumed_len);


#endif /* end of include guard: __HTTP_H */
