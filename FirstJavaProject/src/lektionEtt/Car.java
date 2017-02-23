package lektionEtt;

public class Car
{
	private String model;
	private int year;

	public Car(String model, int year)
	{
		this.model = model;
		this.year = year;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public void setYear(int year)
	{
		this.year = year;
	}
	
	public String getModel()
	{
		return model;
	}
	public int getYear()
	{
		return year;
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Model: " + this.model + "\nYear: " + this.year;
	}
}
