#include "expevaluator.h"
#include "expevaluator.h"

#include <stack>
#include <string>
#include <locale>

const int BIGGER=1;
const int SMALLER=-1;
const int EQUAL=0;
const int ERROR=2;

using namespace std;

ExpEvaluator::ExpEvaluator(void)
{
}

ExpEvaluator::ExpEvaluator(const char* scr)
{
	BuildExpression(scr);
}

ExpEvaluator::~ExpEvaluator(void)
{
}

void ExpEvaluator::BuildExpression(const char* scr)
{
	scrTokens.GetTokens(scr);
	BuildPostExp();
}

double ExpEvaluator::Evaluate(void)
{
	stack<double>	oprands;
	for(Tokens::MyIterator it=postExp.begin();it!=postExp.end();it++)
	{
		if(isdigit(it->at(0)))
			oprands.push(ParseDouble(it->c_str()));
		else
		{
			double temp1 = oprands.top();
			oprands.pop();
			double temp2 = oprands.top();
			oprands.pop();
			oprands.push( BinaryOp(temp2,temp1,it->at(0) ));
		}
	}
	return oprands.top();
}

void ExpEvaluator::BuildPostExp(void)
{
	stack<string> tempStack;
	tempStack.push("#");
	scrTokens.PushToken("#");
	for(Tokens::MyIterator it=scrTokens.begin();it!=scrTokens.end();it++)
	{
		if(isalnum(it->at(0)))
			postExp.PushToken(it->c_str());
		else
		{
			if(*it == ")")
			{
				while(tempStack.top() != "(" && tempStack.top()!="#")
				{
					postExp.PushToken(tempStack.top().c_str());
					tempStack.pop();
				}
				tempStack.pop();
			}
			else
			{
				switch(PrejudiceCmp(it->at(0),tempStack.top().at(0)) )
				{
				case BIGGER:
					tempStack.push(*it);
					break;
				case SMALLER:
					{
						while(tempStack.top()!="#" || 
								PrejudiceCmp(it->at(0),tempStack.top().at(0))==SMALLER)
						{
							postExp.PushToken(tempStack.top().c_str());
							tempStack.pop();
						}
						tempStack.push(*it);
						break;
					}
				case EQUAL:
					{
						while(tempStack.top()!="#")
						{
							postExp.PushToken(it->c_str());
							tempStack.pop();
						}
						tempStack.pop();
					}

				}
			}
		}

	}

}

double ExpEvaluator::ParseDouble(const char* num)
{
	double temp = 0.0;
	double point = 0.1;
	bool flag=false;//用来保存式小数点之前还是之后的标志位
	while(*num)
	{
		if(isdigit(*num))
		{
			if(!flag)
			{
				temp = temp*10 + (*num - '0');
			}
			else 
			{
				temp += (point*(*num - '0'));
				point *= 0.1;
			}
		}
		else if(*num == '.')
			flag = true;

		num++;
	}
	return temp;

}

int ExpEvaluator::PrejudiceCmp(const char first, const char second)
{
	int pre1,pre2;
	switch(first)
	{
	case '*':
	case '/':pre1=4;break;

	case '+':
	case '-':pre1=2;break;

	case '(':pre1=5;break;

	case ')':pre1=0;break;

	case '#':pre1=-1;break;

	default:pre1=-2;
	}

	switch(second)
	{
	case '*':
	case '/':pre2=3;break;

	case '+':
	case '-':pre2=1;break;

	case '(':pre2=0;break;

	case ')':pre2=5;break;

	case '#':pre2=-1;break;

	default:pre2=-2;
	}

	if(pre1 == -2 || pre2 == -2)
		return ERROR;

	if(pre1>pre2)
		return	BIGGER;
	else if(pre1<pre2)
		return SMALLER;
	else if(pre1 == pre2)
		return EQUAL;

}

double ExpEvaluator::BinaryOp(double oprand1, double oprand2, const char op)
{
	switch(op)
	{
	case '-':
		return oprand1-oprand2;
	case '+':
		return oprand1+oprand2;
	case '*':
		return oprand1*oprand2;
	case '/':
		return oprand1/oprand2;
	default:
		return 0.0;
	}
}
