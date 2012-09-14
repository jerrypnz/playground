
def create_counter(initval):
    val = initval
    def _inner_counter():
        nonlocal val
        val = val + 1
        return val
    return _inner_counter

if __name__ == '__main__':
    counter = create_counter(10)
    print("First invocation: ", counter())
    print("Second invocation: ", counter())
    print("Third invocation: ", counter())
