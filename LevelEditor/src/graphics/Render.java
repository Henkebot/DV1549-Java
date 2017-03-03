package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import level.Level;

public class Render
{
	private int m_Width;
	private int m_Height;
	private int[] m_Pixels;
	
	public int[] tiles;
	
	private static final int TILE_SIZE_2BASE = 6;
	private static final int TILE_SIZE = 64;
	private static final int BORDER_THICKNESS = 5;

	private BufferedImage image;

	public Render(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		image = new BufferedImage(m_Width, m_Height, BufferedImage.TYPE_INT_RGB);
		m_Pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		tiles = new int[TILE_SIZE*TILE_SIZE];
		
		generateBoxes();

	}
	
	public void replaceTileWithColor(int x, int y, Color color)
	{

		int startY = (y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;
		int yCondition = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + TILE_SIZE;

		int startX = (x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;
		int xCondition = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + TILE_SIZE;

		for (int y0 = startY; y0 < yCondition; y0++)
		{
			for (int x0 = startX; x0 < xCondition; x0++)
			{
				if (x0 >= m_Width || x0 < 0 || y0 >= m_Height || y0 < 0) continue;
				m_Pixels[x0 + y0 * m_Width] = color.getRGB();
			}
		}

	}
	
	public void generateSheet()
	{
		int color = 0;
		for (int y = 0; y < m_Height; y++)
		{
			int yFrame = y >> TILE_SIZE_2BASE;
			if (y > (yFrame << TILE_SIZE_2BASE) + BORDER_THICKNESS) color = 0xffffff;
			else color = 0;
			for (int x = 0; x < m_Width; x++)
			{
				int xFrame = x >> TILE_SIZE_2BASE;
				if (x < (xFrame << TILE_SIZE_2BASE) + BORDER_THICKNESS) m_Pixels[x + y * m_Width] = 0;
				else m_Pixels[x + y * m_Width] = color;

			}
		}
	}
	
	public void insertCell(int x, int y)
	{
		int startY = (y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;
		int yCondition = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + TILE_SIZE;

		int startX = (x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;
		int xCondition = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + TILE_SIZE;

		int color = 0;

		for (int y0 = startY; y0 < yCondition; y0++)
		{
			int yFrame = y0 >> TILE_SIZE_2BASE;
			if (y0 > (yFrame << TILE_SIZE_2BASE) + BORDER_THICKNESS) color = 0xffffff;
			else color = 0;
			for (int x0 = startX; x0 < xCondition; x0++)
			{
				int xFrame = x0 >> TILE_SIZE_2BASE;
				if (x0 < (xFrame << TILE_SIZE_2BASE) + BORDER_THICKNESS) m_Pixels[x0 + y0 * m_Width] = 0;
				else m_Pixels[x0 + y0 * m_Width] = color;

			}
		}

	}
	
	private void generateBoxes()
	{
		for (int i = 0; i < 64 * 64; i++)
		{
			tiles[i] = (int) (Math.random() * 0xffffff);
		}

		for (int y = 0; y < m_Height; y++)
		{
			for (int x = 0; x < m_Width; x++)
			{
				int tileIndex = (x >> 6) + (y >> 6) * 64;
				m_Pixels[x + y * m_Width] = tiles[tileIndex];
			}
		}
	}

	public void clear()
	{
		for (int i = 0; i < m_Pixels.length; i++)
		{
			m_Pixels[i] = 0xff00ff;
		}
	}

	public void draw(Level level)
	{
		for (int y = 0; y < m_Height; y++)
		{
			for (int x = 0; x < m_Width; x++)
			{
				m_Pixels[x + y * m_Width] = level.getPixels()[x + y * m_Width];
			}
		}
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int[] getPixels()
	{
		return m_Pixels;
	}
}
