package entity;

import java.awt.Color;
import java.io.Serializable;

public class Enemy extends Entity implements Serializable
{

	/**
	 * 
	 */
	private static final long  serialVersionUID	= 1L;
	private final int		   SPEED			= 5;
	private int				   direction;
	private static final Color m_Color			= Color.red;

	public Enemy(int x, int y)
	{
		super(x, y, m_Color);
		direction = 1;
	}

	@Override
	public void requestMov()
	{
		changeX = 0;

		if (!getXBool())
		{
			if (direction == 1)
				direction = -1;
			else direction = 1;
		}

		if (!getYBool())
			changeY = 0;
		else changeY += 1;

		changeX -= SPEED * direction;

		m_yReq = (int) (m_yPos + changeY);

		m_xReq = m_xPos + changeX;
	}

}
