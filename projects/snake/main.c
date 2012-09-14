/*The Snake*/
/*Author:Jerry
/*Date:Feb,unknown,2005
/******Copyright Jerry co.ltd****************/


#include <stdio.h>
#include <stdlib.h>
#include <graphics.h>
#include <bios.h>

#define UP 0x4800
#define DOWN 0x5000
#define LEFT 0x4b00
#define RIGHT 0x4d00
#define PAUSE 0x1970
#define ESC 0x11b/*These macros are used for the keyboard input*/
#define RANDX (random(20)+4)*20
#define RANDY (random(15)+4)*20 /*These macros are used for creating random positions*/




/***********************************************************************************************/
/**********************Type and enum defines****************************************************/




enum direction
{
	EAST,
	WEST,
	SOUTH,
	NORTH
}; /*The enum of the directions*/


enum nodetype
{
	SNAKE,
	FOOD,
	EXFOOD
}; /*The enum of the node type,used in the drawing function*/



typedef struct node
{
	int x,y;    /*The point of the node*/
	struct node *next; /*Pointer to the next node*/
}SnakeNode,*pSnakeNode;



typedef struct
{
	int heading;   /*The direction the snake is facing*/
	int op_heading; /*The opposite of the heading*/
	pSnakeNode head; /*The pointer of the head of the snake*/
	pSnakeNode tail; /*The pointer of the tail of the snake*/
}Snake,*pSnake;


typedef struct
{
	int x,y;/*The position of the food*/
}Food,*pFood;/*The food type definition*/




/***********************************************************************************************/
/**********************Function declarations****************************************************/


void Initialize(); /*Initialize the graphic mode*/
void ShowSplash(); /*Show the splash of the game*/
unsigned int ReadHiscore(FILE*); /*The function to read the highest score*/
void WriteHiscore(FILE*,unsigned int); /*The function to write the highest score*/
void DrawBkground(int,int); /*Draw the back ground*/
void CreateSnake(pSnake,int); /*Create a snake that has n nodes*/
void InsSnakeNode(pSnake); /*Insert a node to the tail of the snake*/
void UpdateSnake(pSnake); /*Update the data of the snake*/
void ShowSnake(pSnake); /*Show the snake on the screen*/
void MoveSnake(pSnake); /*To show the movement of the snake on the screen*/
void ShowScore(int); /*Show the current score on the screen*/
int CheckBite(pSnake); /*Check whether the snake bite itself,the return value is the result*/
void ChangeDirection(pSnake,int); /*Change the direction of the snake.Used when the key is down*/
void FreeSnake(pSnake); /*Free the memory of the snake data structure*/
void DrawNode(int x,int y,int type); /*Draw a node on the screen,the parameter decides what kind of node to draw,a bar(as snake node) or a circle(the food)*/
void EraseNode(int x,int y); /*Erase a node*/
void CreateFood(pFood,pSnake);/*Create a food instance*/
void CreateExFood(pFood,pSnake,pFood);/*Create an extra food */
int CheckEat(pFood,pSnake);/*Check if the snake ate the food,the return value is the result*/
int MainCycle(pSnake,pFood,pFood,int);/*The main cycle of the game,the return value is the score*/
void ShowWellDone(unsigned int);
void ShowGameOver();




/**********************************************************************************************/
/**************************The function definitions********************************************/




int main(void)
{
	pSnake snake;
	pFood food,extra;
	unsigned int score,level,hiscore;
	FILE* fp;

	START:

	clrscr();
	printf("Snake,powered by Jerry's.\n\n");
	printf("Now choose your level:(1 to 9):");
	scanf("%d",&level);
	if(level>9 || level<1) level=6;
	printf("Forgive me!I can't do this in the graphic mode because it is complex and boring......^-^\n");
	printf("Initializing...");
	snake=(pSnake)malloc(sizeof(Snake));
	food=(pFood)malloc(sizeof(Food));
	extra=(pFood)malloc(sizeof(Food));
	printf("Reading hiscore...\n");
	hiscore=ReadHiscore(fp);
	printf("Now,press any key to enter the game!!");
	bioskey(0);
	Initialize();
	ShowSplash();
	DrawBkground(level,hiscore);
	CreateSnake(snake,3);
	CreateFood(food,snake);
	score=MainCycle(snake,food,extra,level);
	if(score>hiscore)
	{
		ShowWellDone(score);
		WriteHiscore(fp,score);
	}
	else ShowGameOver();
	closegraph();

	FreeSnake(snake);
	free(food);
	free(extra);
	clrscr();
	printf("Is it a good game?Do you want to play again?(y or n)\n");
	if( getch()=='y' ) goto START;

	return 0;
}


