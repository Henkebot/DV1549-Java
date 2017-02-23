package lektionTva;

public class PhoneBookTest
{
	public static void main(String[] args)
	{
		PhoneBook eniro = new PhoneBook();
		
		eniro.addPerson("Henrik", "Nilsson", "101001");
		eniro.addPerson("ik", "Nilsson", "101001");
		eniro.addPerson("Heik", "Nilsson", "101001");
		eniro.addPerson("enrik", "Nilsson", "101001");
		
		if(eniro.removePersonAt(2))System.out.println("Removal Successfull");
		else System.out.println("Removal Denied");
		
		String[] strArray = eniro.getAllPersonAsStrings();
		
		for (int i = 0; i < strArray.length; i++)
		{
			System.out.println(strArray[i]);
		}
		
		
	}
}
