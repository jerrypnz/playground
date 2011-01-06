#include <stdio.h>

#define MY_INT_MIN   (-MY_INT_MAX - 1)  
#define MY_INT_MAX   2147483647 

int main(int argc, const char *argv[])
{
    int max = MY_INT_MAX;
    int std_min = MY_INT_MIN;
    int my_min = -2147483648;
    printf("INT_MAX=%d\n", max);
    printf("STD_MIN=%d\n", std_min);
    printf("MY_MIN=%d\n", my_min);
    return 0;
}
