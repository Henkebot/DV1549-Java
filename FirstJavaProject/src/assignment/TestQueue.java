package assignment;

public class TestQueue
{
	final static int ARRAY_SIZE = 10;
	public static void main(String[] args)
	{
		Queue<Integer> intQ = new Queue<>();
		
		for (int i = 0; i < ARRAY_SIZE; i++)
		{
			intQ.enqueue(i);
		}
		
		System.out.println("isEmpty() test:");
		System.out.println("Expect false:\t " + intQ.isEmpty());
		System.out.println("Dequeue all elements..");
		for (int i = 0; i < ARRAY_SIZE; i++)
		{
			System.out.print(intQ.dequeue() + " ");
		}
		
		System.out.println("\nExpect true:\t" + intQ.isEmpty());
		System.out.println("Testing toString method");

		for (int i = 0; i < ARRAY_SIZE; i++)
		{
			intQ.enqueue(i);
		}
		
		String[] str = intQ.getAllElementsAsStrings();
		
		for (int i = 0; i < str.length; i++)
		{
			System.out.print(str[i] + " ");
		}
		
		intQ.enqueue(99);
		str = intQ.getAllElementsAsStrings();
		System.out.println();
		for (int i = 0; i < str.length; i++)
		{
			System.out.print(str[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < ARRAY_SIZE; i++)
		{
			System.out.print(intQ.dequeue() + " ");
		}
		System.out.println();
		System.out.println(intQ.front());
		
		
		
		
		
		
	}

}
