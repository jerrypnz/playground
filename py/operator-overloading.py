class StringField(object):
    def __init__(self, name):
        self.name = name

    def __eq__(self, other):
        return lambda x : getattr(x, self.name) == other


class IntegerField(object):
    def __init__(self, name):
        self.name = name

    def __lt__(self, other):
        return lambda x : getattr(x, self.name) < other

    def __gt__(self, other):
        return lambda x : getattr(x, self.name) > other



class User(object):
    name = StringField("name")
    age = IntegerField("age")

    """User object"""
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __repr__(self):
        return "[name=%s, age=%s]" % (self.name, self.age)


def main():
    users = [User("jerry", 24), User("foo", 21), User("bar", 20), User("jerry", 16)]
    jerrys = filter(User.name == "jerry", users)
    twenties = filter(User.age > 20, users)
    print jerrys
    print twenties


if __name__ == '__main__':
    main()

