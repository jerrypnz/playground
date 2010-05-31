#ifndef __MOUSE_H
#define __MOUSE_H

#include "CControl.h"
#include <allegro.h>

class CMouseControl :public CControl
{
    public:
        
        CMouseControl()
        {
            install_mouse();
            _curX=mouse_x;
            _curY=mouse_y;
            scare_mouse();
            set_mouse_speed(6,6);
        }    
        
        void Control();
        
    private:
        
        int _curX,_curY;
};

#endif    
