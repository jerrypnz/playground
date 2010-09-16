#include <stdio.h>
#include <math.h>
#include <stdlib.h>

int main() {
    long long value = (long long) pow(2.0, 10.0);
    printf("The result of 2^1000: %lld\n", value);
    long result = 0;
    while (value != 0 ) {
        result += (value % 10);
        value /= 10;
    }
    printf("The result is: %ld\n", result);
}
