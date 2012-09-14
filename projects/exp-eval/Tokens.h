#ifndef TOKENS_H
#define TOKENS_H
#include <vector>
#include <string>
#include <iostream>

using namespace std;


class Tokens
{
public:

	typedef vector<string>::iterator MyIterator;

	Tokens(void);
	~Tokens(void);
	// 从一个字符串中得到所有token
	void GetTokens(const char* scr);
	// 向一个标准输出流打印所有token
	void Print(ostream& out);
	//返回token列表的开头
	MyIterator begin(void)
	{
		return tokens.begin();
	}
	// 返回token列表的结尾
	MyIterator end(void)
	{
		return tokens.end();
	}
private:
	// 用STL的vector容器保存的所有token
	vector<string> tokens;
public:
	void PushToken(const char* token);
};

ostream& operator<<(ostream& out,Tokens& tokens);

#endif
