#include <stdio.h>
#include <conio.h>
#include <bios.h>
#include "C:\project\bomb\kernel.c"
#define TAG -2
#define UP 0x4800
#define DOWN 0x5000
#define LEFT 0x4b00
#define RIGHT 0x4d00
#define ESC 0x11b
#define Z 0x2c7a
#define X 0x2d78

void output(char bomb[9][9])
{
	int i,j;
	for(i=0;i<9;i++)
	{
		for(j=0;j<9;j++)
		{
			if(bomb[i][j]==BOMB) printf(" * ");
			else
			{
				if(bomb[i][j]==0) printf(" \1 ");
				else printf(" %d ",bomb[i][j]);
			}
		}
		putchar('\n');
	}
	return;
}


void showbk()
{
	int i,j;
	for(i=0;i<9;i++)
	{
		for(j=0;j<9;j++)
		{
			printf("# ");
		}
		putchar('\n');
	}
	return;
}


void show(int x,int y,char status)
{
	int i,j;
	i=2*x+1;
	j=y+1;
	gotoxy(i,j);
	switch(status)
	{
		case BOMB: cprintf("*");gotoxy(i,j); break;
		case 0: cprintf("\1");gotoxy(i,j); break;
		case TAG:textcolor(RED);cprintf("@");textcolor(WHITE);gotoxy(i,j);break;
		default: cprintf("%d",status);gotoxy(i,j);
	}

	return;
}


main()
{
	char bomb[9][9],status[9][9];
	int key,x,y,result;
	clrscr();
	printf("******************************************\n");
	printf("**** Bomb for DOS in the text mode *******\n");
	printf("************************ by Jerry ********\n");
	printf("\n\nNot knowing how to play?\n\n");
	printf("Up,down,left,right arrow-----move the cursor\n");
	printf("Z-----open the current block\n");
	printf("X-----make a bomb tag\n");
	printf("\n\npress any key to continue...\n");
	getch();
	clrscr();

	START:

	key=0;
	x=1;
	y=1;
	result=0;
	clrscr();
	memset( (void*)status,0,81);
	init(bomb);
	showbk();
	gotoxy(x,y);
	while(key!=ESC)
	{
		if( bioskey(1) )
		{
			key=bioskey(0);
			switch(key)
			{
			 case UP:y-=1;if(y<1) y=1;gotoxy(x,y);break;
			 case DOWN:y+=1;if(y>9) y=9;gotoxy(x,y);break;
			 case LEFT:x-=2;if(x<1) x=1;gotoxy(x,y);break;
			 case RIGHT:x+=2;if(x>18) x=17;gotoxy(x,y);break;
			 case Z:
			 {
				result=click(bomb,status,(x-1)/2,y-1,show);

				if( check(bomb,status) )
				{

					gotoxy(1,11);
					printf("You won!!\n");
					goto END;

				}

				if( result )
				{

					gotoxy(1,11);
					printf("You lose!!\n");
					goto END;

				}
				break;
			 }
			 case X:show((x-1)/2,y-1,TAG);break;
			 default:break;
			}
		}

	}

	END:

	getch();
	clrscr();
	printf("Play again?(y/n)\n");
	if(getch()=='y') goto START;
	return 0;
}