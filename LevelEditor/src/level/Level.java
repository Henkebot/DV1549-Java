package level;

import java.awt.Color;
import java.util.ArrayList;

import entity.Entity;
import entity.Player;

public class Level
{
	private int				  m_Width;
	private int				  m_Height;

	private int[]			  mapCoords;

	private ArrayList<Entity> entities;

	public Level(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		mapCoords = new int[m_Width * m_Height];

		loadMobs();

		generateEmptyMap();

	}

	private void loadMobs()
	{
		entities = new ArrayList<>();
		entities.add(new Player(300, 400, Color.green));

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
				mapCoords[x + y * m_Width] = 0;
			}
		}
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public int[] getMap()
	{
		return mapCoords;
	}

}
