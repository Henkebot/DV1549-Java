package assignment;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame
{
	private static final int WIDTH 	= 300;
	private static final int HEIGHT = 600;

	private static final String addBtnText 		= "Add";
	private static final String showFirstText 	= "Show First";
	private static final String showRemoveText 	= "Remove first";

	private static final String loadFromFileTxt = "Load from file";
	private static final String saveToFileTxt 	= "Save to file";
	private static final String exitTxt 		= "Exit";

	private JTextField inputField;
	private Container contentPane;
	private JList<String> listQueue;

	private Queue<Integer> queue;

	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			switch (event.getActionCommand())
			{
			case addBtnText:
				addValueToQueue();
				break;
			case showFirstText:
				displayFirst();
				break;
			case showRemoveText:
				removeAndDisplayFirst();
				break;
			}
		}
	}

	private class MenuListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			
			switch (event.getActionCommand())
			{
			case loadFromFileTxt: 
				loadFromFile();
				break;
			case saveToFileTxt:
				saveToFile();
				break;
			case exitTxt:
				System.exit(0);
				break;
			}
		}

	}

	public GUI(String title)
	{
		super(title);
		initFields();
		createFrame();
		configureFrame();

	}

	public void saveToFile()
	{
		String filePath = JOptionPane.showInputDialog(contentPane, "Please enter the filename");
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./" + filePath + ".dat"));
			
			out.writeObject(queue);
			out.close();
			
			JOptionPane.showMessageDialog(contentPane, "Successfully saved to file!");
		} catch (FileNotFoundException e)
		{
			
			e.printStackTrace();
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
	}

	public void loadFromFile()
	{
		String filePath = JOptionPane.showInputDialog(contentPane, "Please enter the filename: ");
		
		ObjectInputStream input;
		try
		{
			input = new ObjectInputStream(new FileInputStream("./" + filePath + ".dat"));
			queue = (Queue) input.readObject();
			input.close();
			updateList();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void removeAndDisplayFirst()
	{
		String displayText = "The Queue is currently empty!";
		
		if (!queue.isEmpty()) displayText = "Removed " + queue.dequeue() + " from the queue!";
		
		JOptionPane.showMessageDialog(contentPane, displayText);
		
		updateList();
	}

	public void displayFirst()
	{
		String displayText = "The queue is currently empty!";
		if (!queue.isEmpty())
		{
			displayText = "The first Integer is " + queue.front() + ".";
		}
		JOptionPane.showMessageDialog(contentPane, displayText);

	}

	public void addValueToQueue()
	{
		String integer = inputField.getText();
		// Den kollar dock inte om man har tryckt massa mellanslag
		if (integer.isEmpty())
		{
			JOptionPane.showMessageDialog(contentPane, "Input field is empty!");
		}
		else
		{
			queue.enqueue(Integer.parseInt(integer));
			updateList();
		}
		
		inputField.setText(""); // Reset inputField

	}

	private void updateList()
	{
		listQueue.setListData(queue.getAllElementsAsStrings());
	}

	private void configureFrame()
	{
		contentPane.setLayout(new GridLayout(3, 1));

		buildUpperPart();
		buildMiddlePart();
		buildLowerPart();
		buildMenu();
	}

	private void buildMiddlePart()
	{
		listQueue.setBorder(BorderFactory.createTitledBorder("Queue Content"));
		contentPane.add(listQueue);
	}

	private void buildUpperPart()
	{
		JPanel panel = new JPanel();
		ButtonListener btnListener = new ButtonListener();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Functions"));

		JButton button = new JButton(showFirstText);

		Dimension boxDim = button.getPreferredSize();

		button.addActionListener(btnListener);
		button.setLocation(20, 30);
		button.setSize(boxDim);

		panel.add(button);

		button = new JButton(showRemoveText);

		button.addActionListener(btnListener);
		button.setLocation(20 + boxDim.width + 5, 30);

		boxDim = button.getPreferredSize();

		button.setSize(boxDim);

		panel.add(button);

		contentPane.add(panel);
	}

	private void buildLowerPart()
	{
		JPanel panel = new JPanel();

		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Queue list"));

		inputField.setBorder(BorderFactory.createTitledBorder("Integer input"));
		inputField.setSize(200, 50);
		inputField.setLocation(10, 20);

		JButton button = new JButton(addBtnText);

		button.setSize(button.getPreferredSize());
		button.setLocation(230, 40);
		button.addActionListener(new ButtonListener());

		panel.add(button);

		panel.add(inputField);

		contentPane.add(panel);
	}

	private void buildMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		MenuListener mnuListener = new MenuListener();
		
		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem item = new JMenuItem(loadFromFileTxt);
		item.addActionListener(mnuListener);
		menu.add(item);

		item = new JMenuItem(saveToFileTxt);
		item.addActionListener(mnuListener);
		menu.add(item);

		item = new JMenuItem(exitTxt);
		item.addActionListener(mnuListener);
		menu.add(item);

		setJMenuBar(menuBar);
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
		inputField = new JTextField();
		contentPane = this.getContentPane();
		listQueue = new JList<>();
		queue = new Queue<>();
	}

	public static void main(String[] args)
	{
		GUI gui = new GUI("Integer Queue");
		gui.setVisible(true);
	}
}
