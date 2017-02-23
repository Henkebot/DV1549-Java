package lektionTva;
//Alla metoder i interfacet är abstrakta och publika
public interface IStack<T>
{
	void push(T item);
	T pop();
	T peek();
	boolean isEmpty();
}
