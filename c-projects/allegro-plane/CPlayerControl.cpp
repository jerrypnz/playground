#include "CPlayerControl.h"
#include "CPlane.h"

#include <allegro.h>

void CPlayerControl::Control()
{
	if(key[_keyUp])
		_plane->MoveUp();
	else if(key[_keyDown])
		_plane->MoveDown();
		
	if(key[_keyLeft])
		_plane->MoveLeft();
	else if(key[_keyRight])
		_plane->MoveRight();
}