void Initialize()
{
	int driver=DETECT,mode,result;
	registerbgidriver(EGAVGA_driver);
	initgraph(&driver,&mode,"");/*Initialize the graphic mode*/
	result=graphresult();/*Get the result*/
	if(result!=grOk)
	{
		printf("Graphic initialize failure!Program terminated!\n");
		system("pause");
		exit(-1);/*If fails,exit the program*/
	}
	randomize();
}


void ShowSplash()
{
	int x,y,color,i;
	for(i=0;i<1000;i++)
	{
		x=random(640);
		y=random(480);
		color=random(15);
		putpixel(x,y,color);
	}
	settextjustify(LEFT_TEXT,TOP_TEXT);
	settextstyle(GOTHIC_FONT,0,10);
	setcolor(15);
	outtextxy(80,60,"SNAKE");
	setcolor(5);
	outtextxy(78,58,"SNAKE");
	settextstyle(1,0,2);
	setcolor(LIGHTGREEN);
	outtextxy(20,310,"Author: ");
	outtextxy(20,350,"QQ: 43961207");
	outtextxy(20,390,"E-mail: okjerry@yeah.net");
	outtextxy(20,430,"Blog: http://moonranger.blogcn.com");
	settextstyle(4,0,5);
	setcolor(WHITE);
	outtextxy(102,299,"Jerry");
	setcolor(RED);
	outtextxy(100,297,"Jerry");
	bioskey(0);
	cleardevice();
}


unsigned int ReadHiscore(FILE *fp)
{
	unsigned int temp;
	fp=fopen("score.dat","rb");
	if(!fp)
	{
		printf("Data file not found!\n");
		printf("All the data of the high score lost,and now are you happy?\n");
		fp=fopen("score.dat","wb");
		if(!fp) exit(-1);
		fprintf(fp,"%u",0);
		fclose(fp);
		return 0;
	}
	fscanf(fp,"%u",&temp);
	fclose(fp);
	return temp;
}


void WriteHiscore(FILE *fp,unsigned int score)
{
	fp=fopen("score.dat","wb");
	if(!fp) exit(-1);
	fprintf(fp,"%u",score);
	fclose(fp);
	return;
}



void DrawBkground(int level,int hiscore)
{

	char temp[10];
	setbkcolor(7);
	setcolor(15);
	line(69,69,69,391);
	line(69,69,491,69);
	setcolor(8);
	line(491,391,69,391);
	line(491,391,491,69);/*Draw the main window*/

	setcolor(15);
	line(159,10,391,10);
	line(159,10,159,59);
	setcolor(8);
	line(391,59,391,10);
	line(391,59,159,59);/*Draw the title window*/
	settextstyle(GOTHIC_FONT,0,5);
	setcolor(15);
	outtextxy(200,15,"SNAKE");
	setcolor(5);
	outtextxy(202,13,"SNAKE");


	setcolor(15);
	line(511,69,630,69);
	line(511,69,511,149);
	setcolor(8);
	line(630,149,630,69);
	line(630,149,511,149);/*Draw the window which shows the level*/
	settextstyle(4,0,3);
	setcolor(RED);
	outtextxy(520,71,"Level:");
	sprintf(temp,"  %u",level);
	settextstyle(4,0,4);
	outtextxy(520,99,temp);
	settextstyle(4,0,3);

	setcolor(15);
	line(511,190,630,190);
	line(511,190,511,270);
	setcolor(8);
	line(630,270,630,190);
	line(630,270,511,270);/*Draw the window which shows the current score*/
	setcolor(GREEN);
	outtextxy(520,192,"Score:");
	ShowScore(0);

	setcolor(15);
	line(511,311,630,311);
	line(511,311,511,391);
	setcolor(8);
	line(630,391,630,311);
	line(630,391,511,391);/*Draw the window which shows the highest score*/
	setcolor(YELLOW);
	settextstyle(4,0,3);
	outtextxy(520,313,"Highest:");
	settextstyle(4,0,4);
	sprintf(temp," %u",hiscore);
	outtextxy(520,341,temp);
}


void DrawNode(int x,int y,int type)
{
	switch(type)/*Switch which kind of node to draw*/
	{
		case SNAKE:
		{
			setfillstyle(SOLID_FILL,RED); /*Draw a snake node*/
			bar(x-9,y-9,x+9,y+9);
			break;
		}
		case FOOD:
		{
			setcolor(GREEN);
			setfillstyle(SOLID_FILL,GREEN); /*Draw a food node*/
			circle(x,y,9);
			fillellipse(x,y,9,9);
			break;
		}
		case EXFOOD:
		{
			setcolor(YELLOW);
			setfillstyle(SOLID_FILL,YELLOW); /*Draw a food node*/
			circle(x,y,9);
			fillellipse(x,y,9,9);
			break;
		}
		default:
	 	{
			setfillstyle(SLASH_FILL,GREEN); /*Draw a snake node*/
			bar(x-9,y-9,x+9,y+9);
	 	}
	}
}




