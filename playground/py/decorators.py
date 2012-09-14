#!/usr/bin/python

import time

def log_performance(f):
    """decorator for logging performance info"""
    def __log_performance(*args, **kwargs):
        s = time.time()
        result = f(*args, **kwargs)
        e = time.time();
        time_spent = e - s;
        print "#### Method %s spent %f seconds" % (f.__name__, time_spent)
        return result
    __log_performance.__name__ = f.__name__
    return __log_performance


def log_arguments(f):
    """log arguments of a method call"""
    def __log_arguments(*args, **kwargs):
        print '#### Method %s called with arguments [%s]' % (
                    f.__name__,
                    ', '.join([ str(arg) for arg in args ] + [ '%s=%s' % (k, v) for k, v in kwargs.items() ])
                )
        return f(*args, **kwargs)
    __log_arguments.__name__ = f.__name__
    return __log_arguments


@log_arguments
@log_performance
def test_func(a, b, *args, **kwargs):
    return a * b


class Dummy(object):
    """dummy class for testing instance method decorator"""
    def __init__(self, foo, bar):
        self.foo = foo
        self.bar = bar

    @log_arguments
    @log_performance
    def test_instance_method(self, *args, **kwargs):
        return "%f x %f = %f" % (self.foo, self.bar, self.foo * self.bar)


if __name__ == '__main__':
    result = test_func(0.98, 1.24, 23, 'aa', foo=9.9, bar='hey, you')
    print 'test_func result is: %f' % result
    dummy = Dummy(0.1999, 9.8764)
    result2 = dummy.test_instance_method('bb', foobar='hello to you')
    print 'test_instance_method result is: %s' % result2
