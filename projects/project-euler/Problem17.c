#include <stdio.h>
#include <assert.h>

const int len_digits[] = {
    0, /*zero, but it should not be counted*/
    3, /*one*/
    3, /*two*/
    5, /*three*/
    4, /*four*/
    4, /*five*/
    3, /*six*/
    5, /*seven*/
    5, /*eight*/
    4  /*nine*/
};

const int len_teens[] = {
    3, /*ten*/
    6, /*eleven*/
    6, /*twelve*/
    8, /*thirteen*/
    8, /*fourteen*/
    7, /*fifteen*/
    7, /*sixteen*/
    9, /*seventeen*/
    8, /*eighteen*/
    8  /*nineteen*/
};

const int len_ties[] = {
    0, /*place holder*/
    0, /*place holder*/
    6, /*twenty*/
    6, /*thirty*/
    5, /*forty*/
    5, /*fifty*/
    5, /*sixty*/
    7, /*seventy*/
    6, /*eighty*/
    6  /*ninety*/
};

const int LEN_HUNDRED = 7;
const int LEN_AND = 3;
const int LEN_ONE_THOUSAND = 11;

int calcTiesLength(int number) {
    if (number < 10) {
        return len_digits[number];
    } else if (number < 20) {
        return len_teens[number - 10];
    } else {
        return len_ties[number / 10] + len_digits[number % 10];
    }
}


int calcLength(int number) {
    int hundred_part = number / 100;
    int ties_part = number % 100;
    if (hundred_part == 0)
       return calcTiesLength(ties_part);
    else
       return len_digits[hundred_part] + LEN_HUNDRED + 
           + (ties_part == 0 ? 0 : LEN_AND + calcTiesLength(ties_part) );
}


int main(int argc, const char *argv[]) {
    long totalLen = 0;
    for (int i = 1; i < 1000; i++) {
        totalLen += calcLength(i);
    }
    totalLen += LEN_ONE_THOUSAND;
    printf("The result is: %ld\n", totalLen);
    return 0;
}
