import java.awt.*;
import java.awt.event.*;

public class GuessFrame extends Frame implements ActionListener
{	
	MenuItem startGame;
	MenuItem exitGame;
	MenuItem gameRule;
	MenuItem aboutGame;
	Button ok;
	TextField one;
	TextField two;
	TextField three;
	TextField four;
	TextArea message;
	ImageCanvas image;
	Guess guess;
	int control;
	boolean gameOver;
	
	GuessFrame()
	{
		super("猜数字");	
		guess=new Guess();
		startGame();
		buildUI();
		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});
	}
	
	private void startGame()
	{
		guess.init();
		message.setText("");
		message.append("游戏开始了，GO！\n");
		control=10;
		
	}
	
	private void buildUI()
	{
		setSize(270,280);
		setResizable(false);
		//建立菜单
		MenuBar bar=new MenuBar();
		Menu game=new Menu("游戏");
		Menu help=new Menu("帮助");
		startGame=new MenuItem("开局");
		exitGame=new MenuItem("退出");
		gameRule=new MenuItem("游戏规则");
		aboutGame=new MenuItem("关于");
		game.add(startGame);
		game.add(exitGame);
		help.add(gameRule);
		help.add(aboutGame);
		bar.add(game);
		bar.add(help);
		setMenuBar(bar);
		//建立主界面
		Label please=new Label("请输入一个四位数:");
		image = new ImageCanvas("comeon.gif");
		setLayout(null);
		ok=new Button("OK");
		one=new TextField();
		two=new TextField();
		three=new TextField();
		four=new TextField();
		message=new TextArea();
		one.setColumns(1);
		two.setColumns(1);
		three.setColumns(1);
		four.setColumns(1);
		add(please);
		add(ok);
		add(one);
		add(two);
		add(three);
		add(four);
		add(message);
		add(image);
		please.setBounds(10,60,100,20);
		one.setBounds(10,85,25,25);
		two.setBounds(40,85,25,25);
		three.setBounds(70,85,25,25);
		four.setBounds(100,85,25,25);
		ok.setBounds(130,85,60,25);
		image.setBounds(205,60,50,50);
		message.setBounds(10,120,250,150); 
		//添加时间监听器
		ok.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int[] guessNumbers=new int[4];
		guessNumbers[0]=Integer.parseInt(one.getText());
		guessNumbers[1]=Integer.parseInt(two.getText());
		guessNumbers[2]=Integer.parseInt(three.getText());
		guessNumbers[3]=Integer.parseInt(four.getText());
		guess.compare(guessNumbers);
		control--;
		if(guess.getA="4")
		{
			message.append("恭喜您，猜对了！太棒了！");
		}
		else
		{
			if(control >= 1)
				message.append(
						guessNumbers[0]+
						guessNumbers[1]+
						guessNumbers[2]+
						guessNumbers[3]+
						"，结果是："+guess.getA()+"A"+guess.getB()+"B"+
						"您还有"+control+"次机会");
			else 
			{
				int a=guess.getAnswer();
				message.append("对不起，您没有机会了。正确答案是："+a[0]
						+a[1]
						+a[2]
						+a[3]);
			}
				
		}
		one.setText("");
		two.setText("");
		three.setText("");
		four.setText("");
		image.changeImage("10.gif");
	}
	
	public static void main(String args[])
	{
		GuessFrame f=new GuessFrame();
		f.setVisible(true);
	}	
	
}

class ImageCanvas extends Canvas  
{  
	private Image i;  
	//Image buffer;
	public ImageCanvas(String imageName)  
	{  
		i = Toolkit.getDefaultToolkit().getImage(imageName);  
		MediaTracker mt = new MediaTracker(this);  
		try  
		{  
			mt.addImage(i,0);  
			mt.waitForID(0); 
		}  
		catch(InterruptedException e)  
		{  
			e.printStackTrace();  
		}  
	}  
	
	public void changeImage(String imageName)
	{
		i = Toolkit.getDefaultToolkit().getImage(imageName);  
		MediaTracker mt = new MediaTracker(this);  
		try  
		{  
			mt.addImage(i,0);  
			mt.waitForID(0); 
		}  
		catch(InterruptedException e)  
		{  
			e.printStackTrace();  
		}  
	}

	public void paint(Graphics g)  
	{    
		g.drawImage(i,0,0,this);
	}  

	public void update(Graphics g) 
	{
		paint(g);
	} 

	public Dimension getPreferredSize()  
	{  
		int w = i.getWidth(this);  
		int h = i.getHeight(this);  
		return new Dimension(w,h);  
	}  
}  

		
