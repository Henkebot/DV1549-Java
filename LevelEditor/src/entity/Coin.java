package entity;

import java.awt.Color;
import java.io.Serializable;

public class Coin extends Entity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color m_Color = Color.YELLOW;

	public Coin(int x, int y)
	{
		super(x, y, m_Color);
	}

	@Override
	public void requestMov()
	{
		
		
	}

}
