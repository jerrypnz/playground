#ifndef EXPEVALUATOR_H
#define EXPEVALUATOR_H

#include "tokens.h"

class ExpEvaluator
{

public:
	ExpEvaluator(void);
	ExpEvaluator(const char* scr);
	~ExpEvaluator(void);
	void BuildExpression(const char* scr);
	double Evaluate(void);
	void PrintPostExp(ostream& out)
	{
		postExp.Print(out);
	}

private:
	// Äæ²¨À¼Ê½µÄtokens
	Tokens postExp;
	Tokens scrTokens;
	void BuildPostExp(void);
	
public:
	static double ParseDouble(const char* num);
	static int		PrejudiceCmp(const char first, const char second);
	static double BinaryOp(double oprand1, double oprand2, const char op);
};

#endif