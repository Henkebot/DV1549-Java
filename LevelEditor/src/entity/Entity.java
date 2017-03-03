package entity;

import java.awt.Color;

public abstract class Entity
{
	private Color m_Color;
	protected int m_xPos;
	protected int m_yPos;

	private int	  m_Size;
	private int[] m_EntityPixels;

	public Entity(int x, int y, Color color)
	{
		m_xPos = x;
		m_yPos = y;
		m_Color = color;

		m_Size = 32;

		m_EntityPixels = new int[m_Size * m_Size];

		for (int i = 0; i < m_EntityPixels.length; i++)
		{
			m_EntityPixels[i] = color.getRGB();
		}

	}

	public abstract void move();

	public int[] getPixels()
	{
		return m_EntityPixels;
	}

	public int getX()
	{
		return m_xPos;
	}

	public int getY()
	{
		return m_yPos;
	}

	public int getSize()
	{
		return m_Size;
	}
}
