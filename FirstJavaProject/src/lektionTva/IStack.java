package lektionTva;
//Alla metoder i interfacet �r abstrakta och publika
public interface IStack<T>
{
	void push(T item);
	T pop();
	T peek();
	boolean isEmpty();
}
