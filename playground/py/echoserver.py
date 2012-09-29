#!/usr/bin/env python

"""
A simple echo server
"""

import socket
import sys

host = ''
port = 50000
if len(sys.argv) > 1:
    port = int(sys.argv[1])
backlog = 5
size = 1024
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((host,port))
s.listen(backlog)
while 1:
    client, address = s.accept()
    data = client.recv(size)
    if data:
        client.send(data)
    client.close()
