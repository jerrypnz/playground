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
		super("������");	
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
		message.append("��Ϸ��ʼ�ˣ�GO��\n");
		control=10;
		
	}
	
	private void buildUI()
	{
		setSize(270,280);
		setResizable(false);
		//�����˵�
		MenuBar bar=new MenuBar();
		Menu game=new Menu("��Ϸ");
		Menu help=new Menu("����");
		startGame=new MenuItem("����");
		exitGame=new MenuItem("�˳�");
		gameRule=new MenuItem("��Ϸ����");
		aboutGame=new MenuItem("����");
		game.add(startGame);
		game.add(exitGame);
		help.add(gameRule);
		help.add(aboutGame);
		bar.add(game);
		bar.add(help);
		setMenuBar(bar);
		//����������
		Label please=new Label("������һ����λ��:");
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
		//���ʱ�������
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
			message.append("��ϲ�����¶��ˣ�̫���ˣ�");
		}
		else
		{
			if(control >= 1)
				message.append(
						guessNumbers[0]+
						guessNumbers[1]+
						guessNumbers[2]+
						guessNumbers[3]+
						"������ǣ�"+guess.getA()+"A"+guess.getB()+"B"+
						"������"+control+"�λ���");
			else 
			{
				int a=guess.getAnswer();
				message.append("�Բ�����û�л����ˡ���ȷ���ǣ�"+a[0]
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

		
