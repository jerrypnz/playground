#!/usr/bin/python
import feedparser
import re
import logging
import logging.handlers
import telnetlib
import os
import time
from datetime import datetime

#--- Constants ----------------------------------------------
_LOG_FILE_NAME = '/tmp/verycd-feed-downloader.log'
_CONFIG_DIR = os.path.expanduser("~/.verycd_downloader/")
_RSS_DATE_FORMAT = '%a, %d %b %Y %H:%M:%S'
_CONF_DATE_FORMAT = '%Y-%m-%d_%H:%M:%S'

#--- log config ----------------------------------------------

def __config_logger():
    """create log object"""
    log=logging.getLogger('verycd-feed-downloader')
    log.setLevel(logging.DEBUG)
    handler = logging.handlers.RotatingFileHandler(_LOG_FILE_NAME, maxBytes=1024*1024, backupCount=5)
    handler.setFormatter(logging.Formatter('%(levelname)s [%(asctime)s] %(message)s'))
    log.addHandler(handler)
    return log

log = __config_logger()


#--- Initialization ----------------------------------------------

def __check_create_config_dir():
    """check the config directory exists or not, and create it if it does not exist"""
    if not os.path.exists(_CONFIG_DIR):
        os.mkdir(_CONFIG_DIR)


#--- Helper functions ----------------------------------------------

def line_token_iterator(file, separator=None, support_comment=False, comment_char='#'):
    """convenient file iterator that could separate the lines with given separator
    and ignore comments"""
    try:
        for line in open(file):
            if support_comment:
                com_pos = line.find(comment_char)
                if com_pos >=0:
                    line = line[:com_pos]

            parts = line.split(separator)
            if len(parts) > 0:
                yield tuple(parts)

    except IOError, e:
        log.error('error reading file %s' % file, e)

#--- Classes ----------------------------------------------

class VeryCDFeedObserver(object):

    """VeryCD feed downloader"""
    def __init__(self, url, handler, filter='.*', last_update_time=None):
        self.url = url
        self.handler = handler
        self.filter_reg = re.compile(filter)
        self.last_update_time = last_update_time
        self.current_latest_time = last_update_time


    def process_update(self):
        """process VeryCD feed updates"""
        links = self.__extract_file_info()
        self.handler.handle_ed2k_links(links)
        return (self.url, self.current_latest_time)


    def __check_update_latest_time(self, item_date_s):
        """compare the date in an rss item with last update time of this feed"""
        item_date = datetime.strptime(item_date_s.replace(' +0000',''), _RSS_DATE_FORMAT)
        if self.last_update_time == None or item_date > self.last_update_time:
            if self.current_latest_time == None or item_date > self.current_latest_time:
                self.current_latest_time = item_date
            return True
        else:
            return False


    def __extract_file_info(self):
        """extract a list of file info from a given verycd feed URL"""
        feed = feedparser.parse(self.url)
        items = feed['items']
        return [
                item['description'].encode('UTF-8')
                for item in items
                if re.match(self.filter_reg, item['title'])
                    and self.__check_update_latest_time(item['date'].encode('UTF-8'))
               ]



class MLDonkeyLinkHandler(object):
    """MLDonkey link handler, which submits ed2k download links"""
    def __init__(self, host='127.0.0.1', port=4000):
        self.host = host
        self.port = port


    def handle_ed2k_links(self, links):
        """submit ed2k links to MlDonkey"""
        tn = telnetlib.Telnet(self.host, self.port, 10)
        # > is the command indicator of MLDonkey
        tn.read_until('>')
        for link in links:
            self.send_command(tn, 'dllink %s' % link)
        self.send_command(tn, 'exit')
        ml_output = tn.read_all()
        log.info('MLDonkey telnet outputs'
                + '\n---------------------------------------------\n'
                + ml_output
                + '\n---------------------------------------------\n')
        tn.close()


    def send_command(self, telnet, command):
        """send a command to the given telnet"""
        log.info('sending command \'%s\' to MLDonkey' % command)
        telnet.write('%s\n' % command)


class PrintLinkHanler(object):
    """Simple link handler that only prints the links, for debugging"""

    def handle_ed2k_links(self, links):
        """print all the ed2k links"""
        print '\n-------------\n'.join(links)


class VeryCDFeedDaemon(object):
    """Main class for this script. It reads configuration file and create observers and call them to check feed updates"""
    def __init__(self, interval=600):
        self.interval = interval
        self.ed2k_handler = MLDonkeyLinkHandler()
        self.feed_list_file = os.path.join(_CONFIG_DIR, "feeds.list")
        self.histories_file = os.path.join(_CONFIG_DIR, "histories.conf")


    def run_daemon(self):
        """main flow of the script."""
        while (True):
            self.run_once()
            time.sleep(self.interval)


    def run_once(self):
        """
            read the actual file instead of caching the feed info,
            so that the modification to the feeds.list
            could be read without restarting the daemon
        """
        feeds, histories = self.__read_feeds()
        log.debug("loading feeds and their last update time")

        if len(feeds) != 0 :
            observers = [ VeryCDFeedObserver(url, self.ed2k_handler, filter=file_filter, last_update_time=last_update_time)
                          for url, file_filter, last_update_time in feeds]

            log.debug("checking for updates")
            for observer in observers:
                url, latest_time = observer.process_update()
                histories[url] = latest_time

            self.__write_histories(histories)

            log.debug("finished checking")
        else :
            log.debug("no feeds found")




    def __read_feeds(self):
        """read the feeds and their latest update time"""
        if not os.path.exists(self.feed_list_file):
            log.warn("feeds.list not found")
            return ([], None)

        histories = {}
        if os.path.exists(self.histories_file):
            for parts in line_token_iterator(self.histories_file):
                histories[parts[0]] = datetime.strptime(parts[1], _CONF_DATE_FORMAT)

        feeds = []
        for parts in line_token_iterator(self.feed_list_file, support_comment=True):
            total_parts = len(parts)
            url = parts[0]
            filter = total_parts > 1 and parts[1] or '.*'

            try:
                last_update_time = histories[url]
            except KeyError, e:
                last_update_time = None
                pass

            if last_update_time == None and total_parts > 2:
                try:
                    last_update_time = datetime.strptime(parts[2], _CONF_DATE_FORMAT)
                except ValueError, e:
                    log.error('invalid date: %s' % parts[2], e)
                    pass
            log.debug("feed[url=%s, filter=%s, last_update_time=%s]" % (url, filter, last_update_time))
            feeds.append((url, filter, last_update_time))

        return (feeds, histories)


    def __write_histories(self, histories):
        """write the histories to the histories.conf"""
        his_file = file(self.histories_file, 'w')
        for url, last_update_time in histories.items():
            if last_update_time != None:
                his_file.write("%s %s \n" %(url, datetime.strftime(last_update_time, _CONF_DATE_FORMAT)))
        his_file.close()



#--- Main flow ----------------------------------------------

def test_feed_observer():
    handler = MLDonkeyLinkHandler('localhost', 4000)
    handler = PrintLinkHanler()
    test_observer = VeryCDFeedObserver('http://www.verycd.com/topics/2752686/feed', handler, filter='.*\.rmvb')
    test_observer.process_update()


if __name__ == "__main__":
    __check_create_config_dir()
    daemon = VeryCDFeedDaemon()
    daemon.run_daemon()


