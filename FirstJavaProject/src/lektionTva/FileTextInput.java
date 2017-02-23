package lektionTva;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileTextInput
{
	public static void main(String[] args)
	{
		Person p1 = null;
		try
		{
			Scanner input = new Scanner(new File("./personText.txt"));
			p1 = new Person(input.nextLine(), input.nextLine(), input.nextLine());
			input.close();
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(p1 != null)
			System.out.println(p1);
	}
}