void EraseNode(int x,int y) /*Erase a node from the screen*/
{
	setfillstyle(SOLID_FILL,BLACK);
	bar(x-10,y-10,x+10,y+10);
}




void CreateSnake(pSnake snake,int n) /*Create the data structure of the snake*/
{
	pSnakeNode front,back;
	int i;
	snake->heading=EAST; /*The initial direction of the snake is east*/
	snake->op_heading=WEST; /*The opposite direction of the snake*/
	snake->head=snake->tail=(pSnakeNode)malloc(sizeof(SnakeNode));
	snake->head->x=320;
	snake->head->y=240;
	front=back=snake->head;
	for(i=1;i<n+1;i++)
	{
		back->next=(pSnakeNode)malloc(sizeof(SnakeNode));
		back=back->next;
		back->x=front->x-20;
		back->y=front->y;
		if(back->x <= 0) /*The process to deal with the exception*/
		{
			front->next=NULL;
			free(back);
			snake->tail=front;
			return;
		}
		front=back;
	}
	back->next=NULL;
	snake->tail=back;
}





void UpdateSnake(pSnake snake) /*Update the data of the snake data structure*/
{
	int tx1,ty1,tx2=0,ty2=0; /*Temp varibles of the positions*/
	pSnakeNode temp=snake->head; /*Temp pointer,to operate the linked list*/
	tx1=snake->head->x;
	ty1=snake->head->y;
	switch(snake->heading) /*The new position of the snake head is determined by the direction of the snake*/
	{
		case EAST:
		{
			snake->head->x+=20;
			if(snake->head->x >= 500) snake->head->x=80;
			break;
		}
		case WEST:
		{
			snake->head->x-=20;
			if(snake->head->x <= 60) snake->head->x=480;
			break;
		}
		case SOUTH:
		{
			snake->head->y+=20;
			if(snake->head->y >= 400) snake->head->y=80;
			break;
		}
		case NORTH:
		{
			snake->head->y-=20;
			if(snake->head->y <= 60) snake->head->y=380;
			break;
		}
		default:
		{
			snake->head->x+=20;
			if(snake->head->x >= 500) snake->head->x=80;
			break;
		}
	}
	while((temp=temp->next)!=NULL)/*The other nodes of the snake is the same as its front ones*/
	{
		tx2=temp->x;
		ty2=temp->y;
		temp->x=tx1;
		temp->y=ty1;
		tx1=tx2;
		ty1=ty2;
	}
}




void InsSnakeNode(pSnake snake) /*Insert a node to the snake data structure to the tail*/
{
	pSnakeNode temp;
	temp=snake->tail;
	temp->next=(pSnakeNode)malloc(sizeof(SnakeNode));
	temp=temp->next;
	temp->x = temp->y=-100;
	temp->next=NULL;
	snake->tail=temp;
}





void ShowSnake(pSnake snake) /*Show the snake on the screen.Used at the start of the game*/
{
	pSnakeNode temp=snake->head;
	while(temp!=snake->tail) /*Travle the linked list*/
	{
		DrawNode(temp->x,temp->y,SNAKE); /*Draw a snake node on the screen*/
		temp=temp->next;
	}
}





void MoveSnake(pSnake snake) /*Show the movement of the snake on the screen.Used in the game process*/
{
	pSnakeNode temp=snake->head;
	DrawNode(temp->x,temp->y,SNAKE); /*Draw the new position of the snake head*/
	temp=snake->tail;
	EraseNode(temp->x,temp->y); /*Erase the tail from the screen*/
}





int CheckBite(pSnake snake) /*Check if the snake had biten itself,the return value is the result(0 means no,1 means yes)*/
{
	pSnakeNode temp,head; /*The varible head stores the data of the snake head*/
	temp=head=snake->head;
	while( (temp=temp->next)!= NULL )
	{
		if(temp->x== head->x && temp->y== head->y) return 1; /*If the positions are the same,the snake bited itself*/

	}
	return 0;
}




void ChangeDirection(pSnake snake,int key)
{
	int newh,newop_h;
	switch(key)
	{
		case UP: newh=NORTH; newop_h=SOUTH; break;
		case DOWN: newh=SOUTH; newop_h=NORTH; break;
		case LEFT: newh=WEST; newop_h=EAST; break;
		case RIGHT: newh=EAST; newop_h=WEST; break;
		default: break;
	}
	if(newh==snake->op_heading) return;
	snake->heading=newh;
	snake->op_heading=newop_h;
	return;
}



