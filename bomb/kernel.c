#ifndef _MINE
#define _MINE

#include <stdlib.h>
#include <mem.h>

#define BOMB -1
#define OVER 1

void init(char bomb[9][9])
{
	int num=0,i,j,ii,jj;
	randomize();
	memset( (void*)bomb, 0, 81);
	while(num<10)
	{
		i=random(8);
		j=random(8);
		if(bomb[i][j]!=BOMB)
		{
			bomb[i][j]=BOMB;
			num++;
			for(ii=i-1;ii<=i+1;ii++)
			{
			 for(jj=j-1;jj<=j+1;jj++)
			  {
				 if(bomb[ii][jj]==BOMB) continue;
				 if(ii<0 || jj<0) continue;
				 if(ii>8 || jj>8) continue;
			     	 bomb[ii][jj]++;
			  }
			}

		}
	}
	return;
}




int click(char bomb[9][9],char status[9][9],int x,int y,void (*show)(int,int,char) )
{
	int i,j;


	if(bomb[x][y]==BOMB)
	{
	   (*show)(x,y,BOMB);
	   return OVER;
	}


	if(bomb[x][y])
	{
	   (*show)(x,y,bomb[x][y]);
	   status[x][y]=1;
	   return 0;
	}

	else
	{
	   if(!status[x][y])
	   {
		(*show)(x,y,0);
		status[x][y]=1;

		for(i=x-1;i<=x+1;i++)
		{
		  for(j=y-1;j<=y+1;j++)
			{
			 if(i==x && j==y) continue;
			 if(i<0 || i>8) continue;
			 if(j<0 || j>8) continue;
			 click(bomb,status,i,j,show);
			}
		}

	   }
	   return 0;
	}

}



int check(char bomb[9][9],char status[9][9])
{
	int i,j;
	for(i=0;i<9;i++)
	{
		for(j=0;j<9;j++)
		{
			if(bomb[i][j]!=BOMB && status[i][j]==0) return 0;
		}
	}
	return 1;
}




#endif
