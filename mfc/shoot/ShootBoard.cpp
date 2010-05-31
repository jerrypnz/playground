// ShootBoard.cpp: implementation of the ShootBoard class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Shoot.h"
#include "ShootBoard.h"
#include <stdlib.h>
#include <math.h>

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

ShootBoard::ShootBoard(CDC *dc,CRect *targetRect)
{
	this->targetDC = dc;
	this->clientRect.CopyRect(targetRect);
	this->currentX = 0;
	this->currentY = 0;
}

ShootBoard::~ShootBoard()
{
	
}

void ShootBoard::GenerateTarget()
{
	currentX = rand() % (clientRect.Width()-2*MAX_RADIUS) + MAX_RADIUS;
	currentY = rand() % (clientRect.Height()-2*MAX_RADIUS) + MAX_RADIUS;
	DrawTarget();
}



void ShootBoard::DrawTarget()
{
	CGdiObject *oldPen = targetDC->SelectStockObject(NULL_PEN);
	targetDC->Rectangle(&clientRect);
	CPen circlePen;
	circlePen.CreatePen(PS_SOLID,0,0xff0000);
	targetDC->SelectObject(&circlePen);
	CRect temp;
	for(int i=10;i>=1;i--)
	{
		temp.left = currentX - RADIUS_OFFSET*i;
		temp.right = currentX + RADIUS_OFFSET*i;
		temp.top = currentY - RADIUS_OFFSET*i;
		temp.bottom = currentY + RADIUS_OFFSET*i;
		targetDC->Ellipse(&temp);
	}
	targetDC->SelectObject(oldPen);
	int x1 = currentX - MAX_RADIUS - 5;
	int x2 = currentX + MAX_RADIUS + 5;
	int y1 = currentY - MAX_RADIUS - 5;
	int y2 = currentY + MAX_RADIUS + 5;
	targetDC->MoveTo(x1,currentY);
	targetDC->LineTo(x2,currentY);
	targetDC->MoveTo(currentX,y1);
	targetDC->LineTo(currentX,y2);
	circlePen.DeleteObject();
}

int ShootBoard::Shoot(int x, int y)
{
	CPen redPen;
	redPen.CreatePen(PS_SOLID,4,0x0000ff);
	CPen *oldPen = targetDC->SelectObject(&redPen);
	targetDC->MoveTo(x-5,y-5);
	targetDC->LineTo(x-4,y-4);
	targetDC->SelectObject(oldPen);

	int distance = (int)sqrt( (x-currentX)*(x-currentX) + (y-currentY)*(y-currentY) );
	int mark = 10 - distance / RADIUS_OFFSET;
	mark = mark>0 ? mark:0;
	return mark;
}

void ShootBoard::ClearBakground()
{
	CGdiObject *oldPen = targetDC->SelectStockObject(NULL_PEN);
	targetDC->Rectangle(&clientRect);
	targetDC->SelectObject(oldPen);
}
