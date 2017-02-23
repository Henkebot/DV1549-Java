package lektionEtt;
import java.util.Scanner;

public class StringTest
{

	public static void main(String[] args)
	{
		// Skapar Scanner objektet
		Scanner in = new Scanner(System.in);

		// Initserar String objekt
		String name = null;
		String nameAgain = null;

		// Inmatning
		System.out.print("Mata in ditt namn: ");
		name = in.nextLine();

		System.out.print("Mata in ditt namn igen: ");
		nameAgain = in.nextLine();

		// J�mf�relse med ==
		// Det �r d� pekare s� det st�mmer aldrig, om det inte �r samma
		if (name == nameAgain)
		{
			System.out.println("LIKA MED AVSEENDE P� ==");
		}
		// Jamf�relse med .equals();
		if (name.equals(nameAgain))
		{
			System.out.println("LIKA MED equals METODEN");
		}
		// J�mf�relse med .equalsIgnoreCase();
		if (name.equalsIgnoreCase(nameAgain))
		{
			System.out.println("LIKA MED equalsIgnorecase METODEN");
		}

		in.close();

	}

}
