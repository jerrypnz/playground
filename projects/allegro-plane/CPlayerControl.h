#ifndef __PLAYER_H
#define __PLAYER_H

#include "CControl.h"

class CPlayerControl:public CControl
{
	public:
	
		CPlayerControl(int keyUp,int keyDown,int keyLeft,int keyRight)
		:_keyUp(keyUp),
		 _keyDown(keyDown),
		 _keyLeft(keyLeft),
		 _keyRight(keyRight)
		{
		}
		
		CPlayerControl()
		:_keyUp(0),
		 _keyDown(0),
		 _keyLeft(0),
		 _keyRight(0)
		{
		}
		
		void Control();
		void SetKeys(int keyUp,int keyDown,int keyLeft,int keyRight)
		{
			_keyUp = keyUp;
			_keyDown = keyDown;
			_keyLeft = keyLeft;
			_keyRight = keyRight;
		}
	
	private:
	
		int _keyUp,_keyDown,_keyLeft,_keyRight;
};

#endif
