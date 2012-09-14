#!/usr/bin/python

from multiprocessing import Process, Queue
import time

def subprocess_main(q):
    """Entrance of sub process"""
    while True:
        print "[Subprocess]Get object: ", q.get()

def main():
    """Main process"""
    q = Queue()
    cld = Process(target=subprocess_main, args=(q,))
    cld.start()
    idx = 0
    while True:
        msg = "message from main process, id: %d" % idx
        print "[Main] Put object: %s" % msg
        q.put(msg)
        idx = idx + 1
        time.sleep(1)

if __name__ == '__main__':
    main()

