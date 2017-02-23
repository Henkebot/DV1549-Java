package lektionTva;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileTextOutput
{
	public static void main(String[] args)
	{
		Person p1 = new Person("Jonsson", "Kalle", "192");
		
		try
		{
			PrintWriter out = new PrintWriter("./PersonText.txt");
			out.println(p1.getFirstName());
			out.println(p1.getLastName());
			out.println(p1.getPhoneNr());
			out.close();
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		
	}
}
