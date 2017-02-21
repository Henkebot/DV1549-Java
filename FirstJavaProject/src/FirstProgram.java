
public class FirstProgram
{
	// MAX_VALUE blir kursivt, f�r det �r en klass tillh�righet
	public static int MAX_VALUE = 800;

	// Anv�nder final, det �r som const
	public static final int MIN_VALUE = 200;

	public static void main(String[] args)
	{
		// System �r en klass, alla klasser b�rjar med stor bokstav
		// . man g�r in i klassen
		// out �r en klass variabel
		// println �r en instansvariabel
		System.out.println("Java i Super lagom");

		// Skapar en array med initlista
		int[] numbers = { 1, 2, 3, 4 };
		// Skapar en array med storlek 10, alla v�rden �r 0
		int[] values = new int[10];

		// Skriver ut inneh�llet med hj�lp av en for-loop
		System.out.print("Inneh�llet i numbers �r: ");
		for (int i = 0; i < numbers.length; i++)
		{
			// + ist�llet f�r << i c++
			System.out.print(numbers[i] + " ");
		}

		// Tilldelning
		values[3] = MAX_VALUE;

		// Skriver ut inneh�llet med hj�lp av en for-each
		System.out.print("\n\nInneh�llet i values �r: ");
		for (int theValue : values)
		{
			System.out.print(theValue + " ");
		}

	}

}
