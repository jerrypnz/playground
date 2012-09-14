def create_counter(initval):
    val = initval
    def _inner_counter():
        val = val + 1
        return val
    return _inner_counter

def test2():
    func = lambda : 'value of x: %s' % x
    try:
        print func()
    except Exception, e:
        print 'ERROR:', e
    x = 10
    print func()
    x = 'oops'
    print func()

def outer_func():
    x = 3
    def inner_func1():
        print 'inner func 1:', x
    def inner_func2():
        x = 'hello'
        print 'inner func 2:', x
    inner_func1()
    inner_func2()
    print 'outer func:', x

if __name__ == '__main__':
    outer_func()
