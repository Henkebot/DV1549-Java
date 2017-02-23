package lektionEtt;
import java.util.Random;

public class FörstaUppgiften
{
	// Bounds
	private static final int LOWER = 100;
	private static final int HIGHER = 400;

	// Generate random numbers with Math.Random()
	private static void generateRandomNrWithMath(int[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = (int) (Math.random() * (HIGHER - LOWER)) + LOWER;
		}
	}

	// Generate random numbers with Random-object
	private static void generateRandomNrWithObj(int[] array)
	{
		Random rand = new Random();
		for (int i = 0; i < array.length; i++)
		{
			array[i] = rand.nextInt(HIGHER - LOWER) + LOWER;
		}
	}

	// Prints the array
	private static void printArray(int[] array)
	{
		for (int i : array)
		{
			System.out.print(i + " ");
		}
	}

	// Counts how many numbers who are greater than 175
	private static int calculate(int[] array)
	{
		int NrOfHighNr = 0;
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] > 175)
				NrOfHighNr++;
		}

		return NrOfHighNr;
	}

	// Doubles every second indexs value
	private static void doubleEven(int[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (i % 2 == 0)
				array[i] *= 2;
		}
	}

	public static void main(String[] args)
	{
		int[] myArray = new int[10];
		int[] myArray2 = new int[10];

		generateRandomNrWithMath(myArray);
		generateRandomNrWithObj(myArray2);

		printArray(myArray);
		System.out.println();
		printArray(myArray2);

		System.out.println("\nHow many are greater than 175? " + calculate(myArray) + "\n");

		doubleEven(myArray);

		printArray(myArray);

	}
}
