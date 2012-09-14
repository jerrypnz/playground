#!/usr/bin/python
import web

class Hello(object):
    """Hello webpy application"""

    def GET(self, name):
        """Get method"""
        if not name:
            name = "web.py world"
        return "Hello, " + name

urls = (
    '/(.*)', 'Hello'
)

app = web.application(urls, globals())

if __name__ == '__main__':
    app.run()
