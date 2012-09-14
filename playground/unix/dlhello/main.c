#include <stdio.h>
#include "foo.h"

void print_int(void* arg)
{
    int value = *(int*) arg;
    printf("%d ", value);
}

void inc_int(void* arg)
{
    int value = *(int*) arg;
    *(int*) arg = value + 1;
}

int main(int argc, const char *argv[])
{
    int data[] = {9, 8, 7, 6};
    printf("The data before:\n");
    apply_func(data, print_int, 4, sizeof(int));
    apply_func(data, inc_int, 4, sizeof(int));
    printf("\nThe data after:\n");
    apply_func(data, print_int, 4, sizeof(int));
    printf("\n");
    return 0;
}
