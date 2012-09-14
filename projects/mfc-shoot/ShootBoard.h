// ShootBoard.h: interface for the ShootBoard class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SHOOTBOARD_H__841096E6_374C_44A0_991D_0938123A0674__INCLUDED_)
#define AFX_SHOOTBOARD_H__841096E6_374C_44A0_991D_0938123A0674__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

const int RADIUS_OFFSET = 8;
const int MAX_RADIUS = 80;

class ShootBoard  
{
public:
	void ClearBakground();
	ShootBoard(CDC *dc,CRect *targetRect);
	virtual ~ShootBoard();
	int Shoot(int x,int y);
	void GenerateTarget();	

private:
	CRect clientRect;
	int currentX;
	int currentY;
	void DrawTarget();
	CDC* targetDC;
};

#endif // !defined(AFX_SHOOTBOARD_H__841096E6_374C_44A0_991D_0938123A0674__INCLUDED_)
