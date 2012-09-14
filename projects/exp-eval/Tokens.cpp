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

// ��һ���ַ����еõ�����token
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

// ��һ����׼�������ӡ����token
void Tokens::Print(ostream& out)
{
	for(Tokens::MyIterator it = begin();it!=end();it++)
	{
		out<<*it<<" ";
	}
	out<<endl;
}

////����token�б�Ŀ�ͷ
//inline vector<string>::iterator Tokens::begin(void)
//{
//	return tokens.begin();
//}
//
//// ����token�б�Ľ�β
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
