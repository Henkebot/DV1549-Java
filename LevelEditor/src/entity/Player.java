package entity;

import java.awt.Color;
import java.io.Serializable;

import input.Input;

public class Player extends Entity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int		  SPEED			   = 4;
	private int				  changeX		   = 0;
	private double			  changeY		   = 0;

	public Player(int x, int y, Color color)
	{
		super(x, y, color);

	}

	@Override
	public void requestMov()
	{
		changeX = 0;
		//changeY = 0;

		if (Input.left) changeX -= SPEED;
		if (Input.right) changeX += SPEED;
		if (!yAllowed)
		{
			if (changeY > 0)
			{
				changeY = 0;
			}
			if (Input.up)
			{
				changeY = -12;
			}
		}
		else
		{
			changeY += 1;
		}

		m_xReq = m_xPos + changeX;
		m_yReq = (int) (m_yPos + changeY);
	}

}
