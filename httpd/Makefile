CC = gcc
CFLAGS ?= -g
INCLUDES = -I.
LDFLAGS ?=

objects = http.o buffer.o server.o main.o
executable = tinyhttpd
testobjs = test_http.o test_buffer.o test_server.o
tests = test_http test_buffer test_server 

.PHONY: all
all: $(executable) $(tests)

tinyhttpd: $(objects)
	$(CC) $(LDFLAGS) $^ -o $@
	@echo Build successfully.

%.o: %.c %.h
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

server.o: http.h buffer.h

test_%: test_%.o %.o
	$(CC) $(LDFLAGS) $^ -o $@

test_server: buffer.o

.PHONY: clean
clean:
	-rm -f $(objects) $(executable) $(tests) $(testobjs)
	@echo Project cleaned

tests: $(tests)
	@for test in $^; do \
		echo "--------------- Running test: " $$test "---------------------------"; \
		./$$test; \
	done
