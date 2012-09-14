#!/bin/bash

gcc -std=c99 -Wall -o libfoo.so -fPIC -shared foo.c
