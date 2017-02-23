package lektionEtt;
import java.util.Random;
import java.util.Scanner;

public class SomeInputTesting
{
	public static void main(String[] args)
	{
		// Skapar ett Scanner objekt
		// System.in är kursivt, vilket betyder att in är en klass tillhörighet
		Scanner in = new Scanner(System.in);

		// Skapar ett Random object
		Random randObject = new Random();

		System.out.print("Mata in ditt namn: ");

		// Skapar ett String objekt och initserar den med inmatnings strömmen
		// in.next() läser till första blanka
		// in.nextLine() Läser resten av strängen
		// Det är samma progblem med att Retur följer med när man kör in.next();
		// Man löser det med att man kör in.nextLine() en gång innan man tar
		// emot inmatningen
		String name = in.nextLine();

		System.out.println("Hejsan " + name);

		// Typomvandling
		int randValue = (int) (Math.random() * 10 + 1);

		System.out.println(randValue);

		// Använder random objectet
		randValue = randObject.nextInt(10) + 1;

		System.out.println(randValue);

		// Stänger strömmen
		in.close();
	}
}
