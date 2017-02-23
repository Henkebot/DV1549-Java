package lektionTva;

import java.util.ArrayList;

public class PhoneBook
{
	private ArrayList<Person> register;

	public PhoneBook()
	{
		register = new ArrayList<>();
	}

	public void addPerson(String firstName, String lastName, String phoneNr)
	{
		register.add(new Person(lastName, firstName, phoneNr));
	}

	public int nrOfPerson()
	{
		return register.size();
	}

	public String[] getAllPersonAsStrings()
	{
		String[] result = new String[register.size()];
		for (int i = 0; i < register.size(); i++)
		{
			result[i] = register.get(i).toString();
		}

		return result;
	}

	public boolean removePersonAt(int index)
	{
		boolean isRemoved = false;
		if (index < register.size())
		{
			register.remove(index);
			isRemoved = true;
		}

		return isRemoved;

	}
}
