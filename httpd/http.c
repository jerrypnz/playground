#include "http.h"
#include <stdio.h>

int parser_init(const http_parser_t *parser) {
    printf("Init parser\n");
    return 0;
}

int parser_destroy(const http_parser_t *parser) {
    printf("In parser destroy\n");
    return 0;
}

int parser_reset(const http_parser_t *parser) {
    printf("In parser reset\n");
    return 0;
}

int parser_do_parse(const http_parser_t *parser, const char *data, const size_t data_len, const size_t *consumed_len) {
    printf("Parse HTTP request\n");
    return 0;
}
