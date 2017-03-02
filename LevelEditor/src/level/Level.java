package level;

import java.awt.Color;

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
		generateBoxes();
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
				int tileIndex = (x /64) + (y/64) * 64;
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
