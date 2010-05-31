#include "tokens.h"
#include <locale>
#include ".\tokens.h"

using namespace std;

Tokens::Tokens(void)
{
}

Tokens::~Tokens(void)
{
}

// 从一个字符串中得到所有token
void Tokens::GetTokens(const char* scr)
{
	string temp;
	while(*scr)
	{
		if(isalnum(*scr) || *scr=='.')
		{
			temp.append(scr,1);
		}
		else if( *scr == '+' || 
				 *scr == '-' ||
				 *scr == '*' ||
				 *scr == '/' ||
				 *scr == '(' ||
				 *scr == ')'
				 )
		{
			if(!temp.empty())
			{
				tokens.push_back(temp);
				temp.clear();
			}
			tokens.push_back(string(scr,1));
		}
		else if(isspace(*scr))
		{
			if(!temp.empty())
			{
				tokens.push_back(temp);
				temp.clear();
			}
		}
		scr++;
		/*else
			return false;*/
	}
	if(!temp.empty())
	{
		tokens.push_back(temp);
		temp.clear();
	}
	/*return true;*/
}

// 向一个标准输出流打印所有token
void Tokens::Print(ostream& out)
{
	for(Tokens::MyIterator it = begin();it!=end();it++)
	{
		out<<*it<<" ";
	}
	out<<endl;
}

////返回token列表的开头
//inline vector<string>::iterator Tokens::begin(void)
//{
//	return tokens.begin();
//}
//
//// 返回token列表的结尾
//inline vector<string>::iterator Tokens::end(void)
//{
//	return tokens.end();
//}

ostream& operator<<(ostream& out,Tokens& tokens)
{
	tokens.Print(out);
	return out;
}

void Tokens::PushToken(const char* token)
{
	tokens.push_back(token);
}
