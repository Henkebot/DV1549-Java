package level;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import entity.Coin;
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
	public static final int	  BLOCK_DANGEROUS  = 3;

	public static final int	  COLOR_NONE	   = 0;

	public static final int	  PLAYER_INDEX	   = 0;

	public static final int	  TILE_SIZE_2BASE  = 6;
	public static final int	  TILE_SIZE_PIX	   = 64;

	private int				  m_PStartX;
	private int				  m_PStartY;

	private int				  m_Width;
	private int				  m_Height;

	private int[][]			  m_BlockMap;
	private int[][]			  m_ColorMap;

	private ArrayList<Entity> m_Entities;
	private boolean			  m_HasCoins;
	private ArrayList<Entity> m_Coins;

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		m_BlockMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];
		m_ColorMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];

		for (int x = 0; x < m_Width >> TILE_SIZE_2BASE; x++)
		{
			for (int y = 0; y < m_Height >> TILE_SIZE_2BASE; y++)
			{
				m_BlockMap[x][y] = BLOCK_NOT_USED;
			}
		}

		for (int x = 0; x < m_ColorMap.length; x++)
		{
			for (int y = 0; y < m_ColorMap[x].length; y++)
			{
				m_ColorMap[x][y] = COLOR_NONE;
			}
		}

		m_HasCoins = false;

		loadEntities();

	}

	public Level(Level level)
	{
		m_Width = level.m_Width;
		m_Height = level.m_Height;

		m_BlockMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];
		m_ColorMap = new int[m_Width >> TILE_SIZE_2BASE][m_Height >> TILE_SIZE_2BASE];

		for (int x = 0; x < m_Width >> TILE_SIZE_2BASE; x++)
		{
			for (int y = 0; y < m_Height >> TILE_SIZE_2BASE; y++)
			{
				m_BlockMap[x][y] = level.m_BlockMap[x][y];
			}
		}

		for (int x = 0; x < m_ColorMap.length; x++)
		{
			for (int y = 0; y < m_ColorMap[x].length; y++)
			{
				m_ColorMap[x][y] = level.m_ColorMap[x][y];
			}
		}

		m_Entities = new ArrayList<>();
		for (int i = 0; i < level.m_Entities.size(); i++)
		{
			m_Entities.add(level.m_Entities.get(i));
		}

		m_Coins = new ArrayList<>();
		for (int i = 0; i < level.m_Coins.size(); i++)
		{
			m_HasCoins = true;
			m_Coins.add(level.m_Coins.get(i));
		}
		m_PStartX = m_Entities.get(PLAYER_INDEX).getX();
		m_PStartY = m_Entities.get(PLAYER_INDEX).getY();

	}

	public void resetColorMap()
	{
		for (int x = 0; x < m_ColorMap.length; x++)
		{
			for (int y = 0; y < m_ColorMap[x].length; y++)
			{
				m_ColorMap[x][y] = COLOR_NONE;
			}
		}
	}

	public void changePlayerSpawn(int x, int y)
	{
		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

		if (xCoord < m_Width && xCoord > 0 && yCoord < m_Height && yCoord > 0)
		{
			m_Entities.get(PLAYER_INDEX).setX(xCoord);
			m_Entities.get(PLAYER_INDEX).setY(yCoord);
			m_PStartX = xCoord;
			m_PStartY = yCoord;
		}

	}

	public void addEnemy(int x, int y)
	{
		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

		for (int i = 1; i < m_Entities.size(); i++)
		{
			if (m_Entities.get(i).getX() == xCoord && m_Entities.get(i).getY() == yCoord)
				return;
		}

		if (xCoord < m_Width && xCoord > 0 && yCoord < m_Height && yCoord > 0)
			m_Entities.add(new Enemy(xCoord, yCoord));
	}

	public void removeEntity(int x, int y)
	{
		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

		for (int i = 1; i < m_Entities.size(); i++)
		{
			if (m_Entities.get(i).getX() == xCoord && m_Entities.get(i).getY() == yCoord)
				m_Entities.remove(i);
		}

		for (int i = 0; i < m_Coins.size(); i++)
		{
			if (m_Coins.get(i).getX() == xCoord && m_Coins.get(i).getY() == yCoord)
				m_Coins.remove(i);
		}

		if (m_Coins.size() == 0)
			m_HasCoins = false;
	}

	public void removeAllEnemies()
	{
		m_Entities.subList(1, m_Entities.size()).clear();
	}

	public int getHeight()
	{
		return m_Height;
	}

	public int getWidth()
	{
		return m_Width;
	}

	private void loadEntities()
	{
		m_Entities = new ArrayList<>();
		m_Coins = new ArrayList<>();

		m_PStartX = 20;
		m_PStartY = 20;
		m_Entities.add(new Player(m_PStartX, m_PStartY));
	}

	public void update()
	{
		m_Entities.get(PLAYER_INDEX).requestMov();
		collisionCheck(m_Entities.get(PLAYER_INDEX));
		m_Entities.get(PLAYER_INDEX).move();
		coinCollision();

		for (int i = 1; i < m_Entities.size(); i++)
		{
			m_Entities.get(i).requestMov();
			collisionCheck(m_Entities.get(i));
			m_Entities.get(i).move();
			enemyCollision(m_Entities.get(i));
		}
	}

	private void coinCollision()
	{
		Rectangle playerRec = new Rectangle();
		playerRec.setLocation(m_Entities.get(PLAYER_INDEX).getX(), m_Entities.get(PLAYER_INDEX).getY());
		playerRec.setSize(m_Entities.get(PLAYER_INDEX).getSize(), m_Entities.get(PLAYER_INDEX).getSize());

		for (int i = 0; i < m_Coins.size(); i++)
		{
			Rectangle enemyRec = new Rectangle();
			enemyRec.setLocation(m_Coins.get(i).getX(), m_Coins.get(i).getY());
			enemyRec.setSize(m_Coins.get(i).getSize(), m_Coins.get(i).getSize());
			if (enemyRec.intersects(playerRec))
				m_Coins.remove(i);
		}

	}

	private void enemyCollision(Entity entity)
	{
		Rectangle playerRec = new Rectangle();
		playerRec.setLocation(m_Entities.get(PLAYER_INDEX).getX(), m_Entities.get(PLAYER_INDEX).getY());
		playerRec.setSize(m_Entities.get(PLAYER_INDEX).getSize(), m_Entities.get(PLAYER_INDEX).getSize());

		Rectangle enemyRec = new Rectangle();
		enemyRec.setLocation(entity.getX(), entity.getY());
		enemyRec.setSize(entity.getSize(), entity.getSize());

		if (playerRec.intersects(enemyRec))
			resetPlayer();
	}

	private void resetPlayer()
	{
		m_Entities.get(PLAYER_INDEX).setX(m_PStartX);
		m_Entities.get(PLAYER_INDEX).setY(m_PStartY);
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
				if (x < 0 || y >= (m_Height >> TILE_SIZE_2BASE) || y < 0 || x >= (m_Width >> TILE_SIZE_2BASE))
					continue;

				if (m_BlockMap[x][y] == BLOCK_SOLID)
				{
					Rectangle tile = new Rectangle();
					tile.setLocation(x << TILE_SIZE_2BASE, y << TILE_SIZE_2BASE);
					tile.setSize(TILE_SIZE_PIX, TILE_SIZE_PIX);

					if (rectX.intersects(tile))
						entity.setXBool(false);
					if (tile.intersects(rectY))
						entity.setYBool(false);
				}
				if (entity instanceof Player && m_BlockMap[x][y] == BLOCK_DANGEROUS)
				{
					Rectangle tile = new Rectangle();
					tile.setLocation(x << TILE_SIZE_2BASE, y << TILE_SIZE_2BASE);
					tile.setSize(TILE_SIZE_PIX, TILE_SIZE_PIX);

					Rectangle rect = new Rectangle();
					rect.setLocation(entity.getX(), entity.getY());
					rect.setSize(entity.getSize(), entity.getSize());

					if (rect.intersects(tile))
					{
						entity.setXBool(false);
						entity.setYBool(false);
						resetPlayer();
						

					}

				}
			}
		}
	}

	public ArrayList<Entity> getEntities()
	{
		return m_Entities;
	}

	public ArrayList<Entity> getCoins()
	{
		return m_Coins;
	}

	public int[][] getBlockMap()
	{
		return m_BlockMap;
	}

	public int[][] getColorMap()
	{
		return m_ColorMap;
	}

	public void addCoin(int x, int y)
	{

		int xCoord = ((x >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;
		int yCoord = ((y >> TILE_SIZE_2BASE) << TILE_SIZE_2BASE) + 20;

		for (int i = 0; i < m_Coins.size(); i++)
		{
			if (m_Coins.get(i).getX() == xCoord && m_Coins.get(i).getY() == yCoord)
				return;
		}

		if (xCoord < m_Width && xCoord > 0 && yCoord < m_Height && yCoord > 0)
			m_Coins.add(new Coin(xCoord, yCoord));
		m_HasCoins = true;

	}

	public boolean levelComplete()
	{
		boolean levelCondition = false;
		if (m_HasCoins)
			levelCondition = m_Coins.size() == 0;
		return levelCondition;
	}
	
	public boolean hasCoins()
	{
		return m_HasCoins;
	}

}
