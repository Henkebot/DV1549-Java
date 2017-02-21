
public class FirstProgram
{
	// MAX_VALUE blir kursivt, för det är en klass tillhörighet
	public static int MAX_VALUE = 800;

	// Använder final, det är som const
	public static final int MIN_VALUE = 200;

	public static void main(String[] args)
	{
		// System är en klass, alla klasser börjar med stor bokstav
		// . man går in i klassen
		// out är en klass variabel
		// println är en instansvariabel
		System.out.println("Java i Super lagom");

		// Skapar en array med initlista
		int[] numbers = { 1, 2, 3, 4 };
		// Skapar en array med storlek 10, alla värden är 0
		int[] values = new int[10];

		// Skriver ut innehållet med hjälp av en for-loop
		System.out.print("Innehållet i numbers är: ");
		for (int i = 0; i < numbers.length; i++)
		{
			// + istället för << i c++
			System.out.print(numbers[i] + " ");
		}

		// Tilldelning
		values[3] = MAX_VALUE;

		// Skriver ut innehållet med hjälp av en for-each
		System.out.print("\n\nInnehållet i values är: ");
		for (int theValue : values)
		{
			System.out.print(theValue + " ");
		}

	}

}
