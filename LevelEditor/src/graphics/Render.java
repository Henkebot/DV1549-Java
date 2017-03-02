package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import level.Level;

public class Render
{
	private int m_Width;
	private int m_Height;
	private int[] m_Pixels;

	private BufferedImage image;

	public Render(int width, int height)
	{
		m_Width = width;
		m_Height = height;

		image = new BufferedImage(m_Width, m_Height, BufferedImage.TYPE_INT_RGB);
		m_Pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	}

	public void clear()
	{
		for (int i = 0; i < m_Pixels.length; i++)
		{
			m_Pixels[i] = 0xff00ff;
		}
	}

	public void draw(Level level)
	{
		for (int y = 0; y < m_Height; y++)
		{
			for (int x = 0; x < m_Width; x++)
			{
				m_Pixels[x + y * m_Width] = level.getPixels()[x + y * m_Width];
			}
		}
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int[] getPixels()
	{
		return m_Pixels;
	}
}
