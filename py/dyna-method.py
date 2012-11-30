def make_list_func(fn):
    """make_list_func"""
    def actual_func(items):
        """innter func"""
        return [ fn(item) for item in items]
    return actual_func

add_one = make_list_func(lambda x : x + 1)
twice = make_list_func(lambda x : x * 2)

if __name__ == '__main__':
    data = range(0, 9)
    print add_one(data)
    print twice(data)
