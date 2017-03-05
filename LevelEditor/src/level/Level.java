package level;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import entity.Enemy;
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
	public static final int	  PLAYER_INDEX	   = 0;

	private int				  m_Width;
	private int				  m_Height;

	private int[][]			  blockMap;
	private int[][]			  colorMap;

	private ArrayList<Entity> entities;

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		blockMap = new int[m_Width >> 6][m_Height >> 6];
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

	public Level(Level level)
	{
		m_Width = level.m_Width;
		m_Height = level.m_Height;

		blockMap = new int[m_Width >> 6][m_Height >> 6];
		colorMap = new int[m_Width >> 6][m_Height >> 6];

		for (int x = 0; x < m_Width >> 6; x++)
		{
			for (int y = 0; y < m_Height >> 6; y++)
			{
				blockMap[x][y] = level.blockMap[x][y];
			}
		}

		for (int x = 0; x < colorMap.length; x++)
		{
			for (int y = 0; y < colorMap[x].length; y++)
			{
				colorMap[x][y] = level.colorMap[x][y];
			}
		}

		entities = new ArrayList<>();
		for (int i = 0; i < level.entities.size(); i++)
		{
			entities.add(level.entities.get(i));
		}

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
		entities.get(PLAYER_INDEX).setX(((x / 64) * 64) + 20);
		entities.get(PLAYER_INDEX).setY(((y / 64) * 64) + 20);
	}

	public void addEnemy(int x, int y)
	{
		int xCoord = ((x >> 6) << 6) + 20;
		int yCoord = ((y >> 6) << 6) + 20;

		for (int i = 1; i < entities.size(); i++)
		{
			if (entities.get(i).getX() == xCoord && entities.get(i).getY() == yCoord) return;
		}
		System.out.println(entities.size());
		entities.add(new Enemy(xCoord, yCoord, Color.red));
	}

	public void removeEnemy(int x, int y)
	{
		int xCoord = ((x >> 6) << 6) + 20;
		int yCoord = ((y >> 6) << 6) + 20;

		for (int i = 1; i < entities.size(); i++)
		{
			if (entities.get(i).getX() == xCoord && entities.get(i).getY() == yCoord) entities.remove(i);
		}
	}

	public void removeAllEnemies()
	{
		entities.subList(1, entities.size()).clear();
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
			entity.requestMov();
			collisionCheck(entity);
			entity.move();
		}
	}

	public void collisionCheck(Entity entity)
	{
		entity.setXBool(true);
		entity.setYBool(true);

		int xCoord = entity.getXReq() >> 6;
		int yCoord = (entity.getYReq() + entity.getSize()) >> 6;
		
		if(blockMap[xCoord][yCoord] == BLOCK_SOLID)
		{
			entity.setYBool(false);
			yCoord = entity.getYReq() >> 6;
			if(blockMap[xCoord][yCoord] == BLOCK_SOLID)
			{
				entity.setYBool(true);
			}
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
