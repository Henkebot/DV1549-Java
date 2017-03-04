package entity;

import java.awt.Color;
import java.io.Serializable;

public class Player extends Entity implements Serializable
{

	public Player(int x, int y, Color color)
	{
		super(x, y, color);
	}

	@Override
	public void move()
	{

	}

}
