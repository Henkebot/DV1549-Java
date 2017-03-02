package assignment;

public class TestQueue
{
	final static int ARRAY_SIZE = 10;
	public static void main(String[] args)
	{
		Queue<Integer> intQ = new Queue<>();
		for (int i = 0; i < 100; i++)
		{
			intQ.enqueue(i);
			
		}
		String[] test = intQ.getAllElementsAsStrings();
		for (int i = 0; i < test.length; i++)
		{
			System.out.print(test[i] + " ");
			
		}
		System.out.println();
		
		for (int i = 0; i < 100; i++)
		{
			System.out.print(intQ.dequeue() + " ");
		}
		
	}

}
