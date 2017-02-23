package lektionTva;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileOutput
{

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Person aPerson = new Person("Karlsson", "Karl", "202020");
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./person.dat"));
		
		out.writeObject(aPerson);
		out.close();

	}

}
