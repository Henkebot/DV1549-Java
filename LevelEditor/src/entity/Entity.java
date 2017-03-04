package entity;

import java.awt.Color;
import java.io.Serializable;

public abstract class Entity implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color m_Color;
	protected int m_xPos;
	protected int m_yPos;
	
	protected int m_xReq;
	protected int m_yReq;
	
	protected boolean xAllowed;
	protected boolean yAllowed;

	private int	  m_Size;
	private int[] m_EntityPixels;

	public Entity(int x, int y, Color color)
	{
		m_xPos = x;
		m_yPos = y;
		setM_Color(color);

		m_Size = 32;

		m_EntityPixels = new int[m_Size * m_Size];

		for (int i = 0; i < m_EntityPixels.length; i++)
		{
			m_EntityPixels[i] = color.getRGB();
		}

	}
	
	public int getXReq()
	{
		return m_xReq;
	}
	
	public int getYReq()
	{
		return m_yReq;
	}

	public abstract void requestMov();
	public void move()
	{
		if(xAllowed) m_xPos = m_xReq;
		if(yAllowed) m_yPos = m_yReq;
	}

	public int[] getPixels()
	{
		return m_EntityPixels;
	}

	public void setX(int x)
	{
		m_xPos = x;
	}

	public void setY(int y)
	{
		m_yPos = y;
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

	public Color getM_Color()
	{
		return m_Color;
	}

	public void setM_Color(Color m_Color)
	{
		this.m_Color = m_Color;
	}
	
	public void setXBool(boolean b)
	{
		xAllowed = b;
	}
	
	public void setYBool(boolean b)
	{
		yAllowed = b;
	}
}
