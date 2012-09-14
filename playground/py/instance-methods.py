#!/usr/bin/python

class Dummy(object):

    def __init__(self, foo):
        self.foo = foo

    def doubleFoo(self):
        """docstring for doubleFoo"""
        print "Doing something. Foo is: ", self.foo
        return self.foo * self.foo

    def __str__(self):
        return "Dummy(foo=%d)" % self.foo

    def __repr__(self):
        return self.__str__()


if __name__ == '__main__':
    dummies = [ Dummy(1), Dummy(2), Dummy(3), Dummy(4) ]
    dummies2 = map(Dummy.doubleFoo, dummies)
    print "Old dummies: ", dummies
    print "New dummies: ", dummies2
