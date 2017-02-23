package lektionEtt;
import java.util.Random;
import java.util.Scanner;

public class SomeInputTesting
{
	public static void main(String[] args)
	{
		// Skapar ett Scanner objekt
		// System.in �r kursivt, vilket betyder att in �r en klass tillh�righet
		Scanner in = new Scanner(System.in);

		// Skapar ett Random object
		Random randObject = new Random();

		System.out.print("Mata in ditt namn: ");

		// Skapar ett String objekt och initserar den med inmatnings str�mmen
		// in.next() l�ser till f�rsta blanka
		// in.nextLine() L�ser resten av str�ngen
		// Det �r samma progblem med att Retur f�ljer med n�r man k�r in.next();
		// Man l�ser det med att man k�r in.nextLine() en g�ng innan man tar
		// emot inmatningen
		String name = in.nextLine();

		System.out.println("Hejsan " + name);

		// Typomvandling
		int randValue = (int) (Math.random() * 10 + 1);

		System.out.println(randValue);

		// Anv�nder random objectet
		randValue = randObject.nextInt(10) + 1;

		System.out.println(randValue);

		// St�nger str�mmen
		in.close();
	}
}
