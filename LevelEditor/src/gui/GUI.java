package gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import editor.Editor;
import game.Game;

public class GUI extends JFrame
{
	private Game				game;
	private Editor				editor;

	private static final int	WIDTH		  = 300;
	private static final int	HEIGHT		  = 300;

	private static final String	BTN_TXT_EDITOR = "Start Editor";
	private static final String BTN_TXT_GAME = "Start Game";

	private Container			contentPane;
	private JList<String>		levelList;
	private String[]			levels;

	public GUI()
	{
		super("Game | Editor");

		initFields();
		createFrame();
		configureFrame();
	}

	private void configureFrame()
	{
		contentPane.setLayout(new GridLayout(2, 1));

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JButton button = new JButton(BTN_TXT_EDITOR);
		button.addActionListener(new BtnListener());
		button.setLocation(20, 10);
		button.setSize(button.getPreferredSize());

		panel.add(button);

		button = new JButton(BTN_TXT_GAME);
		button.addActionListener(new BtnListener());
		button.setLocation(150, 10);
		button.setSize(button.getPreferredSize());

		panel.add(button);

		contentPane.add(panel);

		contentPane.add(levelList);

	}

	private void createFrame()
	{
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initFields()
	{
		initJList();
		contentPane = getContentPane();
	}

	private void initJList()
	{
		File folder = new File("./levels/");
		File[] listOfFiles = folder.listFiles();
		levelList = new JList<>();
		int index = 0;
		levels = new String[listOfFiles.length];
		for (File file : listOfFiles)
		{
			if (file.isFile())
			{
				levels[index++] = file.getName().substring(0, file.getName().indexOf("."));
			}
		}
		levelList.setListData(levels);
	}

	public static void main(String[] args)
	{
		GUI test = new GUI();
		test.setVisible(true);
	}

	private class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch (e.getActionCommand())
			{
				case BTN_TXT_GAME:
					startGame();
					break;
				case BTN_TXT_EDITOR:
					startEditor();
					break;
			}
		}
	}

	public void startGame()
	{
		int index = levelList.getSelectedIndex();
		if (index == -1)
			index = 0;
		game = new Game(levels[index]);

	}

	public void startEditor()
	{
		int index = levelList.getSelectedIndex();

		if (index == -1)
			editor = new Editor("");
		else editor = new Editor(levels[index]);

	}
}
