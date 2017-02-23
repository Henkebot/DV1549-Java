
public class Person
{
	private String lastName;
	private String firstName;
	private String phoneNr;
	
	public Person(String lastName, String firstName, String phoneNr)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNr = phoneNr;
	}
	public Person()
	{
		lastName = firstName = phoneNr = "Empty";
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
		// TODO Auto-generated method stub
		return "Lastname: " + lastName + "\nFirstname: " + firstName + "\nPhone Number: " + phoneNr; 
	}
}
