#include <stdio.h>
#include <stdlib.h>

int cmpFunctor(const void* a, const void* b) {
    return (*(int*)a - *(int*)b);
}

int main() {
    int list[10] = {10,99,56,78,12,0,999,123,456,123};
    qsort(list, 10, sizeof(int), cmpFunctor);
    for (int i=0; i < 10; i++) {
        printf("%d ", list[i]);
    }
}
