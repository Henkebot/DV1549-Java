package entity;

import java.awt.Color;
import java.io.Serializable;

public class Coin extends Entity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Coin(int x, int y, Color color)
	{
		super(x, y, color);
	}

	@Override
	public void requestMov()
	{
		
		
	}

}
