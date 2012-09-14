// Expression.cpp : �������̨Ӧ�ó������ڵ㡣
//

#include "Tokens.h"
#include "expevaluator.h"
#include <iostream>
#include <string>
#include <cstdlib>

using namespace std;

int main(int argc, char* argv[])
{
	string temp;
	cout<<"Please input an expression:"<<endl;
	getline(cin,temp);
	ExpEvaluator eval;
	eval.BuildExpression(temp.c_str());
	eval.PrintPostExp(cout);
	cout<<"The result is:"<<eval.Evaluate()<<endl;
	system("pause");
	return 0;
}

