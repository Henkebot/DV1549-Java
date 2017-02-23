package lektionTva;

import java.util.ArrayList;

public class PersonTest
{
	public static void main(String[] args)
	{
		ArrayList<Person> list = new ArrayList<>();
		
		list.add(new Person("Nilsson", "Kalle", "2010"));
		list.add(new Person("Nilsson", "Kalle", "2010"));
		list.add(list.get(0));
		
		Person p1 = new Person("Nilsson", "Kalle", "2010");
		Person p2 = new Person("Nilsson", "Kalle", "2010");
		
		Person p3 = p1;
		
		if(p1.equals(p2))
		{
			System.out.println("P1 IS EQUAL TO P2");
		}
		
		if(p1.equals(p3))
		{
			System.out.println("P1 IS EQUAL TO P3");
		}
		
		
	}
}
