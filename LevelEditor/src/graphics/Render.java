package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import entity.Entity;
import level.Level;

public class Render
{

	private int			  m_Width;
	private int			  m_Height;
	private int[]		  m_Pixels;
	public int[]		  tiles;
	private BufferedImage image;

	private int			  m_xOffset;
	private int			  m_yOffset;

	public static enum MODE
	{
		COLOR, MARK
	}

	public MODE	  renderMode;
	private Level m_Level;

	public Render(int width, int height, Level level)
	{
		renderMode = MODE.COLOR;
		m_Width = width;
		m_Height = height;

		image = new BufferedImage(m_Width, m_Height, BufferedImage.TYPE_INT_RGB);
		m_Pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		tiles = new int[Level.TILE_SIZE_PIX * Level.TILE_SIZE_PIX];
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
		int startY = (y >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;

		int startX = (x >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;

		int xTile = (startX >> Level.TILE_SIZE_2BASE);
		int yTile = (startY >> Level.TILE_SIZE_2BASE);

		if (xTile < 0 || xTile >= m_Level.getWidth() >> Level.TILE_SIZE_2BASE || yTile < 0
				|| yTile >= m_Level.getHeight() >> Level.TILE_SIZE_2BASE)
			return;

		m_Level.getBlockMap()[xTile][yTile] = Level.BLOCK_SOLID;
		m_Level.getColorMap()[xTile][yTile] = color.getRGB();

	}

	public void changeBlockType(int x, int y, int type)
	{
		x -= m_xOffset;
		y -= m_yOffset;
		int startY = (y >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;

		int startX = (x >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;

		int xTile = (startX >> Level.TILE_SIZE_2BASE);
		int yTile = (startY >> Level.TILE_SIZE_2BASE);

		if (xTile < 0 || xTile >= m_Level.getWidth() >> Level.TILE_SIZE_2BASE || yTile < 0
				|| yTile >= m_Level.getHeight() >> Level.TILE_SIZE_2BASE)
			return;

		if (m_Level.getColorMap()[xTile][yTile] == Level.COLOR_NONE)
			return;

		m_Level.getBlockMap()[xTile][yTile] = type;
	}

	public void removeTile(int x, int y)
	{
		x -= m_xOffset;
		y -= m_yOffset;
		int startY = (y >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;

		int startX = (x >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;

		int xTile = (startX >> Level.TILE_SIZE_2BASE);
		int yTile = (startY >> Level.TILE_SIZE_2BASE);

		if (xTile < 0 || xTile >= m_Level.getWidth() >> Level.TILE_SIZE_2BASE || yTile < 0
				|| yTile >= m_Level.getHeight() >> Level.TILE_SIZE_2BASE)
			return;

		m_Level.getColorMap()[xTile][yTile] = Level.COLOR_NONE;
		m_Level.getBlockMap()[xTile][yTile] = Level.BLOCK_NOT_USED;

	}

	public void generateSheet()
	{
		for (int y = 0; y < m_Level.getHeight(); y += Level.TILE_SIZE_PIX)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x += 3)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height)
					continue;

				m_Pixels[xa + ya * m_Width] = 0xffffff;
			}
		}
		for (int y = 0; y < m_Level.getHeight(); y += 3)
		{
			int ya = y + m_yOffset;
			for (int x = 0; x < m_Level.getWidth(); x += Level.TILE_SIZE_PIX)
			{
				int xa = x + m_xOffset;
				if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height)
					continue;

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
				int tileIndex = (x >> Level.TILE_SIZE_2BASE) + (y >> Level.TILE_SIZE_2BASE) << Level.TILE_SIZE_2BASE;
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
					if (xa >= m_Width || xa < 0 || ya >= m_Height || ya < 0)
						continue;
					m_Pixels[xa + ya * m_Width] = entity.getPixels()[x + y * entity.getSize()];
				}
			}
		}

	}

	private void drawLevel()
	{
		if (renderMode == MODE.COLOR)
		{
			for (int y = 0; y < m_Level.getHeight(); y++)
			{
				int ya = y + m_yOffset;
				for (int x = 0; x < m_Level.getWidth(); x++)
				{
					int xa = x + m_xOffset;
					if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height)
						continue;
					if (m_Level
							.getColorMap()[x >> Level.TILE_SIZE_2BASE][y >> Level.TILE_SIZE_2BASE] != Level.COLOR_NONE)
						m_Pixels[xa + ya * m_Width] = m_Level
								.getColorMap()[x >> Level.TILE_SIZE_2BASE][y >> Level.TILE_SIZE_2BASE];
				}
			}

		}
		else if (renderMode == MODE.MARK)
		{
			for (int y = 0; y < m_Level.getHeight(); y += 2)
			{
				int ya = y + m_yOffset;
				for (int x = 0; x < m_Level.getWidth(); x += 2)
				{
					int xa = x + m_xOffset;
					if (xa < 0 || xa >= m_Width || ya < 0 || ya >= m_Height)
						continue;
					switch (m_Level.getBlockMap()[x >> Level.TILE_SIZE_2BASE][y >> Level.TILE_SIZE_2BASE])
					{
						case Level.BLOCK_SOLID:
							m_Pixels[xa + ya * m_Width] = Color.CYAN.getRGB();
							break;
						case Level.BLOCK_NOT_SOLID:
							m_Pixels[xa + ya * m_Width] = Color.WHITE.getRGB();
							break;
						case Level.BLOCK_DANGEROUS:
							m_Pixels[xa + ya * m_Width] = Color.RED.getRGB();
							break;
					}
				}

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
