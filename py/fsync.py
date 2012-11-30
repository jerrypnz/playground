#!/usr/bin/python

from __future__ import with_statement
import os
import sys
from multiprocessing import Process, Queue

def subprocess_main(q):
    filename = q.get()
    print "File Stat:"
    print os.stat(filename)


def main():
    if len(sys.argv) < 1:
        print >> sys.stderr, "Usage: %s filename" % sys.argv[0]
        sys.exit(1)
    print "Staring child process"
    q = Queue()
    cld = Process(target=subprocess_main, args=(q,))
    cld.start()
    src_filename = sys.argv[1]
    dest_filename = "%s.test" % src_filename
    print "Start copying file"
    with open(src_filename) as src_file:
        fd = os.open(dest_filename, os.O_WRONLY | os.O_CREAT | os.O_TRUNC | os.O_SYNC, 0664)
        dest_file = os.fdopen(fd, 'w')
        data = src_file.read(10240)
        while data:
            dest_file.write(data)
            data = src_file.read(10240)
        dest_file.flush()
        os.fsync(fd)
        dest_file.close()
    print "Finished copying file"
    print "Wait child process to stat"
    q.put(dest_filename)
    cld.join()

if __name__ == '__main__':
    main()
