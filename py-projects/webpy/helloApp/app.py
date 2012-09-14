#!/usr/bin/python

import web
from web import form

urls = (
    '/', 'Hello'
)

app = web.application(urls, globals())
render = web.template.render('templates/')

NameForm = form.Form(
        form.Textbox('Name', form.Validator("Name is empty.", lambda x : len(x) > 0)),
        form.Button('Go'),
    )

class Hello(object):
    """Hello controller"""

    def GET(self):
        form = NameForm()
        return render.index(form)

    def POST(self):
        form = NameForm()
        if not form.validates():
            return render.index(form)
        name = form.d.Name
        return render.hello(name)

if __name__ == '__main__':
    app.run()

