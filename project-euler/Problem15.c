#include <stdio.h>
#include <string.h>

__int64 cache[21][21];

__int64 findOutSolutions(int x, int y) {
    if ((x==0 && y==1) || (x==1 && y==0)) {
        return 1L;
    }
    if (cache[x][y] > 0L) {
        return cache[x][y];
    }
    __int64 result = 0L;
    if (x > 0) {
        result += findOutSolutions(x-1, y);
    }
    if (y > 0) {
        result += findOutSolutions(x, y-1);
    }
    cache[x][y] = result;
    return result;
}

void resetCache() {
    //memset(cache, 0L, 400L*sizeof(__int64));    
    for (int i=0;i<21;i++)
        for (int j=0;j<21;j++)
            cache[i][j]=0L;
}

int main() {
    resetCache();
    __int64 allSolutions = findOutSolutions(20,20);
    printf("There are %I64d solutions\n", allSolutions);
}
