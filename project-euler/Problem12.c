#include <stdio.h>


//Disposed solution. It is, maybe, the slowest solution

int calculateDivisors(__int64 n) {
    int divisors = 2; // 1 and n are always n's divisors
    int half = n/2 + 1;
    for (__int64 i=2; i<half; i++) {
        if (n % i == 0)
            divisors++;
    }
    return divisors;
}

int main() {
    int n = 7;
    __int64 triangle = 0;
    
    for(;;) {
        triangle = (n*(n+1)) >> 1;
        int nDivisors = calculateDivisors(triangle);
        //printf("%I64d has %d divisors\n", triangle, nDivisors);
        if (nDivisors > 500)
            break;
        n++;
    }
    
    printf("The first triangle number that has over 500 divisors: %I64d\n", triangle);
    
}
