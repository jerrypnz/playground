#!/usr/bin/python

class MyProperty(object):
    """docstring for MyProperty"""
    def __init__(self, getter=None, setter=None):
        self._getter = getter
        self._setter = setter

    def __set__(self, instance, value):
        print "Setting value with %s" % value
        self._setter(instance, value)

    def __get__(self, instance, owner):
        print "Getting value.."
        val = self._getter(instance)
        print "Result:%s" % val
        return val


class Person(object):
    """docstring for Person"""
    def __init__(self, name):
        super(Person, self).__init__()
        self._name = name

    def set_name(self, name):
        self._name = name

    def get_name(self):
        return self._name

    name = MyProperty(get_name, set_name)

if __name__ == '__main__':
    p = Person("Jerry")
    print p.name
    p.name = "Drizzt"
    print p.name
