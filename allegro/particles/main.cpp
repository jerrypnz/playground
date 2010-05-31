#include <allegro.h>
#include "Particle.h"

Particle particles[200];

int main()
{
    allegro_init();
    install_keyboard(); 
    if (set_gfx_mode(GFX_AUTODETECT, 320, 200, 0, 0) != 0) 
    {
      if (set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0) != 0) 
      {
	       allegro_message("Error setting graphics mode\n%s\n", allegro_error);
	       return 1;
      }
    }
    PALETTE palette;
    
    
}
END_OF_MAIN()    
