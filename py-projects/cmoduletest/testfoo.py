import foo
import signal
import sys

def segfault_handler(signum, frame):
    print >> sys.stderr, "Segment fault captured! Signum: %s" % signum
    print >> sys.stderr, repr(frame)
    sys.exit(-1)


if __name__ == '__main__':
    #signal.signal(signal.SIGSEGV, segfault_handler)
    foo.hello("Jerry")
