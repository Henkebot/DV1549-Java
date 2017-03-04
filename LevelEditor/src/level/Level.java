package level;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import entity.Entity;
import entity.Player;

public class Level implements Serializable
{
	/**
	 * Allt för att få bort varningen
	 */
	private static final long serialVersionUID = 1L;

	public static final int	  BLOCK_NOT_USED   = 0;
	public static final int	  BLOCK_NOT_SOLID  = 1;
	public static final int	  BLOCK_SOLID	   = 2;

	public static final int	  COLOR_NONE	   = 0;

	private int				  m_Width;
	private int				  m_Height;

	private int[][]			  blockMap;
	private int[][]			  colorMap;

	private ArrayList<Entity> entities;

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		blockMap = new int[m_Width / 64][m_Height / 64];
		colorMap = new int[m_Width >> 6][m_Height >> 6];
		for (int x = 0; x < m_Width / 64; x++)
		{
			for (int y = 0; y < m_Height / 64; y++)
			{
				blockMap[x][y] = BLOCK_NOT_USED;
			}
		}
		
		for (int x = 0; x < colorMap.length; x++)
		{
			for (int y = 0; y < colorMap[x].length; y++)
			{
				colorMap[x][y] = COLOR_NONE;
			}
		}

		loadMobs();

	}
	public void resetColorMap()
	{
		for (int x = 0; x < colorMap.length; x++)
		{
			for (int y = 0; y < colorMap[x].length; y++)
			{
				colorMap[x][y] = COLOR_NONE;
			}
		}
	}
	public void changePlayerSpawn(int x, int y)
	{
		entities.get(0).setX(((x / 64) * 64) + 20);
		entities.get(0).setY(((y / 64) * 64) + 20);
	}

	public int getHeight()
	{
		return m_Height;
	}

	public int getWidth()
	{
		return m_Width;
	}

	private void loadMobs()
	{
		entities = new ArrayList<>();
		entities.add(new Player(20, 20, Color.green));

	}

	public void update()
	{
		for (Entity entity : entities)
		{
			entity.move();
		}
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public int[][] getBlockMap()
	{
		return blockMap;
	}
	
	public int[][] getColorMap()
	{
		return colorMap;
	}
	

}
