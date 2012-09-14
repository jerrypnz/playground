#include "cheart.h"

heart::heart()
{
 _x=300+rand()%250;
 _y=rand()%40;
 _speedx=1;
 _speedy=1;
}

heart::~heart()
{
}

void heart::setbitmap(BITMAP *frm1,BITMAP *frm2)
{
 frame1=frm1;
 frame2=frm2;
 current=frame1;
}

void heart::animate()
{
 _x+=_speedx;
 _y+=_speedy;
 if(_x>600 || _x<300) _speedx*=-1;
 if(_y>449 || _y<1) _speedy*=-1;
 angle+=4;
}

void heart::speedup()
{
 _speedx+=rand()%2+1;
 _speedy+=rand()%2+1;
}

void heart::draw(BITMAP *dest)
{
 rotate_sprite(dest,current,_x,_y,itofix(angle));
}

void heart::heartbreak()
{
 current=frame2;
}

