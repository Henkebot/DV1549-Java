package editor;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Entity;
import graphics.Render;
import input.Input;
import level.Level;

public class Editor extends Canvas implements Runnable
{
	/**
	 * Allt för att få bort varningen
	 */
	private static final long	serialVersionUID = -396852945315248822L;

	private static final int	WIDTH			 = 1280;
	private static final int	HEIGHT			 = 720;

	private static final String	colorBtnTxt		 = "Paint mode";
	private static final String	resetBtnTxt		 = "Reset level";
	private static final String	placeEntBtnTxt	 = "Place Entities";

	private static final String	exitTxt			 = "Exit";

	private int					coolInt			 = 1;

	private JFrame				gameFrame;
	private JPanel				toolsPanel;
	private JPanel				toolsFeatures;
	private JLabel				crntToolTxt;
	private Thread				thread;
	private Dimension			dim;
	private Color				selectedColor;

	private String[]			listContent;
	private JList<String>		list;

	private Render				render;
	private Level				level;
	private Input				input;

	private boolean				isGameRunning;

	private enum TOOL
	{
		COLOR, ENTITY, NONE
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
		gameFrame = new JFrame();
		toolsPanel = new JPanel();
		toolsFeatures = new JPanel();
		dim = new Dimension(WIDTH, HEIGHT);
		selectedColor = Color.black;
		list = new JList<>();

		level = new Level(20 * 64, 10 * 64);
		render = new Render(WIDTH, HEIGHT, level);
		input = new Input();

		crntToolTxt = new JLabel("Current tool: None");
		currentTool = TOOL.NONE;
	}

	private void start()
	{
		isGameRunning = true;

		thread = new Thread(this, "Game Thread");
		thread.start();

	}

	private void createWindow()
	{

		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);

		buildMenu();

		buildToolbar();

