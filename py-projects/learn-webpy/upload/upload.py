#!/usr/bin/python

import web
import os.path
from web import form

class Index(object):

    def GET(self):
        """Index action"""
        return render.simple_upload_page('Simple Upload Demo')


class Upload(object):
    """Upload application"""

    def POST(self):
        """Handle uploaded file"""
        x = web.input(myfile={})
        if 'myfile' in x:
            filepath = x.myfile.filename.replace('\\', '/')
            filename = filepath.split('/')[-1]
            fout = open(os.path.join('upload', filename), 'wb')
            fout.write(x.myfile.file.read())
            fout.close()
            return 'Upload succeeded'
        else:
            return 'No file'


urls = (
    '/upload', 'Upload',
    '/', 'Index'
)

app = web.application(urls, globals())
render = web.template.render('templates')

if __name__ == '__main__':
    app.run()

