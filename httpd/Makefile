CC = gcc
CFLAGS ?= -g
INCLUDES = -I.
LDFLAGS ?=

targets = tests server
objects = http.o buffer.o server.o test_http.o test_buffer.o test_server.o main.o
executables = server
tests = test_http test_buffer test_server 

all: $(targets)

clean:
	-rm $(objects) $(executables) $(tests)

buffer.o: buffer.h
http.o: http.h
server.o: server.h http.h buffer.h
main.o: server.h
test_http.o: http.h
test_buffer.o: buffer.h
test_server.o: server.h

test_http: http.o test_http.o
	$(CC) $(LDFLAGS) $^ -o $@

test_buffer: buffer.o test_buffer.o
	$(CC) $(LDFLAGS) $^ -o $@

test_server: server.o test_server.o
	$(CC) $(LDFLAGS) $^ -o $@

server: server.o http.o buffer.o main.o
	$(CC) $(LDFLAGS) $^ -o $@

tests: test_http test_buffer test_server
	@for test in $^; do \
		echo "Running test: " $$test; \
		./$$test; \
	done



