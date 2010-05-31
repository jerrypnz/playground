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
	// ��һ���ַ����еõ�����token
	void GetTokens(const char* scr);
	// ��һ����׼�������ӡ����token
	void Print(ostream& out);
	//����token�б�Ŀ�ͷ
	MyIterator begin(void)
	{
		return tokens.begin();
	}
	// ����token�б�Ľ�β
	MyIterator end(void)
	{
		return tokens.end();
	}
private:
	// ��STL��vector�������������token
	vector<string> tokens;
public:
	void PushToken(const char* token);
};

ostream& operator<<(ostream& out,Tokens& tokens);

#endif
