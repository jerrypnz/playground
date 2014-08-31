#!/usr/bin/python

import random

def simulate_once(total_choices):
    """Simulate the process"""
    host_choices = total_choices[:]

    first_choice = random.choice(total_choices)
    host_choices.remove(first_choice)

    host_choice = 'sheep'

    total_choices.remove(host_choice)
    total_choices.remove(first_choice)
    return ((first_choice == "car"),  (total_choices[0] == "car"))

if __name__ == '__main__':
    total = 10000
    succ_change = 0.0
    succ_nochange = 0.0
    for i in range(total):
        a, b = simulate_once(["sheep", "sheep", "car"])
        if a: succ_nochange = succ_nochange + 1
        if b: succ_change = succ_change + 1
    print "Success %d(change) %d(nochange) times out of a total %d runs." % (succ_change, succ_nochange, total)
