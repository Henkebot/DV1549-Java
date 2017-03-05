package editor;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	private static final String	BTN_TXT_PAINT	 = "Paint Mode";
	private static final String	BTN_TXT_ENTITY	 = "Place Entities";
	private static final String	BTN_TXT_MARK	 = "Mark Mode";
	private static final String	BTN_TXT_RESET	 = "Reset Level";

	private static final String	MNU_TXT_NEW		 = "New level";
	private static final String	MNU_TXT_SAVE	 = "Save level";
	private static final String	MNU_TXT_LOAD	 = "Load level";
	private static final String	MNU_TXT_EXIT	 = "Exit";

	private static final String	MNU_TXT_CONTROL	 = "Controls";
	private static final String	MNU_TXT_ABOUT	 = "About";

	private JFrame				mainFrame;
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

	// Använder i render.clear();
	private int					coolClear		 = 1;
	private boolean				isGameRunning;
	private int					cameraX;
	private int					cameraY;

	private enum TOOL
	{
		COLOR, ENTITY, MARK, NONE
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
		mainFrame = new JFrame();
		toolsPanel = new JPanel();
		toolsFeatures = new JPanel();
		dim = new Dimension(WIDTH, HEIGHT);
		selectedColor = Color.black;
		list = new JList<>();

		level = new Level(10 * 64, 20 * 64);
		render = new Render(WIDTH, HEIGHT, level);
		input = new Input();
		cameraX = cameraY = 0;

		crntToolTxt = new JLabel("Current tool: None");
		currentTool = TOOL.NONE;
	}

	private void start()
	{
		isGameRunning = true;

		thread = new Thread(this, "Editor Thread");
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
		mainFrame.getContentPane().add(toolsPanel);
		mainFrame.getContentPane().add(toolsFeatures);
		mainFrame.add(this);
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Editor");
		mainFrame.requestFocus();
		mainFrame.setVisible(true);

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
		JButton button = new JButton(BTN_TXT_PAINT);
		Dimension buttonDim = new Dimension(112, 25);
		button.addActionListener(tbList);
		button.setLocation(10, 20);
		button.setSize(buttonDim);

		toolsPanel.add(button);

		button = new JButton(BTN_TXT_ENTITY);
		button.addActionListener(tbList);
		button.setLocation(130, 20);
		button.setSize(buttonDim);
		toolsPanel.add(button);

		button = new JButton(BTN_TXT_MARK);
		button.addActionListener(tbList);
		button.setLocation(10, 55);
		button.setSize(buttonDim);
		toolsPanel.add(button);

		button = new JButton(BTN_TXT_RESET);
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

		JMenuItem item = new JMenuItem(MNU_TXT_NEW);
		item.addActionListener(mnuList);
		menu.add(item);

		item = new JMenuItem(MNU_TXT_LOAD);
		item.addActionListener(mnuList);
		menu.add(item);

		item = new JMenuItem(MNU_TXT_SAVE);
		item.addActionListener(mnuList);
		menu.add(item);

		menu.addSeparator();

		item = new JMenuItem(MNU_TXT_EXIT);
		item.addActionListener(mnuList);
		menu.add(item);

		// Help
		menu = new JMenu("Help");
		menuBar.add(menu);

		item = new JMenuItem(MNU_TXT_CONTROL);
		item.addActionListener(mnuList);
		menu.add(item);

		item = new JMenuItem(MNU_TXT_ABOUT);
		item.addActionListener(mnuList);
		menu.add(item);

		mainFrame.setJMenuBar(menuBar);
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
				coolClear++;

				update();

				unprocesed -= 1;
			}
			frames++;
			render();

			if (System.currentTimeMillis() - lastTimeMilli > 1000)
			{
				mainFrame.setTitle("Editor | Ticks " + tick + " Frames " + frames);
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

		render.clear(coolClear);
		render.draw();
		render.generateSheet();

		g.drawImage(render.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();

		bs.show();

	}

	private void update()
	{
		input.update();
		//level.update();

		// Camera movement
		render.setOffset(cameraX, cameraY);
		if (input.isUp()) cameraY+=5;
		if (input.isDown()) cameraY-=5;
		if (input.isLeft()) cameraX+=5;
		if (input.isRight()) cameraX-=5;

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
						case "Orange":
							selectedColor = Color.orange;
							break;
						default:
							selectedColor = Color.red;
					}
					render.replaceTileWithColor(input.getMouseX(), input.getMouseY(), selectedColor);

				}

				if (input.isMouseRight())
				{
					System.out.println("True");
					render.removeTile(input.getMouseX(), input.getMouseY());
				}
			}
			else if (currentTool == TOOL.ENTITY)
			{
				if (input.isMouseLeft())
				{
					int index = list.getSelectedIndex();
					if (index == -1) index = 0;
					switch (listContent[index])
					{
						case "Player":
							level.changePlayerSpawn(input.getMouseX() - render.getOffsets()[0],
									input.getMouseY() - render.getOffsets()[1]);
							break;
						case "Enemy":
							level.addEnemy(input.getMouseX() - render.getOffsets()[0],
									input.getMouseY() - render.getOffsets()[1]);
							break;
					}
				}
				else if (input.isMouseRight())
				{
					level.removeEnemy(input.getMouseX() - render.getOffsets()[0],
							input.getMouseY() - render.getOffsets()[1]);
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
				case MNU_TXT_NEW:
					newLevel();
					break;
				case MNU_TXT_SAVE:
					saveToFile();
					break;
				case MNU_TXT_LOAD:
					loadFromFile();
					break;
				case MNU_TXT_EXIT:
					System.exit(0);
					break;
				case MNU_TXT_CONTROL:
					displayControls();
					break;
				case MNU_TXT_ABOUT:
					displayAbout();
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
				case BTN_TXT_PAINT:
					displayColorWindow();
					break;
				case BTN_TXT_ENTITY:
					displayEntityWindow();
					break;
				case BTN_TXT_MARK:
					displayMarkWindow();
					break;
				case BTN_TXT_RESET:
					resetLevel();
					break;
			}
		}

	}

	public void displayColorWindow()
	{
		currentTool = TOOL.COLOR;
		crntToolTxt.setText("Current tool: Paint Mode");

		listContent = new String[] { "Red", "Blue", "Gray", "Orange" };
		list.setSize(200, 300);
		list.setListData(listContent);
		list.setBorder(BorderFactory.createTitledBorder("Selected a color"));
		toolsFeatures.add(list);
		toolsFeatures.setVisible(true);
		toolsFeatures.setBorder(BorderFactory.createTitledBorder("Colors"));
	}

	public void displayMarkWindow()
	{
		currentTool = TOOL.MARK;
		crntToolTxt.setText("Current tool: Mark mode");

		listContent = new String[] { "Solid", "Not Solid", "Dangerous" };
		list.setSize(200, 300);
		list.setListData(listContent);
		list.setBorder(BorderFactory.createTitledBorder("Selected a Mark"));
		toolsFeatures.add(list);
		toolsFeatures.setVisible(true);
		toolsFeatures.setBorder(BorderFactory.createTitledBorder("Marks"));

	}

	public void displayAbout()
	{
		JOptionPane.showMessageDialog(this, "Level Editor is a tool that creates levels\n\nWritten by Henrik Nilsson");

	}

	public void displayControls()
	{

		JOptionPane.showMessageDialog(this, "Toolbar:\nUse the left mouse button is to place objects\n "
				+ "and right mouse button to discard objects.\n" + "Camera:\nUse WASD to move the map.");
	}

	public void newLevel()
	{
		int answer = JOptionPane.showConfirmDialog(this, "Create a new level?\nAll current progress will be lost.",
				"New Level", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION)
		{
			String size = JOptionPane.showInputDialog(this, "Size format is in cubes (20x20)\nLevel size: ");
			int width = Integer.parseInt(size.substring(0, size.indexOf("x")));
			int height = Integer.parseInt(size.substring(size.indexOf("x") + 1));

			level = new Level(width << 6, height << 6);
			render = new Render(WIDTH, HEIGHT, level);

		}

	}

	public void loadFromFile()
	{
		String name = JOptionPane.showInputDialog(this, "Level name", "sample_level");

		try
		{
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("./" + name + ".level"));
			try
			{

				level = new Level((Level) input.readObject());
				render = new Render(WIDTH, HEIGHT, level);

				JOptionPane.showMessageDialog(this, name + ".level successfully loaded!");

				input.close();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't open " + name + ".level !");

			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveToFile()
	{

		String name = JOptionPane.showInputDialog(this, "Level name", "sample_level");

		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./" + name + ".level"));

			out.writeObject(level);
			out.close();
			JOptionPane.showMessageDialog(this, "Level successfully saved!\nPath: ./" + name + ".level");
		} catch (FileNotFoundException e)
		{

			e.printStackTrace();
		} catch (IOException e)
		{

			e.printStackTrace();
		}

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
		int value = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you wanna reset the level?", "Reset Level",
				JOptionPane.YES_NO_OPTION);
		if (value == JOptionPane.YES_OPTION)
		{
			level.resetColorMap();
			level.removeAllEnemies();
		}
	}
}
