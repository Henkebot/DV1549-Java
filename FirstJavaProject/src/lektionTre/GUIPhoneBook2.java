package lektionTre;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class GUIPhoneBook2 extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4504150416652819025L;

	private class btnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			String btnStr = event.getActionCommand();
			switch (btnStr)
			{
			case "Lägg till":
				addPersonToPhoneBook();
				break;
			}

		}

	}

	private class menyIListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (arg0.getActionCommand() == "Avsluta")
			{
				System.exit(0);
			}

		}

	}

	private JTextField txtFName;
	private JTextField txtLName;
	private JTextField txtPhoneNr;
	private JList<String> listPerson;
	private int current;
	private Container contentPane;
	private PhoneBook phoneBook;

	public GUIPhoneBook2()
	{
		super("Telefonbok");
		InitiateInstanceVariabels();
		configureFrame();
	}

	public void addPersonToPhoneBook()
	{
		String FName = txtFName.getText();
		String LName = txtLName.getText();
		String PNr = txtPhoneNr.getText();

		if (FName.equals("") || LName.equals("") || PNr.equals(""))
		{
			JOptionPane.showMessageDialog(this, "You need to fill all the fields!");
		} else
		{
			phoneBook.add(txtFName.getText(), txtLName.getText(), txtPhoneNr.getText());
			listPerson.setListData(phoneBook.getAllPersonsAsStrings());
			listPerson.setSelectedIndex(++current);
			listPerson.setSelectionForeground(Color.blue);

		}

	}

	private void configureFrame()
	{
		this.setSize(600, 400);
		this.setResizable(false);
		// this.setLocationRelativeTo(null);
		this.setLocation(200, 150);
		this.contentPane.setLayout(new GridLayout(1, 2));// Inehållsrutan delas
															// in i 1 rad och 2
															// kolumner

		buildLeftPart();
		buildRightPart();
		buildMenu();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void buildMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem item = new JMenuItem("Information");
		menu.add(item);
		item = new JMenuItem("Avsluta");
		item.addActionListener(new menyIListener());
		menu.add(item);

		menu = new JMenu("Window");
		menuBar.add(menu);
		item = new JMenuItem("Help");
		menu.add(item);

		this.setJMenuBar(menuBar);

	}

	private void buildRightPart()
	{
		this.listPerson.setBorder(BorderFactory.createTitledBorder(" Alla personer "));
		this.contentPane.add(this.listPerson);
	}

	private void buildLeftPart()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(" Person "));

		this.txtFName.setBorder(BorderFactory.createTitledBorder(" Förnamn "));
		this.txtFName.setSize(260, 50);
		this.txtFName.setLocation(20, 30);

		this.txtLName.setBorder(BorderFactory.createTitledBorder(" Efternamn "));
		this.txtLName.setSize(260, 50);
		this.txtLName.setLocation(20, 100);

		this.txtPhoneNr.setBorder(BorderFactory.createTitledBorder(" Telefonnummer "));
		this.txtPhoneNr.setSize(260, 50);
		this.txtPhoneNr.setLocation(20, 170);

		addButtons(panel);

		panel.add(txtFName);
		panel.add(txtLName);
		panel.add(txtPhoneNr);

		this.contentPane.add(panel);
	}

	private void addButtons(JPanel panel)
	{
		String[] buttons = { "Lägg till", "<<", ">>", "Rensa", "Ändra", "Ta bort" };

		JButton button = new JButton("Lägg till");
		button.setLocation(20, 270);
		button.addActionListener(new btnListener());
		Dimension d = button.getPreferredSize();
		button.setSize(d);
		panel.add(button);

		button = new JButton("<<");
		button.setLocation((int) (20 + d.getWidth() + 5), 270);
		button.setSize(d);
		panel.add(button);

		button = new JButton(">>");
		button.setLocation((int) ((20 + d.getWidth() + 5) * 2), 270);
		button.setSize(d);

		panel.add(button);
	}

	private void InitiateInstanceVariabels()
	{
		this.txtFName = new JTextField();
		this.txtLName = new JTextField();
		this.txtPhoneNr = new JTextField();

		this.listPerson = new JList<>();
		this.current = -1;
		this.contentPane = this.getContentPane();
		this.phoneBook = new PhoneBook();
	}

	public static void main(String[] args)
	{
		GUIPhoneBook2 book = new GUIPhoneBook2();
		book.setVisible(true);
	}

}