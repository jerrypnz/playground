#ifndef BOMB_H
#define BOMB_H

#include <allegro.h>
#include <math.h>

const float g=9.8;

class bomb
{
 public:
        bomb(float x,float y,float angle,int power);
        ~bomb();
        void newbomb(float x,float y,float angle,int power);
        void animate();
        void draw(BITMAP*);
        void explode(BITMAP*,BITMAP*);
        void setrle(RLE_SPRITE *newbomb,RLE_SPRITE *exfrm1,RLE_SPRITE *exfrm2);
        int x(){return (int)_x;}
        int y(){return (int)_y;}
 private:
         float time;
         float _x,_y;
         float _speedx,_speedy;
         float startx,starty;
         RLE_SPRITE *abomb;
         RLE_SPRITE *ex_frame1;
         RLE_SPRITE *ex_frame2;
};
#endif

