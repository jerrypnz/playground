import subprocess
import logging

logging.basicConfig(format='%(asctime)s [%(levelname)s]: %(message)s')


def main():
    p = subprocess.Popen(['bash', '-c', 'script.sh'],
                        stdin=subprocess.PIPE,
                        stdout=subprocess.PIPE,
                        stderr=subprocess.PIPE)
    while True:


