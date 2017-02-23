package lektionTva;
//Det är ett arv, men när det är interface så blir det implements
public class Stack<T> implements IStack<T>
{
	private Object[] elements;
	private int nrOfElements;
	
	public Stack()
	{
		elements = new Object[10];
		nrOfElements = 0;
	}
	@Override
	public void push(T item)
	{
		elements[nrOfElements++] = item;
		
		
	}

	@Override
	public T pop()
	{
		return (T) elements[--nrOfElements];
	}

	@Override
	public T peek()
	{
		return (T)elements[nrOfElements - 1];
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
