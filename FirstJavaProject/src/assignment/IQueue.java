package assignment;

public interface IQueue<T>
{
	void enqueue(T element);

	T dequeue();

	T front();

	boolean isEmpty();

	String[] getAllElementsAsStrings();
}
