def var_arg_test(required, *args, **kargs):
    """var arg test"""
    print 'requried arg     : ', required
    print 'non-keyword args : ', ', '.join(["%s" % arg for arg in args])
    print 'keyword args     : ', '; '.join(["%s=%s" % (k, v) for k, v in kargs.items() ])
    print '-------------------------------------------------------------'


if __name__ == "__main__":
    var_arg_test('foo')
    var_arg_test('bar', 1, 2, 3)
    var_arg_test('hahahahaha', 1, 2, a=99, b='hello')
    var_arg_test('oops', a=1, b='bazinga', c=1.8999)
    # non-keyword arg could not appear after keyword arg
    #var_arg_test('hey,cool', 6, a=1, 7, b='bazinga', c=1.8999, 9)

