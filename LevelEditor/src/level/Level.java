package level;

public class Level
{
	private int m_Width;
	private int m_Height;
	private int[] m_Pixels;
	
	public int[] tiles;
	
	public Level(int width,int height)
	{
		m_Width = width;
		m_Height = height;
		tiles = new int[64*64];
		m_Pixels = new int[m_Height * m_Width];
		//generateBoxes();
		generateSheet();
	}
	
	private void generateSheet()
	{
		int color = 0;
		for (int y = 0; y < m_Height; y++)
		{
			int yFrame = y >> 6;
			if(y > (yFrame * 64) + 2)
				color = 0xffffff;
			else 
				color = 0;
			for (int x = 0; x < m_Width; x++)
			{
				
				m_Pixels[x + y * m_Width] = color;
				
			}
		}
		
		for (int y = 0; y < m_Height; y++)
		{
			
			for (int x = 0; x < m_Width; x++)
			{
				
				int xFrame = x >> 6;
				if(x < (xFrame * 64) + 2)
					m_Pixels[x + y * m_Width] = 0;
				
				
				
			}
		}
		
	}

	public void changeTile(int x, int y)
	{
		
		for (int y0 = (y >> 6) * 64; y0 < ((y >> 6) * 64) + 64; y0++)
		{
			for (int x0 = (x >> 6) * 64 ; x0 < ((x >> 6) * 64) + 64; x0++)
			{
				if(x0 >= m_Width || x0 < 0 || y0 >= m_Height || y0 < 0) continue;
				m_Pixels[x0 + y0 * m_Width] = 0xff0fff;
			}
		}
		
	}
	
	private void generateBoxes()
	{
		for (int i = 0; i < 64*64; i++)
		{
			tiles[i] = (int) (Math.random() * 0xffffff);
		}
		
		for (int y = 0; y < m_Height; y++)
		{
			for (int x = 0; x < m_Width; x++)
			{
				int tileIndex = (x >> 6) + (y>>6) * 64;
				//System.out.println(tileIndex);
				m_Pixels[x + y * m_Width] = tiles[tileIndex];
			}
		}
	}
	
	public int[] getPixels()
	{
		return m_Pixels;
	}
}
