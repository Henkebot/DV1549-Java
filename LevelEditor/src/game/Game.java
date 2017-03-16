package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;

import graphics.Render;
import input.Input;
import level.Level;

public class Game extends Canvas implements Runnable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int  WIDTH			   = 1280;
	private static final int  HEIGHT		   = 720;

	private String			  levelPath;
	private JFrame			  mainFrame;
	private Dimension		  dim;
	private Thread			  thread;

	private Level			  level;
	private Render			  render;
	private static Input	  input;

	private boolean			  isGameRunning;
	private int				  coolClear;

	public Game(String levelPath)
	{
		initVariables(levelPath);
		createWindow();
		start();
	}

	private void start()
	{
		isGameRunning = true;

		thread = new Thread(this, "Game Thread");
		thread.start();
	}

	public static Input getInput()
	{
		return input;
	}

	private void createWindow()
	{
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);

		this.addKeyListener(input);

		mainFrame.add(this);
		mainFrame.pack();

		mainFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				isGameRunning = false;
				mainFrame.dispose();
			}
		});

		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Game");

		mainFrame.setVisible(true);

	}

	private void initVariables(String levelPath)
	{
		mainFrame = new JFrame();
		dim = new Dimension(WIDTH, HEIGHT);

		input = new Input();
		setUpLevel(levelPath);
		render = new Render(WIDTH, HEIGHT, level);

		coolClear = 1;

	}

	private void setUpLevel(String levelPath)
	{
		try
		{
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("./levels/" + levelPath + ".level"));
			try
			{
				level = new Level((Level) input.readObject());

				input.close();

			}
			catch (ClassNotFoundException e)
			{

				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println(levelPath + " was not valid");
			e.printStackTrace();
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}

	}

	@Override
	public void run()
	{
		long lastTimeNano = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;
		double unprocesed = 0;
		int updates = 0;
		int frames = 0;
		double lastTimeMilli = System.currentTimeMillis();
		requestFocus();
		while (isGameRunning)
		{
			long nowNano = System.nanoTime();
			unprocesed += (nowNano - lastTimeNano) / nsPerTick;
			lastTimeNano = nowNano;
			while (unprocesed > 1)
			{
				updates++;
				coolClear++;
				update();
				unprocesed -= 1;
			}
			frames++;
			render();

			if (System.currentTimeMillis() - lastTimeMilli > 1000)
			{
				mainFrame.setTitle("Game | Updates " + updates + " Frames " + frames);
				updates = 0;
				frames = 0;
				lastTimeMilli += 1000;
			}
		}

	}

	private void render()
	{
		BufferStrategy bs = getBufferStrategy();

		if (bs == null)
		{
			createBufferStrategy(1);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		render.clear(coolClear);
		render.draw();

		g.drawImage(render.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();

		bs.show();

	}

	private void update()
	{
		input.update();
		level.update();

		int xOffset = level.getEntities().get(Level.PLAYER_INDEX).getX() - (WIDTH / 2);
		int yOffset = level.getEntities().get(Level.PLAYER_INDEX).getY() - 500;

		render.setOffset(-xOffset, -yOffset - 200);

	}

	public String getLevelPath()
	{
		return levelPath;
	}

	public void setLevelPath(String levelPath)
	{
		this.levelPath = levelPath;
	}

}
