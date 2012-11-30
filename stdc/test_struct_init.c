#include <stdio.h>

typedef struct _foo_t {
    int     bar;
    char    *monkey;
    char    *cat;
} foo_t;

void dump_foo(const foo_t *foo) {
    printf("Foo object at 0x%x[bar=%d, monkey=%s, cat=%s]", (unsigned)foo, foo->bar, foo->monkey, foo->cat);
}

int main(int argc, const char *argv[]) {
    char *monkey = "This is a monkey speaking C";
    char *ct = "I am a cat";

    foo_t foo = {
        monkey: monkey,
        bar: 12,
        cat: ct
    };
    dump_foo(&foo);
    return 0;
}
