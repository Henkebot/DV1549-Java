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
}
