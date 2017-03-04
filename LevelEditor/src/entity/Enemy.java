package entity;

import java.awt.Color;
import java.io.Serializable;

public class Enemy extends Entity implements Serializable
{

	public Enemy(int x, int y, Color color)
	{
		super(x, y, color);
	}

	@Override
	public void move()
	{

	}

}
