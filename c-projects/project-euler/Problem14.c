#include <stdio.h>

typedef long long __int64;

__int64 calcChainLength(__int64 target) {
    // There is at least two numbers in the chain, 1 and itself
    __int64 chainLength = 2L;
    while (target != 1L) {
        if ((target % 2L) != 0L ) {
            target = (3L*target+1L)/2L;
            chainLength += 2L;
        } else {
            target /= 2L;
            chainLength += 1L;
        }
    }
    return chainLength;
}

int main() {
    __int64 maxLength = 0L;
    __int64 result = 0L;
    
    printf("Calculating, please wait...\n");
    
    for (__int64 candidate = 2; candidate < 1000000L; candidate++) {
        __int64 tmpLength = calcChainLength(candidate);
        //printf("Chain length of %I64d is %I64d\n", candidate, tmpLength);
        if (tmpLength > maxLength) {
            maxLength = tmpLength;
            result = candidate;
        }
    }
    
    printf("The answer is %I64d\n", result);
    printf("The chain length is %I64d\n", maxLength);
}
