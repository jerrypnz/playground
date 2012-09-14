class MyIterator:

    def __init__(self):
        self.index = 0

    def __iter__(self):
        return self

    def next(self):
        self.index = self.index + 1
        if self.index >= 10 :
            raise StopIteration
        return self.index

class TestGenerator(object):
    """docstring for TestGenerator"""
    def __init__(self, start, end):
        self.start = start
        self.end = end

    def generate(self):
        for i in range(self.start, self.end + 1):
            yield i


if __name__ == '__main__':
    for i in MyIterator():
        print i

    a = TestGenerator(1, 10)
    for i in a.generate():
        print i



