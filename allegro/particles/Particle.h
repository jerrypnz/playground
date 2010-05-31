#ifndef _PARTICLE_H
#define _PARTICLE_H

struct BITMAP;

class Particle
{
	private:
		
		double m_x;
		double m_y;
		double m_vx;
		double m_vy;
		int m_color;
		int m_life;
		
	public:
		
		Particle()
		:m_x(0),m_y(0),m_vx(0),m_vy(0),m_color(0),m_life(0)
		{}
		
		Particle(double x,double y,double vx,double vy,int color,int life)
		:m_x(x),m_y(y),m_vx(vx),m_vy(vy),m_color(color),m_life(life)
		{}
		
		~Particle(){}
		
		void Born(double x,double y,double vx,double vy,int color,int life)
		{
		    m_x = x;
		    m_y = y;
		    m_vx = vx;
		    m_vy = vy;
		    m_color = color;
		    m_life = life;
  		}    
		bool IsDead() { return m_life<=0;}
		int GetColor() { return m_color;}
		void SetColor(int color) { this->m_color = color; }
		void Update();
		void Draw(BITMAP *buffer);
};

#endif //_PARTICLE_H
