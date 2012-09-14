#ifndef __PLANE_H
#define __PLANE_H

#include "CSprite.h"
#include "CControl.h"

class CPlane:public CSprite
{
	public:
	
		CPlane()
		:CSprite(0,0),_pic(0),_controller(0),SPEED_PLANE(1)
		{
		}
		
		
		CPlane(double _x,double _y,BITMAP *pic,CControl* controller)
		:CSprite(_x,_y),_pic(pic),_controller(controller),SPEED_PLANE(1)
		{
			_controller->SetTargetObject(this);
			_cx=pic->w;
			_cy=pic->h;
		}
		
		virtual ~CPlane(){}
		
		void SetPos(double x,double y)
		{
		    _x = x;
		    _y = y;
		}    
		
		void SetSpeed(double speed)
		{
		    SPEED_PLANE = speed;
		}    
		    
	
		void MoveUp()
		{
			_y-=SPEED_PLANE;
			if(_y<=0)
				_y=0;
		}
		
		void MoveDown()
		{
			_y+=SPEED_PLANE;
			if(_y>=SCREEN_H-_cy)
				_y=SCREEN_H-_cy;
		}
		
		void MoveLeft()
		{
			_x-=SPEED_PLANE;
			if(_x<=0)
				_x=0;
		}
		
		void MoveRight()
		{
			_x+=SPEED_PLANE;
			if(_x>=SCREEN_W-_cx)
				_x=SCREEN_W-_cx;
		}
			

		void Init(double x,double y,BITMAP *pic,CControl *controller);
		void Draw(BITMAP* dest);
		CIRCLE GetCircle();
		void Update();
		bool HitTest(CSprite* other);
			
	private:
	
		CControl *_controller;
		BITMAP *_pic;
		int _cx,_cy;
		double SPEED_PLANE;
		
};

#endif

