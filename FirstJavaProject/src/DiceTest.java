
public class DiceTest
{
	public static void main(String[] args)
	{
		Dice myDice = new Dice(6);
		
		System.out.println(myDice);
		for (int i = 0; i < 10; i++)
		{
			myDice.toss();
			
			System.out.println(myDice);
			
		}
	}
}
