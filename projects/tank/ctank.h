#ifndef TANK_H
#define TANK_H

#include "cbomb.h"

class tank
{
      public:
             tank();
             ~tank();
             void setrle(RLE_SPRITE *frm,int num);
             bool animate();
             void draw(BITMAP* dest);
             void fire(bomb*);
      private:
              float angle;
              int power;
              int frame;
              RLE_SPRITE *frames[6];
};

#endif

