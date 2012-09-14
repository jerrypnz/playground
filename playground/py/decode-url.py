#!/usr/bin/python

import urllib
import sys

def decode_url(url):
    return urllib.unquote(url)



if __name__ == '__main__':
    if len(sys.argv) < 2:
        print "Usage: %s encoded_url" % sys.argv[0]
        sys.exit(1)

    print decode_url(sys.argv[1])
