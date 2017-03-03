package level;

public class Level
{
	private int m_Width;
	private int m_Height;
	private int[] m_Pixels;

	

	

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;
		m_Pixels = new int[m_Height * m_Width];
	}

	

	

	

	

	public int[] getPixels()
	{
		return m_Pixels;
	}
}
