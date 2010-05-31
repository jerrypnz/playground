#include "CBullet.h"
#include <cstdlib>

using namespace std;

CIRCLE CBullet::GetCircle()
{
    return CIRCLE(_x,_y,BULLET_RADIUS);
}    

bool CBullet::HitTest(CSprite* other)
{
	CIRCLE temp = other->GetCircle();	
	double distance=sqrt( (_x - temp._x)*(_x - temp._x) + (_y-temp._y)*(_y-temp._y) );
	
	if(distance<=(BULLET_RADIUS + temp._r))
		return true;
	else
		return false;
} 
	

void CBullet::Draw(BITMAP* dest)
{
	circlefill(dest,(int)_x,(int)_y,BULLET_RADIUS,_color);
}


void CBullet::Update()
{
	_x+=_speedx;
	_y+=_speedy;
	if(_x<0)
	_x=SCREEN_W;
	else if(_x>SCREEN_W)
	{
		_x=0;
		_speedx = (rand()-rand())/19000.0;
		//_speedy +=0.05;
	}

	if(_y<0)
		_y=SCREEN_H;
	else if(_y>SCREEN_H)
	{
		_y=0;
		_x=rand()%SCREEN_H;
		_speedx = (rand()-rand())/19000.0;
		//_speedy +=0.05;
	}
}

void CBullet::Init(double x,double y,double speed,int color)
{
	_x=x;
   	_y=y;
   	_speedx=speed;
   	_speedy=1;
   	_color=color;
}        

