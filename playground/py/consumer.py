def consumer(func):
    def wrapper(*args,**kw):
        gen = func(*args, **kw)
        gen.next()
        return gen
    wrapper.__name__ = func.__name__
    wrapper.__dict__ = func.__dict__
    wrapper.__doc__  = func.__doc__
    return wrapper

def my_generator():
    for i in range(1,10):
        print i
        yield i

print "Undecorated generator:"
for a in my_generator():
    print "Outside generator:", a

print "Decorated generator:"
for b in consumer(my_generator)():
    print "Outside generator:", b
