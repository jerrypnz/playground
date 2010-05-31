#include "CMouseControl.h"
#include "CPlane.h"

void CMouseControl::Control()
{
    if(!_plane)
    	return;
    if(mouse_x>_curX)
    	_plane->MoveRight();
	else if(mouse_x<_curX)
		_plane->MoveLeft();
		
	if(mouse_y>_curY)
		_plane->MoveDown();
	else if(mouse_y<_curY)
		_plane->MoveUp();
		
	_curX=mouse_x;
	_curY=mouse_y;
}	
