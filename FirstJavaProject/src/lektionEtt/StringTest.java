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

		// Jämförelse med ==
		// Det är då pekare så det stämmer aldrig, om det inte är samma
		if (name == nameAgain)
		{
			System.out.println("LIKA MED AVSEENDE PÅ ==");
		}
		// Jamförelse med .equals();
		if (name.equals(nameAgain))
		{
			System.out.println("LIKA MED equals METODEN");
		}
		// Jämförelse med .equalsIgnoreCase();
		if (name.equalsIgnoreCase(nameAgain))
		{
			System.out.println("LIKA MED equalsIgnorecase METODEN");
		}

		in.close();

	}

}
