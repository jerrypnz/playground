class TestGenerator(object):
    """docstring for TestGenerator"""
    def __init__(self, start, end):
        self.start = start
        self.end = end

    def generate(self):
        for i in range(self.start, self.end + 1):
            yield i


if __name__ == '__main__':
    a = TestGenerator(1, 10)
    for i in a.generate():
        print i

