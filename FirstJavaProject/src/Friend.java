
public class Friend
{
	//Instansvariabler
	private String firstName;
	private String lastName;
	private int birthYear;
	
	//Konstruktorer
	public Friend(String firstName, String lastName, int birthYear)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
	}
	
	public Friend(String firstName, String lastName)
	{
		//Anropar den första Konstruktorn, Det är för att undvika redundant kod
		this(firstName, lastName, 2017);	
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public int getBirthYear()
	{
		return birthYear;
	}
	
	public void setBirthYear(int birthYear)
	{
		this.birthYear = birthYear;
	}
	
	public String toString()
	{
		return this.firstName + " " + this.lastName + " som är född " + this.birthYear;
	}
}
