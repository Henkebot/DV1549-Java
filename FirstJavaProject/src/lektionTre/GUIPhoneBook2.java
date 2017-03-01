/*
 * GUI för att hantera phonebook
 */
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

	private static final long serialVersionUID = -4504150416652819025L;

	// ActionListener, det är här alla knapptryckningar skickas
	private class btnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{	
			//Man får en ett ActionEvent som argument och den använder man för
			//att hitta vilken knapp eller meny som blev tryckt
			String btnStr = event.getActionCommand(); // getActionCommand() returnar det som stod på knappen
			switch (btnStr)
			{
			case "Lägg till": // Om det knappen som det stod Lägg till på
				addPersonToPhoneBook(); // Man skapar en metod utanför klassen för att komma åt dess medlemmar
				break;
			}

		}

	}

	// Identisk som btnListener, men gjorde en ny för att visa att denna används
	// i menyer.
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

	// Textfält
	private JTextField txtFName;
	private JTextField txtLName;
	private JTextField txtPhoneNr;
	// Lista av strängar
	private JList<String> listPerson;
	// Räknar som håller koll på den aktuella personen i listan
	private int current;
	// contentPane är själva ritytan i fönstret
	private Container contentPane;

	private PhoneBook phoneBook;

	public GUIPhoneBook2()
	{
		super("Telefonbok");

		InitiateInstanceVariabels();
		configureFrame();
	}
	//Lägger till en person
	public void addPersonToPhoneBook()
	{
		//Läser in vad som står i textfälten
		String FName = txtFName.getText();
		String LName = txtLName.getText();
		String PNr = txtPhoneNr.getText();
		//Kollar så att alla fälten är ifyllda, om inte så får man upp en ruta 
		if (FName.equals("") || LName.equals("") || PNr.equals(""))
		{
			JOptionPane.showMessageDialog(this, "You need to fill all the fields!");
		} else
		{
			//Lägger till personen
			phoneBook.add(txtFName.getText(), txtLName.getText(), txtPhoneNr.getText());
			//Uppdaterar listan med strängarna
			listPerson.setListData(phoneBook.getAllPersonsAsStrings());
			//Ökar current
			listPerson.setSelectedIndex(++current);
			listPerson.setSelectionForeground(Color.blue); // Blå backgrund för det markerade, artistiskt som fan

		}

	}

	// Uppbyggdnad av fönstret
	private void configureFrame()
	{

		this.setSize(600, 400);
		this.setResizable(false);
		this.setLocation(200, 150); // Var på skärmen rutan ska placeras
		
		/* Detta delar upp fönstret i celler, argumenten är (rows, col)*/
		this.contentPane.setLayout(new GridLayout(1, 2));
		
		//Fönstret är nu uppdelat i 2 columner
		buildLeftPart(); 
		buildRightPart();
		
		buildMenu();
		
		//För att programmet ska avslutas när man trycker på krysset i fönstret. 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void buildMenu()
	{
		//Man skapar ett JMenuBar, det är detta som man ser på program som tex (File, Edit, Source) fast bara fältet
		JMenuBar menuBar = new JMenuBar();
		//Nu skapar man rubriker i menufältet
		JMenu menu = new JMenu("File");
		//lägg till dem i menuBar
		menuBar.add(menu);
		//Trycker man på File så får man upp en liten undermenu, det är information man ser då
		JMenuItem item = new JMenuItem("Information");
		menu.add(item); // Lägger till den i File menu
		
		item = new JMenuItem("Avsluta"); //Skapar en till, denna är precis som den ovan.
		item.addActionListener(new menyIListener()); //Lägger till så att det händer något när man trycker på den
		menu.add(item); // Lägger till den i File menu

		menu = new JMenu("Window"); //Denna är precis som File
		menuBar.add(menu); // Lägger till den i menuBaren (fältet)
		item = new JMenuItem("Help"); //Underrubrik för Window
		menu.add(item);

		this.setJMenuBar(menuBar); // Lägger till den i fönstret, VIKTIGT

	}

	private void buildRightPart()
	{
		this.listPerson.setBorder(BorderFactory.createTitledBorder(" Alla personer "));
		this.contentPane.add(this.listPerson);
	}

	private void buildLeftPart()
	{
		//JPanel är en typ av rityta 
		JPanel panel = new JPanel(); //Det gör inget att panel skapas och förstörs i denna metoden
		//Eftersom vi lägger till den i contentPane senare. 
		
		panel.setLayout(null);
		//BorderFactory.createTitleBorder är vad namnet beskriver, det blir en border runt objektet.
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

		addButtons(panel); // Lägger till knapparna

		//Inte glömma att lägga till dem i panelen man skapade överst.
		panel.add(txtFName);
		panel.add(txtLName);
		panel.add(txtPhoneNr);

		//Till sist lägger man till den i contentPane, vilket är huvudytan.
		this.contentPane.add(panel);
	}

	private void addButtons(JPanel panel)
	{
		//Vi skapar bara ett button objekt och ändrar för att senare lägga till i panelen
		//Men sen använder vi samma objekt och ändrar. Detta sparar minne och har samma
		//Effekt som om vi gjorde nya JButton objekt
		JButton button = new JButton("Lägg till");
		button.setLocation(20, 270);
		//Här kopplar vi vår actionListener som man finner överst i klassen
		button.addActionListener(new btnListener());
		//Dimension d är ett objekt som håller koll på dimensioner. Vi frågar button objektet vilken storlek
		//Den föredrar och använder.
		Dimension d = button.getPreferredSize();
		button.setSize(d);
		panel.add(button); // Lägger till i panelen

		button = new JButton("<<");
		button.setLocation((int) (20 + d.getWidth() + 5), 270);
		button.setSize(d);
		panel.add(button);

		button = new JButton(">>");
		button.setLocation((int) ((20 + (d.getWidth() *  2) + 5) ), 270);
		button.setSize(d);
		panel.add(button);
	}

	// Initiserar objekten
	private void InitiateInstanceVariabels()
	{
		this.txtFName = new JTextField();
		this.txtLName = new JTextField();
		this.txtPhoneNr = new JTextField();

		this.listPerson = new JList<>();
		this.current = -1;
		// Kopplar contentPane med fönstret
		this.contentPane = this.getContentPane();
		this.phoneBook = new PhoneBook();
	}

	public static void main(String[] args)
	{
		GUIPhoneBook2 book = new GUIPhoneBook2();
		book.setVisible(true);
	}

}