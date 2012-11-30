#include <stdio.h>
#include <string.h>

int main() {
    const char* delims = " \t\n";
    char str[] = "jerry is   here\n to   say hello.              ";
    char* token = strtok(str, delims);
    while (token != NULL) {
        printf("Token: %s\n", token);
        token = strtok(NULL, delims);
    }
    printf("Original String: %s\n", str);
}
