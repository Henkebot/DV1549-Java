package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.plaf.FontUIResource;

import entity.Entity;
import level.Level;

public class Render
{
	private static final int TILE_SIZE_2BASE  = 6;
	private static final int TILE_SIZE		  = 64;
	private static final int BORDER_THICKNESS = 5;

	private int				 m_Width;
	private int				 m_Height;
	private int[]			 m_Pixels;
	private int[]			 m_MapPixels;
	private Level			 m_Level;
	public int[]			 tiles;

	private int				 m_xOffset;
	private int				 m_yOffset;

	private BufferedImage	 image;

	public Render(int width, int height, Level level)
	{
		m_Width = width;
		m_Height = height;

		image = new BufferedImage(m_Width, m_Height, BufferedImage.TYPE_INT_RGB);
		m_Pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		tiles = new int[TILE_SIZE * TILE_SIZE];
		m_Level = level;
		m_MapPixels = new int[m_Level.getWidth() * m_Level.getHeight()];
		// generateSheet();

	}

	public int[] getOffsets()
	{
		return new int[] { m_xOffset, m_yOffset };
	}

	public void setOffset(int x, int y)
	{
		m_xOffset -= x;
		m_yOffset -= y;
	}

	public void replaceTileWithColor(int x, int y, Color color)
	{
		x -= m_xOffset;
		y -= m_yOffset;
		int startY = (y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;

		int startX = (x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;

		int xTile = (startX >> TILE_SIZE_2BASE);
		int yTile = (startY >> TILE_SIZE_2BASE);

		if (xTile < 0 || xTile >= m_Level.getWidth() >> TILE_SIZE_2BASE || yTile < 0
				|| yTile >= m_Level.getHeight() >> TILE_SIZE_2BASE)
			return;

		m_Level.getBlockMap()[xTile][yTile] = m_Level.BLOCK_SOLID;
		m_Level.getColorMap()[xTile][yTile] = color.getRGB();

	}
	
	public void removeTile(int x, int y)
	{
		x -= m_xOffset;
		y -= m_yOffset;
		int startY = (y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;

		int startX = (x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;

		int xTile = (startX >> TILE_SIZE_2BASE);
		int yTile = (startY >> TILE_SIZE_2BASE);

		if (xTile < 0 || xTile >= m_Level.getWidth() >> TILE_SIZE_2BASE || yTile < 0
				|| yTile >= m_Level.getHeight() >> TILE_SIZE_2BASE)
			return;

		
		m_Level.getColorMap()[xTile][yTile] = m_Level.COLOR_NONE;
		

	}

	public void generateSheet()
	{
		for (int y = 0; y < m_Level.getHeight(); y+=64)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x+=3)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height) continue;
				
					m_Pixels[xa + ya * m_Width] = 0xffffff;
			}
		}
		for (int y = 0; y < m_Level.getHeight(); y+=3)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x+=64)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height) continue;
				
					m_Pixels[xa + ya * m_Width] = 0xffffff;
			}
		}
		
		
	}

	public void insertCell(int x, int y)
	{
		x -= m_xOffset;
		y -= m_yOffset;

		int startY = (y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;
		int yCondition = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + TILE_SIZE;

		int startX = (x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE;
		int xCondition = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + TILE_SIZE;

		int color = 0;
		if (startX < 0 || startX >= m_Level.getWidth()) return;

		m_Level.getBlockMap()[startX >> TILE_SIZE_2BASE][startY >> TILE_SIZE_2BASE] = m_Level.BLOCK_NOT_USED;

		for (int y0 = startY; y0 < yCondition; y0++)
		{
			int yFrame = y0 >> TILE_SIZE_2BASE;
			if (y0 > (yFrame << TILE_SIZE_2BASE) + BORDER_THICKNESS) color = 0;
			else color = 0xffffff;
			for (int x0 = startX; x0 < xCondition; x0++)
			{
				int xFrame = x0 >> TILE_SIZE_2BASE;
				if (x0 >= m_Level.getWidth() || x0 < 0 || y0 < 0 || y0 >= m_Level.getHeight()) continue;
				if (x0 < (xFrame << TILE_SIZE_2BASE) + BORDER_THICKNESS)
					m_MapPixels[x0 + y0 * m_Level.getWidth()] = 0xffffff;
				else m_MapPixels[x0 + y0 * m_Level.getWidth()] = color;

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

	public void clear(int ticks)
	{
		for (int i = 0; i < m_Pixels.length; i++)
		{
			m_Pixels[i] = (i + ticks) >> 1;
		}

	}

	public void draw()
	{
		generateSheet();
		drawLevel();
		drawEntities();

	}

	private void drawEntities()
	{

		Entity entity = m_Level.getEntities().get(0);

		int xScroll = entity.getX() + m_xOffset;
		int yScroll = entity.getY() + m_yOffset;

		for (int y = 0; y < entity.getSize(); y++)
		{
			int ya = y + yScroll;
			for (int x = 0; x < entity.getSize(); x++)
			{
				int xa = x + xScroll;
				if (xa >= m_Level.getWidth() || xa < 0 || ya >= m_Level.getHeight() || ya < 0) continue;
				m_Pixels[xa + ya * m_Level.getWidth()] = entity.getPixels()[x + y * entity.getSize()];
			}
		}

	}

	private void drawLevel()
	{

		for (int y = 0; y < m_Level.getHeight(); y++)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x++)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height) continue;
				if (m_Level.getColorMap()[x >> TILE_SIZE_2BASE][y >> TILE_SIZE_2BASE] != m_Level.COLOR_NONE)
					m_Pixels[xa + ya * m_Width] = m_Level.getColorMap()[x >> TILE_SIZE_2BASE][y >> TILE_SIZE_2BASE];
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
