#include "server.h"
#include "buffer.h"
#include "stacktrace.h"
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

const char* test_data = 
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
    "Cookie: lzstat_uv=39255935923757216993|1146165@1270080@1632275@1145010@0; "
    "remember_me=yes; login_token=MTAyMjExXzJkZjY4ZGIyNDIzZTdjMTE4YmM5OTU0OTQ2MTU0N2Fh%0A; "
    "_javaeye3_session_=BAh7BzoMdXNlcl9pZGkDQ48BOg9zZXNzaW9uX2lkIiVjMDdlNjJmZGJiNTFhZjhhYmU5Yjk4NjE1Y2ZjODBhOQ%3D%3D"
    "--b9945c8e50a5eba75e75c92b2c92e5a3a86f1000\r\n\r\n";


connection_t    *conn = NULL;

// Basic idea is to write the test_data to a temp file and then open it, 
// use the fd as a "fake" TCP socket fd.
void init_conn_for_read() {
    int     fd;
    size_t  datalen;
    size_t  pg_sz;
    char    tmpfile[64] = "/tmp/test_server_XXXXXX";

    fd = mkstemp(tmpfile);
    if (fd == -1) {
        fprintf(stderr, "Error creating temp file");
        exit(1);
    }

    datalen = strlen(test_data);
    write(fd, test_data, datalen);
    lseek(fd, 0, SEEK_SET);

    pg_sz = datalen / 3 + 10;

    conn = malloc(sizeof(connection_t));
    if (conn == NULL) {
        perror("Error allocating memory");
        exit(1);
    }
    bzero(conn, sizeof(connection_t));
    conn->sock_fd = fd;
    conn->read_buf_q = buf_create(pg_sz, 1);
}

void init_conn_for_write() {
    size_t  datalen;
    size_t  pg_sz;
    datalen = strlen(test_data);
    pg_sz = datalen / 3 + 10;
    conn = malloc(sizeof(connection_t));
    if (conn == NULL) {
        perror("Error allocating memory");
        exit(1);
    }
    bzero(conn, sizeof(connection_t));
    conn->write_buf_q = buf_create(pg_sz, 1);
}

void test_conn_read() {
    int             rc;
    buf_queue_t     *buf_q;
    buf_page_t      *pg = NULL;
    char            result[2048];
    char            *offset = result;

    init_conn_for_read();

    buf_q = conn->read_buf_q;
    rc = conn_read(conn);
    assert(rc == 0);
    assert(buf_q->len == 3);

    pg = buf_get_head(buf_q);
    while (pg != NULL) {
        assert (pg->data != NULL);
        assert (pg->data_offset == 0);
        assert (pg->data_size > 0);
        memcpy(offset, pg->data, pg->data_size);
        offset += pg->data_size;
        pg = pg->_next;
    }
    *offset = '\0';

    printf("The data in the buffer: %s\n\n", result);
    assert(strcmp(test_data, result) == 0);

}

void test_conn_write() {
    int             rc;
    size_t      data_len;
    buf_queue_t     *buf_q;
    buf_page_t      *pg = NULL;
    char            result[2048];
    char            *offset = result;

    init_conn_for_write();
    buf_q = conn->write_buf_q;
    data_len = strlen(test_data);
    rc = conn_write_to_buffer(conn, test_data, 100);
    assert(rc == 0);
    rc = conn_write_to_buffer(conn, test_data + 100, 100);
    assert(rc == 0);
    rc = conn_write_to_buffer(conn, test_data + 200, data_len - 200);
    assert(rc == 0);

    assert(buf_q->len == 3);

    pg = buf_get_head(buf_q);
    while (pg != NULL) {
        assert (pg->data != NULL);
        assert (pg->data_offset == 0);
        assert (pg->data_size > 0);
        memcpy(offset, pg->data, pg->data_size);
        offset += pg->data_size;
        pg = pg->_next;
    }
    *offset = '\0';

    printf("The data in the buffer: %s\n\n", result);
    assert(strcmp(test_data, result) == 0);
}

int main(int argc, const char *argv[]) {
    print_stacktrace_on_error();
    test_conn_read();
    test_conn_write();
    return 0;
}
