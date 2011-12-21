CC = gcc
CFLAGS ?= -g -rdynamic -Wall
INCLUDES = -I.
LDFLAGS ?= -g -rdynamic

objects = ioloop.o echoserver.o
executable = echoserver

.PHONY: all
all: $(executable)

echoserver: $(objects)
	$(CC) $(LDFLAGS) $^ -o $@
	@echo Build successfully.

%.o: %.c %.h
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

.PHONY: clean
clean:
	-rm -f $(objects) $(executable) $(tests) $(testobjs)
	@echo Project cleaned

