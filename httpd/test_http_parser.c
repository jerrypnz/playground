#include "http.h"
#include <assert.h>
#include <string.h>
#include <stdio.h>


char* test_request = 
    "GET /home/hello.do?id=1001&name=hello HTTP/1.1\r\n"
    "Host: www.javaeye.com\r\n"
    "Connection: keep-alive\r\n"
    "Referer: http://www.javaeye.com/\r\n"
    "Cache-Control: max-age=0\r\n"
    "If-Modified-Since: Sat, 15 Dec 2007 04:04:14 GMT\r\n"
    "If-None-Match: \"3922745954\"\r\n"
    "Accept: */*\r\n"
    "User-Agent: Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.15 (KHTML, like Gecko) Chrome/10.0.612.1 Safari/534.15\r\n"
    "Accept-Encoding: gzip,deflate,sdch\r\n"
    "Accept-Language: zh-CN,zh;q=0.8\r\n"
    "Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n"
    "Cookie: lzstat_uv=39255935923757216993|1146165@1270080@1632275@1145010@0; remember_me=yes; login_token=MTAyMjExXzJkZjY4ZGIyNDIzZTdjMTE4YmM5OTU0OTQ2MTU0N2Fh%0A; _javaeye3_session_=BAh7BzoMdXNlcl9pZGkDQ48BOg9zZXNzaW9uX2lkIiVjMDdlNjJmZGJiNTFhZjhhYmU5Yjk4NjE1Y2ZjODBhOQ%3D%3D--b9945c8e50a5eba75e75c92b2c92e5a3a86f1000\r\n\r\n";

const http_header_t     expected_headers[] = {
    {"Host", "www.javaeye.com"},
    {"Connection", "keep-alive"},
    {"Referer", "http://www.javaeye.com/"},
    {"Cache-Control", "max-age=0"},
    {"If-Modified-Since", "Sat, 15 Dec 2007 04:04:14 GMT"},
    {"If-None-Match", "\"3922745954\""},
    {"Accept", "*/*"},
    {"User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.15 (KHTML, like Gecko) Chrome/10.0.612.1 Safari/534.15"},
    {"Accept-Encoding", "gzip,deflate,sdch"},
    {"Accept-Language", "zh-CN,zh;q=0.8"},
    {"Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3"},
    {"Cookie", "lzstat_uv=39255935923757216993|1146165@1270080@1632275@1145010@0; remember_me=yes; login_token=MTAyMjExXzJkZjY4ZGIyNDIzZTdjMTE4YmM5OTU0OTQ2MTU0N2Fh%0A; _javaeye3_session_=BAh7BzoMdXNlcl9pZGkDQ48BOg9zZXNzaW9uX2lkIiVjMDdlNjJmZGJiNTFhZjhhYmU5Yjk4NjE1Y2ZjODBhOQ%3D%3D--b9945c8e50a5eba75e75c92b2c92e5a3a86f1000"}
};

http_parser_t   parser;

void dump_parser();
void assert_headers();

void test_parse_once() {
    int req_size, consumed_size, rc;

    printf("\n\nTesting parsing all in one time\n");
    req_size = strlen(test_request);
    rc = parser_do_header(&parser, test_request, req_size, &consumed_size);
    printf("Request size: %d, consumed size: %d, return status: %d\n", req_size, consumed_size, rc);
    dump_parser();
    assert(rc == STATUS_COMPLETE);
    assert(consumed_size == req_size);
    assert_headers();
}

void test_parse_multiple_times() {
    int     req_size, consumed_size, rc;
    int     part1_size, part2_size, part3_size;
    char    *data;

    printf("\n\nTesting parsing all in multiple time\n");
    data = test_request;
    req_size = strlen(data);

    part1_size = req_size / 3;
    part2_size = req_size / 3 + 9;
    part3_size = req_size - part1_size - part2_size;

    // First time
    rc = parser_do_header(&parser, data, part1_size, &consumed_size);
    printf("First Time: Request size: %d, consumed size: %d, return status: %d\n", req_size, consumed_size, rc);
    dump_parser();
    assert(rc == STATUS_CONTINUE);
    assert(consumed_size == part1_size);

    data += consumed_size;

    // Second time
    rc = parser_do_header(&parser, data, part2_size, &consumed_size);
    printf("Second Time: Request size: %d, consumed size: %d, return status: %d\n", req_size, consumed_size, rc);
    dump_parser();
    assert(rc == STATUS_CONTINUE);
    assert(consumed_size == part2_size);

    data += consumed_size;

    // Third time
    rc = parser_do_header(&parser, data, part3_size, &consumed_size);
    printf("Third Time: Request size: %d, consumed size: %d, return status: %d\n", req_size, consumed_size, rc);

    dump_parser();
    assert(rc == STATUS_COMPLETE);
    assert(consumed_size == part3_size);
    assert_headers();
}

void dump_parser() {
    int i;

    printf("--------- Parser State -----------------------\n");
    printf("Method: %s\n", parser.method);
    printf("Path: %s\n", parser.path);
    printf("Query String: %s\n", parser.query_str);
    printf("HTTP Version: %s\n", parser.http_version);
    printf("Header count: %d\n", parser.header_count);
    printf("Headers: \n");
    printf("------------\n");

    for (i = 0; i < parser.header_count; i++) {
        printf("\r%s: %s\n", parser.headers[i].name, parser.headers[i].value);
    }

    printf("----------------------------------------------\n");
}

void assert_headers() {
    int expected_header_size, i;

    expected_header_size = sizeof(expected_headers) / sizeof(http_header_t);

    printf("Expecting %d headers\n", expected_header_size);
    assert(expected_header_size == parser.header_count);
    printf("Checked\n");

    for (i = 0; i < parser.header_count; i++) {
        assert(strcmp(expected_headers[i].name,  parser.headers[i].name) == 0);
        assert(strcmp(expected_headers[i].value, parser.headers[i].value) == 0);
    }
}

int main(int argc, const char *argv[])
{
    parser_init(&parser);
    test_parse_once();
    parser_reset(&parser);
    test_parse_multiple_times();
    return 0;
}
