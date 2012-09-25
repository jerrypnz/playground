#!/usr/bin/python

from multiprocessing import Process, Queue
from mpd import MPDClient, MPDError
import time
import os.path
import urllib
import re


def cover_searcher_main(q, album_iter):
    """Album art searcher"""
    cover_urlregx = re.compile(r"(http://img.xiami.com/.+/album/.+_1\.jpg)")

    for artist, album in album_iter:
        keyword = "%s %s" % (artist, album)
        search_url = "http://www.xiami.com/search?" + urllib.urlencode({'key': keyword})
        try:
            content = urllib.urlopen(search_url).read()
        except:
            print "Error opening ", search_url
            continue
        r = cover_urlregx.search(content)
        if r:
            small_img_url = r.groups()[0]
            """
            If the small image URL is:
                http://img.xiami.com/images/album/img52/23352/3693121295449157_1.jpg
            Then the large image URL is:
                http://img.xiami.com/images/album/img52/23352/3693121295449157_2.jpg
            """
            one_pos = small_img_url.rfind('1')
            if one_pos > 0:
                large_img_url = small_img_url[0 : one_pos] + '2' + small_img_url[one_pos + 1 : ]
                print "Large cover URL: ", large_img_url
                q.put((large_img_url, artist, album))
        else:
            print "Cover for %s - %s is not found on xiami.com" % (artist, album)



def downloader_main(q, save_dir):
    """Entrance of sub process"""
    while True:
        (url, artist, album) = q.get()
        if not url:
            print "Received end signal, returning"
            return
        print "Downloading cover art for %s - %s from %s" % (artist, album, url)
        _download_cover(url, artist, album, save_dir)


def _download_cover(url, artist, album, save_dir):
    try:
        file_ext = url[url.rfind('.') + 1 : ]
        file_name = "%s-%s.%s" % (artist, album, file_ext)
        urllib.urlretrieve(url, os.path.join(save_dir, file_name))
    except:
        print "Error saving %s" % file_name


def mpd_albums_generator(client):
    for artist in client.list('artist'):
        for album in client.list('album', artist):
            yield (artist, album)


def connect_mpd(host='localhost', port=6600, password=None):
    client = MPDClient()
    try:
        client.connect(host, port)
    except:
        print "Error connecting to mpd at %s:%d" % (host, port)
        return None

    if password:
        try:
            client.password(password)
        except:
            print "mpd password is wrong"
            return None

    return client


def main():
    """Main process"""
    save_dir = "/home/jerry/test_covers/"
    client = connect_mpd()
    if not client:
        return

    q = Queue()
    cld = Process(target=downloader_main, args=(q, save_dir))
    cld.start()
    try:
        cover_searcher_main(q, mpd_albums_generator(client))
    finally:
        """ Empty string indicates the end"""
        q.put(("", "", ""))
        cld.join()

def test_mpd_album_generator():
    for artist, album in mpd_albums_generator(connect_mpd()):
        print artist, ': ', album

if __name__ == '__main__':
    main()
    #test_mpd_album_generator()

