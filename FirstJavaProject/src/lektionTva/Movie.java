package lektionTva;

public class Movie extends Media
{
	private String mainActor;
	private int playTime;
	
	public Movie(String title, int pubYear, String mainActor, int playTime)
	{
		super(title, pubYear);
		this.mainActor = mainActor;
		this.playTime = playTime;
	}
	
	public Movie()
	{
		this("UnknownTitle", 0, "UnknownActor", 0);
	}
}
