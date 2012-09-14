CC = gcc
CFLAGS ?= -g -rdynamic -Wall
INCLUDES = -I.
LDFLAGS ?= -g -rdynamic

objects = http.o buffer.o server.o main.o handler.o location.o phase.o stacktrace.o
executable = tinyhttpd
testobjs = test_http.o test_buffer.o test_handler.o test_location.o test_server.o
tests = test_http test_buffer test_handler test_location test_server

.PHONY: all
all: $(executable) $(tests)

tinyhttpd: $(objects)
	$(CC) $(LDFLAGS) $^ -o $@
	@echo Build successfully.

%.o: %.c %.h
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

server.o: http.h buffer.h location.h phase.h

test_%: test_%.o %.o stacktrace.o
	$(CC) $(LDFLAGS) $^ -o $@

test_server: buffer.o phase.o location.o

.PHONY: clean
clean:
	-rm -f $(objects) $(executable) $(tests) $(testobjs)
	@echo Project cleaned

tests: $(tests)
	@for test in $^; do \
		echo "---- Running test: " $$test "---"; \
		./$$test; \
		if [ $$? -ne 0 ]; then \
			echo "Test failed"; \
			break; \
		fi; \
		echo "---- $$test passed --- "; \
		echo ""; \
	done; \
	echo "---- All tests passed ---"
