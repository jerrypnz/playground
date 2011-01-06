#include "core.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netinet/in.h>

enum _http_methods {
    HTTP_GET, HTTP_POST, HTTP_PUT, HTTP_DELETE
};

enum _std_http_req_header_fields {
    REQHDR_ACCEPT = 0,
    REQHDR_ACCEPT_CHARSET,
    REQHDR_ACCEPT_ENCODING,
    REQHDR_ACCEPT_LANGUAGE,
    REQHDR_AUTHORIZATION,
    REQHDR_EXPECT,
    REQHDR_FROM,
    REQHDR_HOST,
    REQHDR_IF_MATCH,
    REQHDR_IF_MODIFIED_SINCE,
    REQHDR_IF_NONE_MATCH,
    REQHDR_IF_RANGE,
    REQHDR_IF_UNMODIFIED_SINCE,
    REQHDR_MAX_FORWARDS,
    REQHDR_PROXY_AUTHORIZATION,
    REQHDR_RANGE,
    REQHDR_REFERER,
    REQHDR_TE,
    REQHDR_USER_AGENT 
};

const char  *_std_http_req_header_field_str[] = {
    "Accept",
    "Accept-Charset",
    "Accept-Encoding",
    "Accept-Language",
    "Authorization",
    "Expect",
    "From",
    "Host",
    "If-Match",
    "If-Modified-Since",
    "If-None-Match",
    "If-Range",
    "If-Unmodified-Since",
    "Max-Forwards",
    "Proxy-Authorization",
    "Range",
    "Referer",
    "TE",
    "User-Agent"
};

enum _http_trans_state {
    TX_STAT_REQ_HEADER,
    TX_STAT_REQ_BODY,
    TX_STAT_PROCESSING,
    TX_STAT_RESP_WRITING
}

typedef int (*stat_handler)(http_trans_t *tx, void *data, size_t len);

typedef struct _http_header_t {
    char    *value;
} http_header_t;

typedef struct _http_request_t {
    int     method;
    char    *path;
    http_header_t   *hd;
} http_request_t;

typedef struct _http_conn_t {
    int                 sockfd;
    int                 state;
    struct sockaddr     *remoteaddr;
    http_request_t      *request;
} http_trans_t;
