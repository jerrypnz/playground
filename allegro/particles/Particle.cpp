#include "Particle.h"
#include <allegro.h>

void Particle::Update()
{
    m_x += m_vx;
    m_y += m_vy;
    
    if(m_life > 0)
    	m_life -- ;
    	
}

void Particle::Draw(BITMAP *buffer)
{
    putpixel(buffer , (int)m_x , (int)m_y , m_color);
}    
