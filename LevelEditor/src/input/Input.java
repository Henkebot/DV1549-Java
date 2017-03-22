package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements KeyListener, MouseListener
{
	private boolean[] key;

	private boolean m_MouseClicked;
	private boolean m_MouseLeft;
	private boolean m_MouseRight;
	private int m_MouseX;
	private int m_MouseY;
	
	private boolean m_KeyClicked;
	private static boolean up, down, left, right;

	public Input()
	{
		key = new boolean[1024];
		
		m_KeyClicked = false;
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
	
	public boolean isKeyClicked()
	{
		return m_KeyClicked;
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
		m_KeyClicked = true;
		key[event.getKeyCode()] = true;
		

	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		m_KeyClicked = false;
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

		if (event.getButton() == MouseEvent.BUTTON1) m_MouseLeft = true;

		if (event.getButton() == MouseEvent.BUTTON3) m_MouseRight = true;

		m_MouseX = event.getX();
		m_MouseY = event.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		m_MouseClicked = false;
		m_MouseLeft = false;
		m_MouseRight = false;

	}

	public boolean isMouseLeft()
	{
		return m_MouseLeft;
	}

	public boolean isMouseRight()
	{
		return m_MouseRight;
	}

	public static boolean isUp()
	{
		return up;
	}

	public static boolean isDown()
	{
		return down;
	}

	public static boolean isLeft()
	{
		return left;
	}

	public static boolean isRight()
	{
		return right;
	}

}
