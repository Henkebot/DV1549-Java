package assignment;

public class TestQueue
{
	final static int ARRAY_SIZE = 1000;

	public static void main(String[] args)
	{
		Queue<Integer> intQ = new Queue<>();
		for (int i = 0; i < ARRAY_SIZE; i++)
		{
			intQ.enqueue(i);
		}
		for (int i = 0; i < intQ.getAllElementsAsStrings().length; i++)
		{
			System.out.println(intQ.getAllElementsAsStrings()[i]);
		}
		for (int i = 0; i < ARRAY_SIZE; i++)
		{
			System.out.println(intQ.dequeue());
		}

	}

}
