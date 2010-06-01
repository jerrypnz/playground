/*  HELLO.C -- Hello, world */

#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <stdlib.h>

#define IS_DIGIT(c) ((c) >= '0' && (c) <='9')
#define DIGIT(c) ((c) - '0')
#define TRUE 1
#define FALSE 0

/* 把这行去掉就不会显示多余的信息 */
#define SHOW_DETAIL


static char* chNum[] = {"零","一","二","三","四","五","六","七","八","九"};
static char* BiggerChar[] = {"","万","亿","万亿","亿亿"};
static char* SmallerChar[] = {"十","百","千"};

/**
 * 分析number所代表的阿拉伯数字并转换成汉字形式,存放在result中,result
 * 的内存问题需用户自行解决,本函数不会检查溢出问题
 */
void ToChinese(char *number,char *result);
/**
 * 将一个四位数转换成汉字形式,number必须为四位数,否则激发一个断言错误
 * 此函数北ToChinese()调用
 */
void ThousandToChinese(char *number, char *result);
/**
 * 删除number所代表的阿拉伯数字开头多余的0
 */
void DeleteLeadingZero(char *number);
/**
 * 判断number是否为一个数
 */
int IsNumber(char *number);
/**
 * 判断number所代表的数是不是全为0
 */
int IsZero(char *number);


main()
{
    char source[50];
    char result[200];
    printf("阿拉伯数转换为中文表达程序\n");
    printf("作者:Jerry 时间:2006-10-27\n");
start:
    printf("----------------------------------------------\n");
    printf("请输入一个阿拉伯数(输入exit退出程序):");
    gets(source);
    if(strcmp(source,"exit")==0)
        return;
    if(!IsNumber(source))
    {
        printf("摆脱,要输入一个阿拉伯数啊!\n");
        goto start;
    }
    if(strlen(source)>18)
    {
        printf("目前还不能处理那么大的数字!不好意思.不过你先想想你自己能把它表达出来吗?^_^\n");
        goto start;
    }
    ToChinese(source,result);
    printf("对应的中文表达是:\n\t%s\n",result);

    goto start;
}



void DeleteLeadingZero(char *number)
{
    char *temp = number;
    while(*temp++ =='0');
    temp--;
    strcpy(number,temp);
}


int IsNumber(char *number)
{
    int i;
    for(i=0;i<strlen(number);i++)
    {
        if(!IS_DIGIT(number[i]))
            return FALSE;
    }
    return TRUE;
}


int IsZero(char *number)
{
    while(*number)
    {
        if(DIGIT(*number)!=0)
            return FALSE;
        number++;
    }
    return TRUE;
}


void ToChinese(char *number,char *result)
{
    char *tempThousand[50];
    char *tempResult[80];
    int firstLen;
    int zeroProcessed = FALSE;
    int numLen;
    int tokenNum,totalTokens;
    int tokenLen;
    int i;
    result[0] = '\0';
    if(!IsNumber(number))
        return;
    if(IsZero(number))
    {
        strcat(result,chNum[0]);
        return;
    }
    DeleteLeadingZero(number);
    numLen = strlen(number);
    totalTokens = (numLen%4==0) ? (numLen/4):(numLen/4 + 1);

    #ifdef SHOW_DETAIL
    printf("\tDEBUGINFO-->去掉多余的零后的数:%s\n",number);
    printf("\tDEBUGINFO-->数的长度为:%d\n",numLen);
    printf("\tDEBUGINFO-->按四位一段分为%d段\n",totalTokens);
    #endif

    if(totalTokens == 0)
        totalTokens = 1;
    tokenNum = totalTokens;
    while(tokenNum>0)
    {
        tokenLen = numLen - 4*(tokenNum-1);
        memset(tempResult,0,80);
        memset(tempThousand,0,50);
        memcpy(tempThousand,number,tokenLen);
        ThousandToChinese(tempThousand,tempResult);
        if(strlen(tempResult)!=0)
            strcat(tempResult,BiggerChar[tokenNum-1]);

        #ifdef SHOW_DETAIL
        printf("\tDEBUGINFO-->此段长度:%d---段:%4s---处理结果: %s\n",tokenLen,tempThousand,tempResult);
        #endif

        strcat(result,tempResult);

        tokenNum--;
        numLen -= tokenLen;
        number += tokenLen;
    }
}

void ThousandToChinese(char *number,char *result)
{
    int j = 3;
    int numLen;
    int i;
    char temp[4][50];
    /*删除开头(从最高位开始)所有的0*/
    numLen = strlen(number);
    result[0] = '\0';
    /**
     * 如果删除0后字符串长度为0,说明全部都是零,直接退出
     */
    if(IsZero(number))
        return;
    i = numLen-1;
    assert(numLen <= 4);
    /*
    if(IsZero(number))
        return;
    */
    ;
    /**
     * 从个位开始跳过0不作处理,在第一个非零数字处停止,
     * 因为这些0不用转换成任何汉字.比如3000是三千.
     */
    while(i>=0 && number[i--]=='0');
    i++;

    temp[0][0] = '\0';temp[1][0] = '\0';
    temp[2][0] = '\0';temp[3][0] = '\0';

    for(;i>=0;i--,j--)
    {
        if(i>0 && DIGIT(number[i])==0 && DIGIT(number[i-1])==0)
            continue;
        strcat(temp[j], chNum[DIGIT(number[i])] );
        if(i != numLen-1 && DIGIT(number[i]) != 0)
            strcat(temp[j], SmallerChar[numLen-i-2] );
    }

    for(j=j+1;j<4;j++)
    {
        strcat(result,temp[j]);
    }
}
