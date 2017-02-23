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

	
	public String getMainActor()
	{
		return mainActor;
	}
	public int getPlayTime()
	{
		return playTime;
	}
	public void setMainActor(String mainActor)
	{
		this.mainActor = mainActor;
	}
	public void setPlayTime(int playTime)
	{
		this.playTime = playTime;
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
		Movie other = (Movie) obj;
		if (mainActor == null)
		{
			if (other.mainActor != null)
				return false;
		} else if (!mainActor.equals(other.mainActor))
			return false;
		if (playTime != other.playTime)
			return false;
		return true;
	}

	@Override
	public String toStringSpec()
	{
	
		return  "Main actor: " + mainActor + "\nPlayTime: " + playTime + "\n";
	}
	
	
}
