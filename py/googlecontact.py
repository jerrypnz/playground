#fileencoding: utf-8

import csv
import codecs
import cStringIO
import sys

class UTF8Recoder(object):
    """
    Iterator that reads an encoded stream and reencodes the input to UTF-8
    """
    def __init__(self, f, encoding):
        self.reader = codecs.getreader(encoding)(f)

    def __iter__(self):
        return self

    def next(self):
        return self.reader.next().encode("utf-8")


class UnicodeCsvDictReader(object):
    """
    A CSV reader which will iterate over lines in the CSV file "f",
    which is encoded in the given encoding.
    """
    def __init__(self, f, dialect=csv.excel, encoding="utf-8", **kwds):
        f = UTF8Recoder(f, encoding)
        self.reader = csv.DictReader(f, dialect=dialect, **kwds)

    def next(self):
        row = self.reader.next()
        # return [unicode(s, "utf-8") for s in row]
        return dict(zip(row.iterkeys(), [unicode(s, "utf-8") for s in row.itervalues()] ))

    def __iter__(self):
        return self


class UnicodeCsvDictWriter(object):
    """
    A CSV writer which will write rows to CSV file "f",
    which is encoded in the given encoding.
    """
    def __init__(self, f, fieldnames, dialect=csv.excel, encoding="utf-8", **kwds):
        # Redirect output to a queue
        self.queue = cStringIO.StringIO()
        self.writer = csv.DictWriter(self.queue, fieldnames, dialect=dialect, **kwds)
        self.stream = f
        self.encoder = codecs.getincrementalencoder(encoding)()

    def writerow(self, row):
        self.writer.writerow(dict(zip(row.iterkeys(), [s.encode("utf-8") for s in row.itervalues()] )))
        # Fetch UTF-8 output from the queue ...
        data = self.queue.getvalue()
        data = data.decode("utf-8")
        data = self.encoder.encode(data)
        self.stream.write(data)
        self.queue.truncate(0)

    def writerows(self, rows):
        for row in rows:
            self.writerow(row)

    def writeheader(self):
        self.writer.writeheader()


GOOGLE_CONTACT_KEYS = [
    'Yomi Name',
    'Maiden Name',
    'Group Membership',
    'Priority',
    'Location',
    'Given Name Yomi',
    'Hobby',
    'Mileage',
    'Additional Name Yomi',
    'Billing Information',
    'Birthday',
    'Name Prefix',
    'Family Name Yomi',
    'Nickname',
    'Family Name',
    'Directory Server',
    'Additional Name',
    'Name',
    'Gender',
    'Notes',
    'Name Suffix',
    'Initials',
    'Sensitivity',
    'Short Name',
    'Phone 1 - Type',
    'Phone 1 - Value',
    'E-mail 1 - Type',
    'E-mail 1 - Value',
    'Occupation',
    'Given Name',
    'Subject'
]

FIELD_SEPARATOR='圕'

class GmailContactAdapter(object):
    """Adapter of Gmail CSV address book files"""

    # The following are field names of Gmail CSV contact files
    NAME = "Name"
    FAMILY_NAME = "Family Name"
    GIVEN_NAME = "Given Name"
    WEBSITE = "Website 1 - Value"
    WEBSITE_TYPE = "Website 1 - Type"
    EMAILS = [ "E-mail %d - Value" % i for i in range(1, 3) ]
    EMAIL_TYPES = [ "E-mail %d - Type" % i for i in range(1, 3) ]
    PHONE_NUMBERS = [ "Phone %d - Value" % i for i in range(1, 4) ]
    PHONE_NUMBER_TYPES = [ "Phone %d - Type" % i for i in range(1, 4) ]
    ADDRS = [ "Address %d - Type" % i for i in range(1, 3) ]
    ADDR_TYPES = [ "Address %d - Type" % i for i in range(1, 3) ]

    DEFAULT_PHONENUMBER_TYPES = ('mobile', 'home', 'office')
    DEFAULT_ADDR_TYPES = ('home', 'office')


    def import_contacts(self, f):
        """Returns a generator that could be used to iterator the imported friends"""
        reader = UnicodeCsvDictReader(f, encoding="utf-16")
        for row in reader:
            yield self._to_member(row)

    def export_contacts(self, f, member_iter):
        """Iterate the members, covert them to Gmail CSV format and write the result to given file object"""
        pass

    def _to_member(self, row):
        """Mapping a Gmail contact record to a member dict"""
        friend = {}

        # Name
        first_name = row[self.GIVEN_NAME]
        last_name = row[self.FAMILY_NAME]
        if first_name and last_name:
            friend['name'] = (first_name, last_name)
        else:
            friend['name'] = (row[self.NAME], )

        isvalid = lambda x : x in row and row[x]

        # Website
        friend['blog'] = row[self.WEBSITE]

        # Email Addresses
        friend['email'] = tuple([ row[key] for key in self.EMAILS if isvalid(key) ])

        # Phone Numbers
        friend['phone'] = tuple([ row[key] for key in self.PHONE_NUMBERS if isvalid(key) ])

        # Addresses
        friend['address'] = tuple([ row[key] for key in self.ADDRS if isvalid(key) ])

        return friend

    def _from_member(self, member):
        """Mapping a member record to a Gmail contact record"""
        row = {}

        # Name
        first_name, last_name = member['name']
        row[self.GIVEN_NAME] = first_name
        row[self.FAMILY_NAME] = last_name
        row[self.NAME] = "%s %s" % (first_name, last_name)

        # Website
        row[self.WEBSITE] = member['blog']
        row[self.WEBSITE_TYPE] = 'blog'

        # Email Addresses
        for i, email in enumerate(member['email']):
            row[self.EMAILS[i]] = email
            row[self.EMAIL_TYPES[i]] = '*'

        # Phone Numbers
        for i, phone in enumerate(member['phone']):
            row[self.PHONE_NUMBERS[i]] = phone
            row[self.PHONE_NUMBER_TYPES[i]] = self.DEFAULT_PHONENUMBER_TYPES[i]

        # Addresses
        for i, addr in enumerate(members['address']):
            row[self.ADDRS[i]] = addr
            row[self.ADDR_TYPES[i]] = self.DEFAULT_ADDR_TYPES[i]

        return row


def main(filename):
    inf = open(filename, 'r')
    adapter = GmailContactAdapter()
    #out = open(filename + ".copy", 'wb')
    keys = None

    #writer.writeheader()
    for row in adapter.import_contacts(inf):
        print "------------------------------------"
        for k, v in row.items():
            print k.rjust(20), ': ', v
        #writer.writerow(row)

    inf.close()
    #out.close()

if __name__ == '__main__':
    if len(sys.argv) < 1:
        print "Usage: %s google_contact_file"
        sys.exit(1)
    main(sys.argv[1])
