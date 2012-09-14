#include "cbomb.h"

bomb::bomb(float x,float y,float angle,int power):startx(x),starty(y)
{
         _speedx=power*cos(angle);
         _speedy=power*sin(angle);
         time=0.1;
}

bomb::~bomb()
{
}

void bomb::newbomb(float x,float y,float angle,int power)
{
          startx=x;
          starty=y;
          _speedx=power*cos(angle);
          _speedy=power*sin(angle);
          time=0.1;
}

void bomb::animate()
{
          _x=startx+_speedx*time;
          _y=starty-(_speedy*time-0.5*g*time*time);
          time+=0.1;
}

void bomb::draw(BITMAP* dest)
{
          draw_rle_sprite(dest,abomb,(int)_x,(int)_y);
}

void bomb::setrle(RLE_SPRITE *newbomb,RLE_SPRITE *exfrm1,RLE_SPRITE *exfrm2)
{
     abomb=newbomb;
     ex_frame1=exfrm1;
     ex_frame2=exfrm2;
}

void bomb::explode(BITMAP *dest1,BITMAP *dest2)
{
     draw_rle_sprite(dest1,ex_frame1,(int)_x,(int)_y);
     blit(dest1,dest2,0,0,0,0,640,480);
     rest(160);
     draw_rle_sprite(dest1,ex_frame2,(int)_x,(int)_y);
     blit(dest1,dest2,0,0,0,0,640,480);
     rest(200);
}



