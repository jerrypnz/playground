#include <stdio.h>

typedef unsigned char byte;
typedef unsigned long ulong;

void show_bytes(byte *p, int len) {
    printf("0x ");
    for (int i = 0; i < len; i++) {
        printf("%02X ", *(p+i));
    }
    printf("\n");
}

int main(int argc, const char *argv[])
{
    char a = -99;
    long aa = (long) a;

    show_bytes((byte*)&a, 1);
    show_bytes((byte*)&aa, sizeof(long));

    byte b = 99;
    ulong bb = (ulong) b;

    show_bytes((byte*)&b, 1);
    show_bytes((byte*)&bb, sizeof(ulong));

    long cc = -100;
    char c = (char) cc;
    show_bytes((byte*)&cc, sizeof(long));
    show_bytes((byte*)&c, 1);

    ulong dd = 100;
    byte d = (byte) dd;

    show_bytes((byte*)&dd, sizeof(ulong));
    show_bytes((byte*)&d, 1);

    return 0;
}
