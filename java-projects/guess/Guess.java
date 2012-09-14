import java.util.Random;

public class Guess
{
	private int[] numbers = new int[4];
	private int a;
	private int b;
	
	public void init()
	{
		Random r = new Random();
		for(int i=0;i<4;i++)
		{	
			int temp = r.nextInt(10);
			int j = 0;
			while(j<i)
			{
				if(temp == numbers[j])
				{
					temp = r.nextInt(10);
					j=0;
				}
				else
					j++;
			}
			numbers[i] = temp;
		}
	}
	
	public void compare(int[] num)
	{
		a = 0;
		b = 0;
		if(num.length != 4)
			return;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(numbers[i] == num[j])
				{
					if(i == j)
						a++;
					else
						b++;
				}
			}
		}
	}
	
	public int getA()
	{
		return a;
	}
	
	public int getB()
	{
		return b;
	}
	
	public int[] getAnswer()
	{
		return numbers;
	}
	
	public void printNumbers()
	{
		for(int i=0;i<4;i++)
			System.out.print(numbers[i] + " ");
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		Guess g = new Guess();
		int a[] = new int[4];
		a[0]=2;a[1]=4;a[2]=1;a[3]=3;
		for(int i=0;i<5;i++)
		{
			g.init();
			g.printNumbers();
			g.compare(a);
			System.out.println("A:" + g.getA() + " B:" + g.getB());
		}
	}
	
}