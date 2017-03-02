package assignment;

public class Queue<T> implements IQueue<T>
{
	private Object[] m_Array;
	private int m_StartIndex;
	private int m_EndIndex;
	
	public Queue()
	{
		m_Array = new Object[10];
		m_StartIndex = 0;
		m_EndIndex = 0;
		
	
	}
	
	private int nrOfElements()
	{
		int size = 0;
		
		if(m_StartIndex < m_EndIndex)
			size = m_EndIndex + 1;
		else if(m_StartIndex > m_EndIndex)
			size = (m_Array.length - m_StartIndex) + m_EndIndex + 1;
		
		return size;
	}
	
	@Override
	public void enqueue(T element)
	{
		if(m_Array.length == (nrOfElements()-1) 
				|| m_StartIndex == m_EndIndex + 1) // kollar om nästa endIndex är samma som startIndex
			expand();
		m_Array[m_EndIndex++ % m_Array.length] = element;
		
	}
	
	private void expand()
	{
		
		Object[] temp = new Object[m_Array.length * 2];
		int arraySize = nrOfElements() - 1;
		
		int newIndex = 0;
		for (; newIndex < arraySize; newIndex++)
		{
			temp[newIndex] = m_Array[(m_StartIndex++ % m_Array.length)];
		}
		
		m_Array = temp;
		m_StartIndex = 0;
		m_EndIndex = newIndex;
	}

	@Override
	public T dequeue()
	{
		
		@SuppressWarnings("unchecked")
		T elm = (T)m_Array[m_StartIndex];
		m_Array[m_StartIndex++] = null;
		return elm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T front()
	{
		return (T) m_Array[m_StartIndex];
	}

	@Override
	public boolean isEmpty()
	{
		
		return nrOfElements() == 0;
	}

	@Override
	public String[] getAllElementsAsStrings()
	{
		int arraySize = nrOfElements() -1;
		int tempStartIndex = m_StartIndex;
		String[] strArray = new String[arraySize];
		
		for (int newIndex = 0; newIndex < arraySize ; newIndex++)
		{
			strArray[newIndex] = m_Array[(tempStartIndex++ % m_Array.length)].toString();
		}
		
		return strArray;
	}

}
