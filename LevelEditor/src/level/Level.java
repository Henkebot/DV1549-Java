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
	
	public static final int BLOCK_NOT_SOLID = 0;
	public static final int BLOCK_SOLID = 1;
	
	private int				  m_Width;
	private int				  m_Height;

	private int[]			  mapPixels;
	private int[][]			  blockMap;
	
	

	private ArrayList<Entity> entities;

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		mapPixels = new int[m_Width * m_Height];
		blockMap = new int[m_Width / 64][m_Height / 64];
		
		for (int x = 0; x < m_Width / 64; x++)
		{
			for (int y = 0; y < m_Height / 64; y++)
			{
				blockMap[x][y] = BLOCK_NOT_SOLID;
			}
		}

		loadMobs();

		generateEmptyMap();

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

	public void generateEmptyMap()
	{
		for (int y = 0; y < m_Height; y++)
		{
			for (int x = 0; x < m_Width; x++)
			{
				mapPixels[x + y * m_Width] = 0;
			}
		}
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public int[] getMap()
	{
		return mapPixels;
	}
	
	public int[][] getBlockMap()
	{
		return blockMap;
	}

}
