package lektionTre;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUIPhoneBook extends JFrame
{
	private class BtnListen implements ActionListener, ListSelectionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{

			if (arg0.getActionCommand().equals("Lägg till"))
			{
				addPerson();
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

	

	}

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	private static final int BOX_WIDTH = 80;
	private static final int BOX_HEIGHT = 25;

	private JTextField firstnameField;
	private JTextField surnameField;
	private JTextField phoneNrField;

	private JButton addPerson;
	private JButton clear;
	private JButton change;
	private JButton remove;
	private JButton leftArrow;
	private JButton rightArrow;

	private DefaultListModel listModel;
	private JList<String> lista;

	private Container contentPane;

	private PhoneBook book;

	public GUIPhoneBook(String title)
	{
		super(title);

		configureFrame();
		initFrame();
	}

	public void addPerson()
	{
		book.add(firstnameField.getText(), surnameField.getText(), phoneNrField.getText());
		updateList();

	}

	private void updateList()
	{
		listModel.addElement(book.getAllPersonsAsStrings()[book.nrOfPersons() - 1]);
		
	}

	private void configureFrame()
	{
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		
		BtnListen listener = new BtnListen();
		
		firstnameField = new JTextField("Firstname");
		firstnameField.setBounds(20, 30, 260, 50);

		contentPane.add(firstnameField);

		surnameField = new JTextField("Lastname");
		surnameField.setBounds(20, 110, 260, 50);

		contentPane.add(surnameField);

		phoneNrField = new JTextField("Phone Number");
		phoneNrField.setBounds(20, 180, 260, 50);

		contentPane.add(phoneNrField);

		addPerson = new JButton("Lägg till");
		addPerson.addActionListener(listener);
		addPerson.setBounds(20, 270, BOX_WIDTH, BOX_HEIGHT);

		contentPane.add(addPerson);

		clear = new JButton("Rensa");
		clear.setBounds(20, 300, BOX_WIDTH, BOX_HEIGHT);

		contentPane.add(clear);

		change = new JButton("Ända");
		change.setBounds(105, 300, BOX_WIDTH, BOX_HEIGHT);

		contentPane.add(change);

		remove = new JButton("Ta Bort");
		remove.setBounds(190, 300, BOX_WIDTH, BOX_HEIGHT);

		contentPane.add(remove);

		leftArrow = new JButton("<<");
		leftArrow.setBounds(105, 270, BOX_WIDTH, BOX_HEIGHT);

		contentPane.add(leftArrow);

		rightArrow = new JButton(">>");
		rightArrow.setBounds(190, 270, BOX_WIDTH, BOX_HEIGHT);

		contentPane.add(rightArrow);
		book = new PhoneBook();
		
		listModel = new DefaultListModel();
		
		lista = new JList<String>(listModel);
		lista.setBounds(285, 5, 295, 350);
		lista.addListSelectionListener(listener);
		contentPane.add(lista);

	}

	private void initFrame()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(200, 150);

	}

	public static void main(String[] args)
	{
		GUIPhoneBook test = new GUIPhoneBook("PhoneBook");

	}

}
