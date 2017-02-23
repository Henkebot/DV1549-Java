package lektionTva;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileInput
{
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("./person.dat"));
		Person p = (Person)input.readObject();
		input.close();
		System.out.println(p);
	}
}
