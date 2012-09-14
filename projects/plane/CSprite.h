#ifndef __SPRITE_H
#define __SPRITE_H

#include <allegro.h>

struct CIRCLE
{
		double _x;
		double _y;
		double _r;
		
		CIRCLE(double x,double y,double r)
		:_x(x),_y(y),_r(r)
		{
		}
};
		

class CSprite
{
	public:
			
		CSprite(double x,double y)
		:_x(x),_y(y)
		{
		}
		
		CSprite()
		:_x(0),_y(0)
		{
		}    
		
		virtual ~CSprite(){}
		
		virtual bool HitTest(CSprite*){}
		virtual void Draw(BITMAP*){}
		virtual void Update(){}
		virtual CIRCLE GetCircle(){}

	protected:
	
	    double _x,_y;
};

#endif

