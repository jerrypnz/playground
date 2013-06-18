#!/usr/bin/python

import urllib2
import re
import sys
import time
from functools import partial
from lxml.html import tostring
from lxml.html.soupparser import fromstring

def list_article_urls(url, pattern):
    data = urllib2.urlopen(url).read()
    urls = []
    for url in re.findall(pattern, data):
        if not url in urls:
            urls.append(url)
    return urls

to_utf8_string = partial(tostring, pretty_print=True, encoding="utf8")

def scrap_content(article_url):
    data = urllib2.urlopen(article_url).read().decode("gbk")
    doc = fromstring(data)
    content_div = doc.cssselect("div.content")[0]
    if content_div is not None:
        title_h1 = content_div.cssselect("h1")[0]
        contents = content_div.cssselect("div.content_txt>p")
        return "\n".join([to_utf8_string(p) for p in [title_h1] + contents])
    else:
        return None

def main():
    if len(sys.argv) < 1:
        print >> sys.stderr, "Usage: %s targetfile.html" % (sys.argv[0])
    with open(sys.argv[1], 'w') as f:
        for url in list_article_urls(
                "http://www.gaokao.com/zyk/dzkb/ywkb/jsrkywb1/",
                "http:\/\/www\.gaokao\.com\/e\/20090910\/.*\.shtml"):
            print "scrapping", url
            while True:
                try:
                    f.write(scrap_content(url))
                    break
                except:
                    time.sleep(10)
                    print "failed, retrying", url
            f.write("\n")
            time.sleep(5)

if __name__ == '__main__':
    main()
