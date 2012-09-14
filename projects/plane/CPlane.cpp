#include "CPlane.h"

void CPlane::Init(double x,double y,BITMAP *pic,CControl *controller)
{
			_x=x;
			_y=y;
			_pic=pic;
			_controller=controller;
			_controller->SetTargetObject(this);
			_cx=pic->w;
			_cy=pic->h;
}	

void CPlane::Update()
{
	_controller->Control();
}


void CPlane::Draw(BITMAP *dest)
{
	//TODO:Add your drawing routine here
	draw_sprite(dest,_pic,(int)_x,(int)_y);
}


CIRCLE CPlane::GetCircle()
{
	double centerX=_x+_cx/2;
	double centerY=_y+_cy/2;
	double radius=_cx/2;
	return CIRCLE(centerX,centerY,radius);
}

bool CPlane::HitTest(CSprite *other)
{
	CIRCLE temp = other->GetCircle();	
	double distance=sqrt( (_x - temp._x)*(_x - temp._x) + (_y-temp._y)*(_y-temp._y) );
	
	if(distance<=(_cx/2 + temp._r))
		return true;
	else
		return false;

}
