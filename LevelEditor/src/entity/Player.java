package entity;

import java.awt.Color;
import java.io.Serializable;

import input.Input;

public class Player extends Entity implements Serializable
{
	/**
	 * 
	 */
	private static final long  serialVersionUID	= 1L;
	private final int		   SPEED			= 4;
	private static final Color m_Color			= Color.green;

	public Player(int x, int y)
	{
		super(x, y, m_Color);

	}

	@Override
	public void requestMov()
	{
		changeX = 0;

		if (Input.isLeft())
			changeX -= SPEED;
		if (Input.isRight())
			changeX += SPEED;

		if (!getYBool() && !isJumping())
		{
			changeY = 0;

			if (Input.isUp())
			{
				changeY -= 18;

			}
		}
		else
		{
			changeY += 1;
		}

		m_yReq = (int) (m_yPos + changeY);

		m_xReq = m_xPos + changeX;

	}

}
