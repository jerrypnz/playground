#ifndef __BULLET_H
#define __BULLET_H

#include "CSprite.h"

class CBullet:public CSprite
{
	public:
		
		enum
		{
			BULLET_RADIUS=1
		};
		
		CBullet(double x,double y,double speed,int color)
		:CSprite(x,y),
  		_speedx(speed),
    	_speedy(1),
     	_color(color)
		{
		}
		
		CBullet()
		{
		}
		
		virtual ~CBullet(){}
  
      	void Init(double x,double y,double speed,int color);
		
		bool HitTest(CSprite* other);
		CIRCLE GetCircle();
	 	void Draw(BITMAP* dest);
		void Update();
		
	private:
	
		int _color;
		double _speedx,_speedy;
};

#endif

