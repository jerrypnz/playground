def qsort(data):
    size = len(data)
    if size <= 1:
        return data
    pivot = data[size/2]
    return  \
        qsort([small for small in data if small < pivot]) + \
        [middle for middle in data if middle == pivot] + \
        qsort([big for big in data if big > pivot])

print qsort([67,99,89,10,4,99,56,7,123,99])
