#include <stdio.h>

int main(int argc, const char *argv[])
{
    unsigned x = 10;
    printf ("x(10U) > -1 ? %d\n", (x > -1));
    printf ("10 > -1 ? %d\n", (10 > -1));
    printf ("10u > -1 ? %d\n", (10u > -1));
    printf("x + (-1) = %u\n", (x + (-1)));
    printf("x + (-1u) = %u\n", (x + (unsigned)-1));
    printf("-1u: %u\n", (unsigned)-1);
    printf("end");
    return 0;
}
