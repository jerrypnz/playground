#include <stdio.h>
#include <math.h>

int main() {
    __int64 primes[200000];
    primes[0] = 2;
    primes[1] = 3;
    int currentPos = 2;
    __int64 primeSum = 5;
    for (__int64 candidate = 5; candidate < 2000000L; candidate += 2) {
        int isPrime = 1;
        __int64 candidateSqrt = (__int64)sqrt(candidate) + 1;
        for (int i = 1; i < currentPos, primes[i] < candidateSqrt; i++) {
            if (candidate % primes[i] == 0) {
                isPrime = 0;
                break;
            }
        }
        if (isPrime) {
            primes[currentPos++] = candidate;
            primeSum += candidate;
            if (currentPos >= 200000 ) {
                printf("Meet the max length of the array, exiting program.");
                return 1;
            }
        }
    }
    //printf("The length of long: %d\n", sizeof(__int64));
    printf("The sum of all the primes below 2000000: %I64d\n", primeSum);
}
