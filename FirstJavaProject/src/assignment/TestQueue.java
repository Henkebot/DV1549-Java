package assignment;

public class TestQueue
{
	final static int ARRAY_SIZE = 10;
	public static void main(String[] args)
	{
		Queue<Integer> intQ = new Queue<>();
		intQ.enqueue(20);
		System.out.println(intQ.getAllElementsAsStrings().length);
		intQ.dequeue();
		intQ.enqueue(203);
		System.out.println(intQ.getAllElementsAsStrings().length);
		
	}

}
