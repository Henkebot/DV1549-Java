package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import entity.Entity;
import level.Level;

public class Render
{
	private static final int TILE_SIZE_2BASE = 6;
	private static final int TILE_SIZE		 = 64;

	private int				 m_Width;
	private int				 m_Height;
	private int[]			 m_Pixels;
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

	}

	public int[] getOffsets()
	{
		return new int[] { m_xOffset, m_yOffset };
	}

	public void setOffset(int x, int y)
	{
		m_xOffset = x;
		m_yOffset = y;
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

		m_Level.getBlockMap()[xTile][yTile] = Level.BLOCK_SOLID;
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

		m_Level.getColorMap()[xTile][yTile] = Level.COLOR_NONE;
		m_Level.getBlockMap()[xTile][yTile] = Level.BLOCK_NOT_USED;

	}

	public void generateSheet()
	{
		for (int y = 0; y < m_Level.getHeight(); y += 64)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x += 3)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height) continue;

				m_Pixels[xa + ya * m_Width] = 0xffffff;
			}
		}
		for (int y = 0; y < m_Level.getHeight(); y += 3)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x += 64)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height) continue;

				m_Pixels[xa + ya * m_Width] = 0xffffff;
			}
		}

	}

	@SuppressWarnings("unused")
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
			m_Pixels[i] = (i + ticks) >> 4;
		}

	}

	public void draw()
	{
		drawLevel();
		drawEntities();

	}

	private void drawEntities()
	{

		for (Entity entity : m_Level.getEntities())
		{
			int xScroll = entity.getX();
			int yScroll = entity.getY();

			xScroll += m_xOffset;
			yScroll += m_yOffset;

			for (int y = 0; y < entity.getSize(); y++)
			{
				int ya = y + yScroll;
				for (int x = 0; x < entity.getSize(); x++)
				{
					int xa = x + xScroll;
					if (xa >= m_Width || xa < 0 || ya >= m_Height || ya < 0) continue;
					m_Pixels[xa + ya * m_Width] = entity.getPixels()[x + y * entity.getSize()];
				}
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
				if (m_Level.getColorMap()[x >> TILE_SIZE_2BASE][y >> TILE_SIZE_2BASE] != Level.COLOR_NONE)
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
