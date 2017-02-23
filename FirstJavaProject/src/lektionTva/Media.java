package lektionTva;

public abstract class Media
{
	private String title;
	private int pubYear;
	
	public Media(String title, int pubYear)
	{
		this.title = title;
		this.pubYear = pubYear;
	}
	public Media()
	{
		this("UnknownTitle", 0);
	}
	
	public int getPubYear()
	{
		return pubYear;
	}
	public String getTitle()
	{
		return title;
	}
	public void setPubYear(int pubYear)
	{
		this.pubYear = pubYear;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Override
	public String toString()
	{
	
		return "Title: " + title + "\nPub Year: " + pubYear +"\n" + toStringSpec();
	}
	
	public abstract String toStringSpec();
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		if (pubYear != other.pubYear)
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
