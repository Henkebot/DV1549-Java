package lektionEtt;

public class Dice
{
	private int nrOfFaces;
	private int value;

	public Dice(int nrOfFaces)
	{
		this.nrOfFaces = nrOfFaces;
		toss();
	}
	
	public Dice()
	{
		this(6);
	}

	public int getNrOfFaces()
	{
		return nrOfFaces;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Value: " + this.value;
	}

	public void toss()
	{
		this.value = (int) (Math.random() * nrOfFaces) + 1;
	}
}
