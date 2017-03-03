package editor;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
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

	private enum TOOL
	{
		TEXTURE, SPAWN
	}

	private TOOL currentTool;

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

		currentTool = TOOL.TEXTURE;
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

		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);

		buildMenu();

		buildToolbar();

		this.addMouseListener(input);
		this.addKeyListener(input);
		gameFrame.getContentPane().add(panel);
		gameFrame.add(this);
		gameFrame.pack();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setTitle("Editor");

		gameFrame.setVisible(true);

	}

	private void buildToolbar()
	{
		panel.setLayout(new GridLayout(1, 3));
		panel.setBorder(BorderFactory.createTitledBorder("Tools"));
		panel.setLocation(0, HEIGHT - 200);
		panel.setSize(new Dimension(WIDTH + 10, 210));

		JButton button = new JButton("Select Texture");
		button.setLocation(20, 20);
		button.setSize(button.getPreferredSize());

		panel.add(button);

		button = new JButton("Place spawns");
		button.setLocation(200, 20);
		button.setSize(button.getPreferredSize());
		panel.add(button);

		button = new JButton("Mark solid");
		button.setLocation(380, 20);
		button.setSize(button.getPreferredSize());
		panel.add(button);

	}

	private void buildMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		// File
		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem item = new JMenuItem("Load from file");
		menu.add(item);

		item = new JMenuItem("Save to file");
		menu.add(item);

		menu.addSeparator();

		item = new JMenuItem("Exit");
		menu.add(item);
		// Help
		menu = new JMenu("Help");
		menuBar.add(menu);

		item = new JMenuItem("Controls");
		gameFrame.setJMenuBar(menuBar);
		menu.add(item);

		item = new JMenuItem("About");
		gameFrame.setJMenuBar(menuBar);

		menu.add(item);
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

		if (input.isMouseClicked())
		{
			if (input.isMouseLeft()) level.changeTile(input.getMouseX(), input.getMouseY());

			if (input.isMouseRight())
			{
				level.insertCell(input.getMouseX(), input.getMouseY());
			}

		}

		if (input.isUp()) level.generateSheet();

	}

}
