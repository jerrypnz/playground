#ifndef __HTTP_H
#define __HTTP_H

#define REQUEST_BUFFER_SIZE     1024

enum _http_methods {
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
    PARSER_STATE_HEADER_VALUE,
    PARSER_STATE_HEADER_CR,
    PARSER_STATE_HREADER_LF,
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
    char                    *host;
    enum _http_methods      method;
    enum _http_versions     version;
    int                     content_len;
    struct _http_header     *headers;

    int                     _buf_idx;
    char                    _buffer[REQUEST_BUFFER_SIZE];
    enum _parser_state      _state;
};


typedef struct _http_parser     http_parser_t;
typedef struct _http_header     http_header_t;

int parser_init(const http_parser_t *parser);
int parser_destroy(const http_parser_t *parser);
int parser_reset(const http_parser_t *parser);
int parser_do_parse(const http_parser_t *parser, const char *data, const size_t data_len, const size_t *consumed_len);


#endif /* end of include guard: __HTTP_H */
