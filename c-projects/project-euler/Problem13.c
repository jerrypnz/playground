#include <stdio.h>
#include <string.h>

#define CHAR_TO_INT(c)  ((c) - '0')
#define INT_TO_CHAR(digit) ('0' + (char)(digit))

char data[100][55];
char answer[100];

void readData(const char* fileName) {
    FILE* fp = fopen(fileName, "r");
    if (fp == 0) {
        printf("Could not open file: %s\n", fileName);
        return;
    }
    for (int i=0;i<100,!feof(fp);i++) {
        fgets(data[i], 55, fp);
    }
    fclose(fp);
}

void printData() {
    printf("\nThe numbers: \n");
    for (int i=0; i<100; i++) {
        printf("%s", data[i]);
    }
    printf("\n");
}  

int calcAnswer() {
    int sum=0;
    int tmp=0;
    int digit;
    int digitPos = 98;
    for (int i=49; i>=0; i--) {
        for (int j=0; j<100; j++) {
            sum += CHAR_TO_INT(data[j][i]);
        }
        sum += tmp;
        tmp = sum / 10;
        digit = sum % 10;
        sum = 0;
        answer[digitPos--] = INT_TO_CHAR(digit);
    }
    while (tmp > 0) {
        digit = tmp % 10;
        answer[digitPos--] = INT_TO_CHAR(digit);
        tmp /= 10;
    }
    return digitPos + 1;
}

const char* getRealPath(const char* arg0, const char* fileName) {
    char* lastSlash = strrchr(arg0, '\\');
    if (lastSlash == 0) {
        return fileName;
    }
    strcpy(lastSlash+1, fileName);
    return arg0;
}

int main(int argc, char** argv) {
    char filePath[100];
    strncpy(filePath, argv[0], 100);
    getRealPath(filePath, "Problem13.data");
    readData(filePath);
    printData();
    memset(answer,0,100);
    int startPos = calcAnswer();
    printf("\n\nThe sum of all the numbers is: \n%s\n", answer + startPos);
}