void CreateFood(pFood food,pSnake snake)
{
	pSnakeNode temp=snake->head;
	food->x=RANDX;
	food->y=RANDY;
	while(1)
	{
		if(!temp) break;
		if(food->x == temp->x && food->y == temp->y)
		{
			food->x=RANDX;
			food->y=RANDY;
			temp=snake->head;
		}
		else temp=temp->next;
	}
	return;
}



void CreateExFood(pFood extra,pSnake snake,pFood food)
{
	CreateFood(extra,snake);
	while( extra->x == food->x && extra->y == food->y )  CreateFood(extra,snake);
}



int CheckEat(pFood food,pSnake snake)
{
	int x=snake->head->x;
	int y=snake->head->y;
	if(food->x ==x && food->y ==y)
	{
		EraseNode(food->x,food->y);
		return 1;
	}
	else return 0;
}




void FreeSnake(pSnake snake)
{
	pSnakeNode temp1,temp2;
	temp1=snake->head;
	temp2=temp1->next;
	while(temp2!=NULL)
	{
		free(temp1);
		temp1=temp2;
		temp2=temp2->next;
	}
	free(temp1);
	free(snake);
}



void ShowScore(score)
{
	char temp[10];
	setcolor(GREEN);
	setfillstyle(SOLID_FILL,7);
	bar(515,220,628,268);
	sprintf(temp," %u",score);
	settextjustify(LEFT_TEXT,RIGHT_TEXT);
	settextstyle(GOTHIC_FONT,0,4);
	outtextxy(520,220,temp);
}



int MainCycle(pSnake snake,pFood food,pFood extra,int level)
{
	int key=0;
	int score=0;
	int timer=15;
	int exexist=0;
	int delaytime=500*(10-level);
	ShowSnake(snake);
	DrawNode(food->x,food->y,FOOD);
	while(key!=ESC)
	{
		if(bioskey(1))
		{
			key=bioskey(0);
			if(key==PAUSE) bioskey(0);
			ChangeDirection(snake,key);
		}
		UpdateSnake(snake);
		if( CheckEat(food,snake) )
		{
			InsSnakeNode(snake);
			CreateFood(food,snake);
			DrawNode(food->x,food->y,FOOD);
			score+=(1+level);
			ShowScore(score);
			if(!exexist)
			{
				if(random(4)==1)
				{
					CreateExFood(extra,snake,food);
					DrawNode(extra->x,extra->y,EXFOOD);
					exexist=1;
					timer=15;
				}
			}
		}
		if(exexist)
		{
			if( CheckEat(extra,snake) )
			{
				score+=(12*timer);
				ShowScore(score);
				exexist=0;
			}
			else timer--;
			if(timer==0)
			{
				exexist=0;
				timer=15;
				EraseNode(extra->x,extra->y);
			}
		}
		if( CheckBite(snake) ) break;
		MoveSnake(snake);
		delay(delaytime);
	}
	return score;
}


void ShowWellDone(unsigned int score)
{
	int x,y,color,i;
	char temp[10];
	cleardevice();
	setbkcolor(BLACK);
	for(i=0;i<1000;i++)
	{
		x=random(640);
		y=random(480);
		color=random(15);
		putpixel(x,y,color);
	}
	settextjustify(LEFT_TEXT,TOP_TEXT);
	settextstyle(GOTHIC_FONT,0,10);
	setcolor(15);
	outtextxy(60,60,"Well Done");
	setcolor(5);
	outtextxy(58,58,"Well Done");
	sprintf(temp,"%u",score);
	settextstyle(3,0,1);
	setcolor(GREEN);
	outtextxy(80,250,"You have broken the record,and your score is:");
	setcolor(RED);
	settextstyle(GOTHIC_FONT,0,7);
	outtextxy(480,300,temp);
	bioskey(0);
}



void ShowGameOver()
{
	int x,y,color,i;
	cleardevice();
	setbkcolor(BLACK);
	for(i=0;i<500;i++)
	{
		x=random(640);
		y=random(480);
		color=random(15);
		putpixel(x,y,color);
	}
	settextjustify(LEFT_TEXT,TOP_TEXT);
	settextstyle(GOTHIC_FONT,0,10);
	setcolor(15);
	outtextxy(20,60,"Game Over");
	setcolor(4);
	outtextxy(18,58,"Game Over");
	settextstyle(3,0,1);
	setcolor(YELLOW);
	outtextxy(100,300,"You didn't break the record,try next time!");
	bioskey(0);
}

