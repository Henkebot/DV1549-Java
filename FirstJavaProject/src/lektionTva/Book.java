package lektionTva;

public class Book extends Media
{
	private String author;

	public Book(String title, int pubYear, String author)
	{
		super(title, pubYear);
		this.author = author;
	}

	public Book()
	{
		this("UnknownTitle", 0, "UnknownAuthor");
	}
	
	public String getAuthor()
	{
		return author;
	}

	public String toStringSpec()
	{
		return  "Author: " + author + "\n";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null)
		{
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		return true;
	}

}
