package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements KeyListener, MouseListener
{
	private boolean[] key;
	private boolean m_MouseClicked;

	private boolean up, down, left, right;

	private int m_MouseX;
	private int m_MouseY;

	public Input()
	{
		key = new boolean[1024];

		up = down = left = right = false;

		m_MouseClicked = false;
		m_MouseX = -1;
		m_MouseY = -1;
	}

	public void update()
	{
		up = key[KeyEvent.VK_W];
		left = key[KeyEvent.VK_A];
		right = key[KeyEvent.VK_D];
		down = key[KeyEvent.VK_S];

	}

	public boolean isMouseClicked()
	{
		return m_MouseClicked;
	}

	public int getMouseX()
	{
		return m_MouseX;
	}

	public int getMouseY()
	{
		return m_MouseY;
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		key[event.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		key[event.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(KeyEvent event)
	{
		// Kommer inte att användas

	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// Kommer inte att användas
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// Kommer inte att användas
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// Kommer inte att användas

	}

	@Override
	public void mousePressed(MouseEvent event)
	{	
		m_MouseClicked = true;
		m_MouseX = event.getX();
		m_MouseY = event.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		m_MouseClicked = false;

	}
}
