#ifndef HEART_H
#define HEART_H

#include <allegro.h>

class heart
{
 public:
        heart();
        ~heart();
        void animate();
        void setbitmap(BITMAP *frm1,BITMAP *frm2);
        void speedup();
        void heartbreak();
        void draw(BITMAP*);
        int x(){return _x+23;}
        int y(){return _y+23;}
 private:
         int _x;
         int _y;
         int _speedx;
         int _speedy;
         int angle;
         BITMAP *frame1;
         BITMAP *frame2;
         BITMAP *current;
};
#endif

