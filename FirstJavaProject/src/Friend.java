
public class Friend
{
	// Instansvariabler
	private String firstName;
	private String lastName;
	private int birthYear;

	// Relation, har ett Dice objekt
	private Dice theDice;

	// Konstruktorer
	public Friend(String firstName, String lastName, int birthYear)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;

		// Nu har ett vänobjekt exakt en tärning
		/* this.theDice = new Dice(6); */

		this.theDice = null;
	}

	public Friend(String firstName, String lastName)
	{
		// Anropar den första Konstruktorn, Det är för att undvika redundant kod
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

	public void tossTheDice()
	{
		if(this.theDice != null)
			this.theDice.toss();
	}
	
	public int getDiceValue()
	{
		int value = 0;
		if(this.theDice != null)
			value = theDice.getValue();
		return value;
	}

	public void setBirthYear(int birthYear)
	{
		this.birthYear = birthYear;
	}

	public void setTheDice(Dice theDice)
	{
		this.theDice = theDice;
	}

	public Dice giveDice()
	{
		Dice diceHolder = this.theDice;
		this.theDice = null;

		return diceHolder;

	}

	public String toString()
	{
		return this.firstName + " " + this.lastName + " som är född " + this.birthYear;
	}
}
