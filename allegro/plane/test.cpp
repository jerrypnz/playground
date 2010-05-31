#include <cstdlib>
#include <time.h>
#include "CBullet.h"
#include "CPlayerControl.h"
#include "CMouseControl.h"
#include "CPlane.h"

using namespace std;

//#define USE_MOUSE

#define BULLET_NUM 74

long g_time=0;

void TimerProc()
{
    g_time++;
}

//The allegro main function
int main()
{
	allegro_init();
	if(set_gfx_mode(GFX_AUTODETECT_WINDOWED,400,300,0,0))
	{
		allegro_message("Error setting graphics mode");
		return -1;
	}
	install_keyboard();
	install_timer();
	
	//allegro_message("Screen width and height:%d * %d",SCREEN_W,SCREEN_H);
	
	BITMAP *buffer=create_bitmap(SCREEN_W,SCREEN_H);
	if(!buffer)
	{
		allegro_message("Error creating bitmap");
		return 1;
	}
	
	if(install_sound(DIGI_AUTODETECT,MIDI_WIN32MAPPER,""))
	{
	    allegro_message("Error installing sound");
	    return 2;
	}    
	
	PALETTE the_pal;
	BITMAP *plane = load_bitmap("rocket.bmp",the_pal);
	set_palette(the_pal);
	if(!plane)
	{
	    allegro_message("Error loading image");
	    return 2;
	}    
 
	
	clear_bitmap(buffer);
	clear_bitmap(screen);
	
	//vector<CBullet> bullets;
	CBullet bullets[BULLET_NUM];
	
	#ifdef USE_MOUSE
	CMouseControl player1;
	#else
	CPlayerControl player1(KEY_UP,KEY_DOWN,KEY_LEFT,KEY_RIGHT);
	#endif
	
	CPlane plane1(190,140,plane,&player1);
	MIDI *music = load_midi("back.mid");
	
	play_midi(music,true);
	
	int i;
	bool lose ;
	int count;
	
	gameloop:
	
     
    plane1.SetPos(190,250);     	    
	srand(retrace_count);
	lose = false;
	
	i=0;
	while(i < BULLET_NUM)
	{
		bullets[i].Init(rand()%SCREEN_W,
  						rand()%(SCREEN_H/2),
        				rand()/19000.0-rand()/19000.0,
               			makecol(255,200,0)
               			);
		i++;
	}			
	//allegro_message("About to enter loop!!");
	install_int_ex(TimerProc,MSEC_TO_TIMER(100));
	
	count = retrace_count;
	while( (!key[KEY_ESC]) && (!lose))
	{	
	    for(int j=0;j<BULLET_NUM;j++)
	    {
     		bullets[j].Update();
     		
     		if( bullets[j].HitTest(&plane1)==true )
     			lose = true;
     			
			bullets[j].Draw(buffer);
		}		
		plane1.Update();
		plane1.Draw(buffer);
		blit(buffer,screen,0,0,0,0,SCREEN_W,SCREEN_H);
		clear_bitmap(buffer);
		while(retrace_count - count <=0);
			count=retrace_count;		
	}
	
	remove_int(TimerProc);
	
	textprintf_justify(screen,
 						font,
   						120,
     					220,
          				140,
          				100,
              			makecol(255,255,255),
                 		"Time:%.2f seconds.",
                   		g_time/10.0);
                   		
	textprintf_justify(screen,
						font,
						60,
						260,
						160,
						200,
						makecol(255,255,0),
						"Press ESC to quit,Space to try again."
						);
						
     g_time=0;
     
     {
         
     int choice;
     
     choose:
     choice = readkey()>>8;
     if(choice == KEY_ESC)
     	return 0;
     else if(choice == KEY_SPACE)
     	goto gameloop;
 	 else
 	 	goto choose;
 	 	
  	 }                		
	
	destroy_bitmap(buffer);
	stop_midi();
	destroy_midi(music);
	return 0;
	
}
END_OF_MAIN()
