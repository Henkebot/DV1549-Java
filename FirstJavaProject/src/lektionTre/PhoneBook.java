package lektionTre;

import java.util.ArrayList;

public class PhoneBook 
{
	private ArrayList<Person> persons;
	
	public PhoneBook()
	{
		this.persons = new ArrayList<>();
	}
	
	public void add(String firstName, String lastName, String phoneNr)
	{
		this.persons.add(new Person(lastName, firstName, phoneNr));
	}
	public String[] getAllPersonsAsStrings()
	{
		String[] stringPersons = new String[this.nrOfPersons()];
		
		for (int i=0; i<stringPersons.length; i++)
		{
			stringPersons[i] = this.persons.get(i).toString();
		}
		return stringPersons;
	}
	public int nrOfPersons()
	{
		return this.persons.size();
	}
	public boolean removePersonAt(int index)
	{
		boolean didRemove = false;
		try
		{
			this.persons.remove(index);
			didRemove = true;
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		return didRemove;
	}
	public String firstNameOfPersonAt(int index)
	{
		String str = "?";
		try
		{
			str = this.persons.get(index).getFirstName();		
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		return str;
	}
	

	public String lastNameOfPersonAt(int index)
	{
		String str = "?";
		
		try
		{
			str = this.persons.get(index).getLastName();
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		return str;
	}
	public String phoneNumberOfPersonAt(int index)
	{
		String str = "?";
		
		try
		{
			str = this.persons.get(index).getPhoneNr();
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		return str;
	}
	public void changeFirstNameOfPersonAt(int index, String theFirstName)
	{
		try
		{
			this.persons.get(index).setFirstName(theFirstName);
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	public void changeLastNameOfPersonAt(int index, String theLastName)
	{
		try
		{
			this.persons.get(index).setLastName(theLastName);
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
	}
	public void changePhoneNumberOfPersonAt(int index, String thePhoneNumber)
	{
		try
		{
			this.persons.get(index).setPhoneNr(thePhoneNumber);
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
	}
}
