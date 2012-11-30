#include <stdio.h>

char* str1 = "Hello World!";
char* str2 = "Hello World!";
char str3[] = "Hello World!";

int main(int argc, const char *argv[])
{
    printf("The address of the two global strings:\n");
    printf("str1: 0x%x\n", (unsigned int)str1);
    printf("str2: 0x%x\n", (unsigned int)str2); // str1 and str2 share the same storage
    printf("str3: 0x%x\n", (unsigned int)str3);

    printf("Before modifying the string content\n");
    printf("str1: %s\n", str1);
    printf("str2: %s\n", str2);
    printf("str3: %s\n", str3);

    // str1[0] = 'k'; //Cause segment fault
    str3[0] = 'k';
    printf("After modifying the string content\n");
    printf("str1: %s\n", str1);
    printf("str2: %s\n", str2);
    printf("str3: %s\n", str3);
    return 0;
}
