#!/bin/bash

gcc -fpic -c -I/usr/include/python2.7 -I/usr/lib/python2.7/config foo.c
gcc -shared -o foo.so foo.o
