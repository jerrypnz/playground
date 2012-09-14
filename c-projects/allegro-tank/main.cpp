#include <allegro.h>
#include "tank.h"
#include "cbomb.h"
#include "ctank.h"
#include "cheart.h"
#include <stdio.h>

DATAFILE *thedata;
tank thetank;
bomb thebomb(0,0,0,0);
heart theheart;

int initgame()
{
    allegro_init();
    install_keyboard();
    install_timer();

    if(set_gfx_mode(GFX_AUTODETECT,640,480,0,0)!=0)
    {
           allegro_message("Graphic initialize error!");
           return 1;
    }

    if (install_sound(DIGI_AUTODETECT, MIDI_AUTODETECT,"") != 0)
    {
      		allegro_message("Error ocured in initializing sound device.\n");
      		return 1;
   	}
    return 0;

}

void initobj()
{
     for(int i=0;i<6;i++)
     {
            thetank.setrle((RLE_SPRITE*)thedata[8+i].dat,i);
     }
     thebomb.setrle((RLE_SPRITE*)thedata[BOMB].dat,(RLE_SPRITE*)thedata[EXPLODE1].dat,(RLE_SPRITE*)thedata[EXPLODE2].dat);
     theheart.setbitmap((BITMAP*)thedata[HEART1].dat,(BITMAP*)thedata[HEART2].dat);
}

void gameover(int score)
{
     char msg[100];
     sprintf(msg,"Game over!Your score is %d.Challege next time!^_^",score);
     textout_centre(screen,font,msg,320,240,20);
}


int main(int argc,char *argv[])
{
    if(initgame()) return 1;
    char name[256];
    replace_filename(name,argv[0],"tank.dat",sizeof(name));
    thedata=load_datafile(name);
    if(!thedata)
    {
                set_gfx_mode(GFX_TEXT,0,0,0,0);
                allegro_message("Error open data file!");
                return 1;
    }

    set_palette((PALETTE)thedata[THEPAL].dat);

    initobj();


    BITMAP *buffer;
    buffer=create_bitmap(640,480);

    if(!buffer) return 1;

    clear_bitmap(buffer);
    clear_bitmap(screen);

    acquire_screen();

    int flag=0;
    int mark=0;
    int bombs=10;
    play_midi((MIDI*)thedata[MUSIC].dat,1);
    char msg1[20];
    char msg2[20];
    sprintf(msg1,"Mark:%d",mark);
    sprintf(msg2,"Bombs:%d",bombs);

    while(1)
    {

            blit(buffer,screen,0,0,0,0,640,480);
            clear_bitmap(buffer);
            blit((BITMAP*)thedata[BACK].dat,buffer,0,0,0,0,640,480);
            textout(buffer,font,msg1,10,10,30);
            textout(buffer,font,msg2,10,20,30);
            theheart.animate();
            theheart.draw(buffer);
           if(flag==0)
           {
                      if(thetank.animate() )
                      {
                            thetank.fire(&thebomb);
                            bombs--;
                            flag=1;
                            sprintf(msg2,"Bombs:%d",bombs);
                      }
                      thetank.draw(buffer);
            }
            else
            {
                       thetank.draw(buffer);
                       thebomb.animate();
                       int tempx=thebomb.x();
                       int tempy=thebomb.y();
                       int hx=theheart.x();
                       int hy=theheart.y();
                       if(ABS(hx-tempx)<23.0 && ABS(hy-tempy)<23.0)
                       {
                             play_sample((SAMPLE*)thedata[EXPLODE].dat,200,255,1000,0);
                             thebomb.explode(buffer,screen);
                             theheart.speedup();
                             mark+=10;
                             bombs+=2;
                             sprintf(msg1,"Mark:%d",mark);
                             flag=0;
                       }
                       else if(bombs<1)
                       {
                            gameover(mark);
                            readkey();
                            stop_midi();
                            release_screen();
                            allegro_exit();
                            return 0;
                       }
                       if(tempy>440 || tempx>610 || tempy<12)
                       {
                                play_sample((SAMPLE*)thedata[EXPLODE].dat,240,255,1000,0);
                                thebomb.explode(buffer,screen);
                                flag=0;
                       }
                       thebomb.draw(buffer);
             }
             if(key[KEY_ESC]) break;
    }

    release_screen();
    allegro_exit();
    return 0;
}
END_OF_MAIN();


