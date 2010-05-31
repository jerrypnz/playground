#include "ctank.h"

tank::tank()
{
           frame=0;
           angle=0;
           power=20;
}

tank::~tank()
{
}

void tank::setrle(RLE_SPRITE *frm,int num)
{
             if(num<0 || num>5) return;
             frames[num]=frm;
}

bool tank::animate()
{
              if(key[KEY_UP]) angle+=3;
              if(key[KEY_DOWN]) angle-=3;
              if(key[KEY_LEFT]) power-=2;
              if(key[KEY_RIGHT]) power+=2;
              if(angle>59) angle=59;
              if(angle<0) angle=0;
              if(power>250) power=250;
              if(power<10) power=10;
              frame=(int)(angle/10);
              if(key[KEY_SPACE]) return true;
              else return false;
}

void tank::draw(BITMAP *dest)
{
               draw_rle_sprite(dest,frames[frame],10,400);
               rectfill(dest,10,380,10+power,390,20);
}

void tank::fire(bomb *thebomb)
{
      thebomb->newbomb(53,425,3.14*angle/180,power);
}



