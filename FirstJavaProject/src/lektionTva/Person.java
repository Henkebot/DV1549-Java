package lektionTva;

public class Person
{
	//Privata instansvaribler
	private String lastName;
	private String firstName;
	private String phoneNr;
	//publica instansmetoder
	public Person(String lastName, String firstName, String phoneNr)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNr = phoneNr;
	}
	public Person()
	{
		//För att undvika rendundant kod
		this("?", "?", "?");
	}
	//Getters
	public String getLastName()
	{
		return lastName;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getPhoneNr()
	{
		return phoneNr;
	}
	//Setters
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public void setPhoneNr(String phoneNr)
	{
		this.phoneNr = phoneNr;
	}
	
	//ToString()
	@Override
	public String toString()
	{
		return "Lastname: " + lastName + "\nFirstname: " + firstName + "\nPhone Number: " + phoneNr + "\n"; 
	}
	
	//Min egna equals
	
//	public boolean equals(Object other)
//	{
//		//Castar om other till Person pekare
//		Person p =(Person) other;
//		
//		boolean isSame = false;
//		
//		//Jämförelse görs med .equals() i string klassen
//		if(this.firstName.equals(p.firstName) &&
//			this.lastName.equals(p.lastName) &&
//			this.phoneNr.equals(p.phoneNr))
//			isSame = true;
//		
//		return isSame;
//	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phoneNr == null) ? 0 : phoneNr.hashCode());
		return result;
	}
	
	//Auto genererad kod
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null)
		{
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null)
		{
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNr == null)
		{
			if (other.phoneNr != null)
				return false;
		} else if (!phoneNr.equals(other.phoneNr))
			return false;
		return true;
	}
}
