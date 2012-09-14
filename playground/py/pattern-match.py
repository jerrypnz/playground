#!/usr/bin/python

import itertools
import traceback

class PatternError(Exception):
    pass

class _PatternGroup(object):
    """match matching for python"""
    patterns_matchers = {}

    def __init__(self):
        super(_PatternGroup, self).__init__()
        self._rules = []

    def _add_pattern(self, fn, *args, **kwargs):
        self._rules.append(((args, kwargs), fn))
        return self._run

    def _run(self, *args, **kwargs):
        retval = None
        for rule, fn in self._rules:
            if self._match(rule, (args, kwargs)):
                retval = fn(*args, **kwargs)
                break
        else:
            raise PatternError("No match found")
        return retval

    def _match(self, rule, val):
        pargs, pkwargs = rule
        args, kwargs = val
        for actual, expected in itertools.izip(args, pargs):
            if actual != expected:
                return False
        for key, expectedval in pkwargs.iteritems():
            if not key in kwargs or kwargs[key] != expectedval:
                return False
        return True


def match(*args, **kwargs):
    def _do_match(fn):
        fname = fn.__name__ #FIXME Problem 1: Should not only use function name as the key
        if fname in _PatternGroup.patterns_matchers:
            return _PatternGroup.patterns_matchers[fname]._add_pattern(fn, *args, **kwargs)
        else:
            matcher = _PatternGroup()
            _PatternGroup.patterns_matchers[fname] = matcher
            return matcher._add_pattern(fn, *args, **kwargs)
    return _do_match


@match("male")
def greet(male, name):
    print "Hello, Mr. %s" % name

@match("female")
def greet(female, name):
    print "Hello, Mrs. %s" % name

@match()
def greet(_, name):
    print "Hello, %s" % name

if __name__ == '__main__':
    greet('male', 'Cooper')
    greet('female', 'Geller')
    greet('', 'Drizzt')
