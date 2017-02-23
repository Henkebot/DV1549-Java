package lektionTva;

public class MediaTest
{
	public static void main(String[] args)
	{
		Media aMedia = new Book("Nils",192,"Geil");
		
		if(aMedia instanceof Book)
		{
			((Book) aMedia).getAuthor();
		}
		
		System.out.println(aMedia);			
		aMedia = new Movie("ET", 1988, "ET", 123);
		
		System.out.println(aMedia);
		
	
	}
}