		this.addMouseListener(input);
		this.addKeyListener(input);
		gameFrame.getContentPane().add(toolsPanel);
		gameFrame.getContentPane().add(toolsFeatures);
		gameFrame.add(this);
		gameFrame.pack();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setTitle("Editor");
		gameFrame.requestFocus();
		gameFrame.setVisible(true);

	}

	private void buildToolbar()
	{
		toolsPanel.setLayout(null);
		toolsPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
		toolsPanel.setLocation(0, HEIGHT - 200);
		toolsPanel.setSize(new Dimension(250, 210));

		toolsFeatures.setLayout(new GridLayout());
		toolsFeatures.setLocation(250, HEIGHT - 200);
		toolsFeatures.setSize(new Dimension(250, 210));
		toolsFeatures.setVisible(false);

		TBListener tbList = new TBListener();
		JButton button = new JButton(colorBtnTxt);
		Dimension buttonDim = new Dimension(112, 25);
		button.addActionListener(tbList);
		button.setLocation(10, 20);
		button.setSize(buttonDim);

		toolsPanel.add(button);

		button = new JButton(placeEntBtnTxt);
		button.addActionListener(tbList);
		button.setLocation(130, 20);
		button.setSize(buttonDim);
		toolsPanel.add(button);

		button = new JButton("Mark solid");
		button.setLocation(10, 55);
		button.setSize(buttonDim);
		toolsPanel.add(button);

		button = new JButton(resetBtnTxt);
		button.addActionListener(tbList);
		button.setLocation(130, 55);
		button.setSize(buttonDim);
		toolsPanel.add(button);

		crntToolTxt.setSize(200, 20);
		crntToolTxt.setLocation(20, 100);
		toolsPanel.add(crntToolTxt);

	}

	private void buildMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		MenuListener mnuList = new MenuListener();
		// File
		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem item = new JMenuItem("Load from file");
		menu.add(item);

		item = new JMenuItem("Save to file");
		menu.add(item);

		menu.addSeparator();

		item = new JMenuItem(exitTxt);
		item.addActionListener(mnuList);
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
		long lastTimeNano = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;
		double unprocesed = 0;
		int tick = 0;
		int frames = 0;
		double lastTimeMilli = System.currentTimeMillis();
		while (isGameRunning)
		{
			long nowNano = System.nanoTime();
			unprocesed += (nowNano - lastTimeNano) / nsPerTick;
			lastTimeNano = nowNano;
			while (unprocesed > 1)
			{
				tick++;
				coolInt++;

				update();

				unprocesed -= 1;
			}
			frames++;
			render();

			if (System.currentTimeMillis() - lastTimeMilli > 1000)
			{
				System.out.println("Ticks " + tick + " Frames " + frames);
				tick = 0;
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
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Clear the screen
		render.clear(coolInt);

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
		if (input.isUp()) render.setOffset(0, -5);
		if (input.isDown()) render.setOffset(0, 5);
		if (input.isLeft()) render.setOffset(-5, 0);
		if (input.isRight()) render.setOffset(+5, 0);

		// Mouse inputs
		if (input.isMouseClicked())
		{
			if (currentTool == TOOL.COLOR)
			{

				if (input.isMouseLeft())
				{
					int index = list.getSelectedIndex();
					if (index == -1) index = 0;
					switch (listContent[index])
					{
						case "Red":
							selectedColor = Color.red;
							break;
						case "Blue":
							selectedColor = Color.blue;
							break;
						case "Gray":
							selectedColor = Color.gray;
							break;
						default:
							selectedColor = Color.red;
					}
					render.replaceTileWithColor(input.getMouseX(), input.getMouseY(), selectedColor);

				}

				if (input.isMouseRight())
				{
					render.insertCell(input.getMouseX(), input.getMouseY());
				}
			}
			else if(currentTool == TOOL.ENTITY)
			{
				if(input.isMouseLeft())
				{
					int index = list.getSelectedIndex();
					if(index  == -1) index = 0;
					switch(listContent[index])
					{
						case "Player":
							Entity player = level.getEntities().get(0);
							render.insertCell(player.getX(), player.getY());
							level.changePlayerSpawn(input.getMouseX()- render.getOffsets()[0], input.getMouseY()- render.getOffsets()[1]);
							break;
					}
				}
			}

		}

	}

	private class MenuListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			switch (e.getActionCommand())
			{
				case exitTxt:
					System.exit(0);
					break;
			}

		}

	}

	private class TBListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			switch (e.getActionCommand())
			{
				case colorBtnTxt:
					displayColorWindow();
					break;
				case resetBtnTxt:
					resetLevel();
					break;
				case placeEntBtnTxt:
					displayEntityWindow();
					break;
			}
		}

	}

	public void displayColorWindow()
	{
		currentTool = TOOL.COLOR;
		crntToolTxt.setText("Current tool: Paint Mode");

		listContent = new String[] { "Red", "Blue", "Gray" };
		list.setSize(200, 300);
		list.setListData(listContent);
		list.setBorder(BorderFactory.createTitledBorder("Selected a color"));
		toolsFeatures.add(list);
		toolsFeatures.setVisible(true);
		toolsFeatures.setBorder(BorderFactory.createTitledBorder("Colors"));
	}

	public void displayEntityWindow()
	{
		currentTool = TOOL.ENTITY;
		crntToolTxt.setText("Current tool: Place Entities");

		listContent = new String[] { "Player", "Enemy" };
		list.setSize(200, 300);
		list.setListData(listContent);
		list.setBorder(BorderFactory.createTitledBorder("Selected an Entity"));
		toolsFeatures.add(list);
		toolsFeatures.setVisible(true);
		toolsFeatures.setBorder(BorderFactory.createTitledBorder("Entities"));

	}

	public void resetLevel()
	{
		int value = JOptionPane.showConfirmDialog(gameFrame, "Are you sure you wanna reset the level?", "Reset Level",
				JOptionPane.YES_NO_OPTION);
		if (value == JOptionPane.YES_OPTION) render.generateSheet();
	}
}
