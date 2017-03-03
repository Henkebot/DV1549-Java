package editor;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import graphics.Render;
import input.Input;
import level.Level;

public class Editor extends Canvas implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -396852945315248822L;

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;

	// Variables
	private JFrame gameFrame;
	private JPanel panel;
	private Thread thread;
	private Dimension dim;

	private Render render;
	private Level level;
	private Input input;
	private boolean isGameRunning;

	public Editor()
	{
		initVariables();
		createWindow();
		start();
	}

	private void initVariables()
	{
		render = new Render(WIDTH, HEIGHT);
		level = new Level(WIDTH, HEIGHT);
		input = new Input();
	}

	private void start()
	{
		isGameRunning = true;

		thread = new Thread(this, "Game Thread");
		thread.start();

	}

	private void createWindow()
	{

		gameFrame = new JFrame();
		panel = new JPanel();
		dim = new Dimension(WIDTH, HEIGHT);

		//gameFrame.addMouseListener(input);
		//panel.addMouseListener(input);

		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);

		JMenuBar menuBar = new JMenuBar();
		//menuBar.addMouseListener(input);
		JMenu menu = new JMenu("File");
		//menu.addMouseListener(input);
		menuBar.add(menu);

		JMenuItem item = new JMenuItem("Load from file");
		//item.addMouseListener(input);
		menu.add(item);

		item = new JMenuItem("Save to file");
		//item.addMouseListener(input);
		menu.add(item);

		menu.addSeparator();

		item = new JMenuItem("Exit");
		//item.addMouseListener(input);
		menu.add(item);
		gameFrame.setJMenuBar(menuBar);

		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Tools"));
		panel.setLocation(0, HEIGHT - 200);
		panel.setSize(new Dimension(WIDTH, 200));

		JButton button = new JButton("Select Texture");
		//button.addMouseListener(input);
		button.setSize(button.getPreferredSize());

		panel.add(button);
		this.addMouseListener(input);
		gameFrame.getContentPane().add(panel);
		gameFrame.add(this);
		gameFrame.pack();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setTitle("Editor");

		gameFrame.setVisible(true);

	}

	@Override
	public void run()
	{
		while (isGameRunning)
		{
			update();
			render();
		}
	}

	private void render()
	{
		BufferStrategy bs = getBufferStrategy();

		if (bs == null)
		{
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		render.clear();
		render.draw(level);

		g.drawImage(render.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();

		bs.show();

	}

	private void update()
	{
		input.update();
		if(input.isMouseClicked())
		{
			level.changeTile(input.getMouseX(), input.getMouseY());
			
		}
	}

}
