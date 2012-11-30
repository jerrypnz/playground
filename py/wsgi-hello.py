#!/usr/bin/python

from flup.server.fcgi import WSGIServer


def application(environ, start_response):
    content = "Hello world"
    headers = [('Content-Type', 'text/plain"', 'Content-Length', str(len(content)))]
    start_response('200 OK', headers)
    return content

def main():
    options = { 'bindAddress': '/var/run/test-put.sock', 'umask': 0000 }
    WSGIServer(application, **options).run()
