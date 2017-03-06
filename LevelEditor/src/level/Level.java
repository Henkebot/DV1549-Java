package level;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import graphics.Render;

public class Level implements Serializable
{
	/**
	 * Allt för att få bort varningen
	 */
	private static final long serialVersionUID = 1L;

	public static final int	  BLOCK_NOT_USED   = 0;
	public static final int	  BLOCK_NOT_SOLID  = 1;
	public static final int	  BLOCK_SOLID	   = 2;
	public static final int	  BLOCK_DANGEROUS  = 3;

	public static final int	  COLOR_NONE	   = 0;

	public static final int	  PLAYER_INDEX	   = 0;

	public static final int	  TILE_SIZE_2BASE  = 6;
	public static final int	  TILE_SIZE_PIX	   = 64;

	private int				  m_Width;
	private int				  m_Height;

	private int[][]			  blockMap;
	private int[][]			  colorMap;

	private ArrayList<Entity> entities;

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		blockMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];
		colorMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];
		for (int x = 0; x < m_Width >> TILE_SIZE_2BASE; x++)
		{
			for (int y = 0; y < m_Height >> TILE_SIZE_2BASE; y++)
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

		blockMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];
		colorMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];

		for (int x = 0; x < m_Width >> TILE_SIZE_2BASE; x++)
		{
			for (int y = 0; y < m_Height >> TILE_SIZE_2BASE; y++)
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
		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

		if (xCoord < m_Width && xCoord > 0 && yCoord < m_Height && yCoord > 0)
		{
			entities.get(PLAYER_INDEX).setX(xCoord);
			entities.get(PLAYER_INDEX).setY(yCoord);
		}

	}

	public void addEnemy(int x, int y)
	{
		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

		for (int i = 1; i < entities.size(); i++)
		{
			if (entities.get(i).getX() == xCoord && entities.get(i).getY() == yCoord) return;
		}

		if (xCoord < m_Width && xCoord > 0 && yCoord < m_Height && yCoord > 0)
			entities.add(new Enemy(xCoord, yCoord, Color.red));
	}

	public void removeEnemy(int x, int y)
	{
		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

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

		Rectangle rectX = new Rectangle();
		rectX.setLocation(entity.getXReq(), entity.getY());
		rectX.setSize(entity.getSize(), entity.getSize());

		Rectangle rectY = new Rectangle();
		rectY.setLocation(entity.getX(), entity.getYReq());
		rectY.setSize(entity.getSize(), entity.getSize());

		for (int x = (entity.getX() >> TILE_SIZE_2BASE) - 1; x < (entity.getX() >> TILE_SIZE_2BASE) + 2; x++)
		{
			for (int y = (entity.getY() >> TILE_SIZE_2BASE) - 1; y < (entity.getY() >> TILE_SIZE_2BASE) + 2; y++)
			{
				if (x < 0 || y >= (m_Height >> TILE_SIZE_2BASE) || y < 0 || x >= (m_Width >> TILE_SIZE_2BASE)) continue;

				if (blockMap[x][y] == BLOCK_SOLID)
				{
					Rectangle tile = new Rectangle();
					tile.setLocation(x << TILE_SIZE_2BASE, y << TILE_SIZE_2BASE);
					tile.setSize(TILE_SIZE_PIX, TILE_SIZE_PIX);

					if (rectX.intersects(tile)) entity.setXBool(false);
					if (tile.intersects(rectY)) entity.setYBool(false);

				}
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
