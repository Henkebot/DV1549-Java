package editor;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static final long	serialVersionUID = -396852945315248822L;

	private static final int	WIDTH			 = 1280;
	private static final int	HEIGHT			 = 720;

	private static final String	colorBtnTxt		 = "Paint mode";
	private static final String	resetBtnTxt		 = "Reset level";
	private static final String	placeEntBtnTxt	 = "Place Entities";

	// Variables
	private JFrame				gameFrame;
	private JPanel				panel;
	private Thread				thread;
	private Dimension			dim;

	private Render				render;
	private Level				level;
	private Input				input;

	private boolean				isGameRunning;

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
		level = new Level(WIDTH, HEIGHT);
		render = new Render(WIDTH, HEIGHT, level);
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
		panel.setLayout(new FlowLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Tools"));
		panel.setLocation(WIDTH - 200, 0);
		panel.setSize(new Dimension(240, HEIGHT));

		TBListener tbList = new TBListener();
		JButton button = new JButton(colorBtnTxt);
		Dimension buttonDim = new Dimension(button.getPreferredSize());
		button.addActionListener(tbList);
		button.setLocation(20, 20);
		button.setSize(buttonDim);

		panel.add(button);

		button = new JButton(placeEntBtnTxt);
		button.setLocation(200, 20);
		button.setSize(buttonDim);
		panel.add(button);

		button = new JButton("Mark solid");
		button.setLocation(380, 20);
		button.setSize(buttonDim);
		panel.add(button);

		button = new JButton(resetBtnTxt);
		button.addActionListener(tbList);
		button.setLocation(560, 20);
		button.setSize(buttonDim);
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

		// Clear the screen
		render.clear();

		render.draw();

		g.drawImage(render.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();

		bs.show();

	}

	private void update()
	{
		input.update();
		level.update();

		// Camera movement
		if (input.isUp()) render.setOffset(0, -1);
		if (input.isDown()) render.setOffset(0, 1);
		if (input.isLeft()) render.setOffset(-1, 0);
		if (input.isRight()) render.setOffset(1, 0);

		// Mouse inputs
		if (input.isMouseClicked())
		{
			if (input.isMouseLeft()) render.replaceTileWithColor(input.getMouseX(), input.getMouseY(), Color.BLACK);

			if (input.isMouseRight())
			{
				render.insertCell(input.getMouseX(), input.getMouseY());
			}

		}

	}

	private class TBListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String btnTxt = e.getActionCommand();
			switch (btnTxt)
			{
				case colorBtnTxt:
					displayColorWindow();
					break;
				case resetBtnTxt:
					resetLevel();
					break;
			}
		}

	}

	public void displayColorWindow()
	{
		JFrame frame = new JFrame("Colors");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(this);
		frame.requestFocus();

		JPanel panel = new JPanel();
		frame.setSize(200, 300);
		frame.setVisible(true);

	}

	public void resetLevel()
	{
		render.generateSheet();
	}
}
