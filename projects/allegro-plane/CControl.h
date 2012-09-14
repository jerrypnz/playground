#ifndef __CONTROL_H
#define __CONTROL_H

class CPlane;

class CControl
{
	public:
	
		CControl()
		:_plane(0)
		{
		}
	
		CControl(CPlane *plane)
		:_plane(plane)
		{
		}
		
		void SetTargetObject(CPlane *plane)
		{
			_plane = plane;
		}
	
		virtual void Control()=0;
		
	protected:
	
		CPlane *_plane;
};

#endif

