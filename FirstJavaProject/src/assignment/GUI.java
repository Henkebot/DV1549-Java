package assignment;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame
{
	private static final int WIDTH = 600;
	private static final int HEIGHT = 300;
	private JTextField inputField;
	private Container contentPane;

	private Queue<Integer> queue;

	public GUI(String title)
	{
		super(title);
		initFields();
		createFrame();
		configureFrame();
	}
	
	private void configureFrame()
	{
		contentPane.setLayout(new GridLayout(2,1));
		
		buildUpperPart();
		buildLowerPart();
		buildMenu();
	}
	
	private void buildUpperPart()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Queue"));
		
		inputField.setBorder(BorderFactory.createTitledBorder("Integer input"));
		inputField.setSize(260, 50);
		inputField.setLocation(20, 20);
		
		addButtons(panel);
		
		panel.add(inputField);
		
		contentPane.add(panel);
	}
	
	private void addButtons(JPanel panel)
	{
		/*Buttons TODO::
		 * Lägga till
		 * Visa det heltal som är först i kön
		 * Ta bort det heltal som är först
		 * visa hela köns innehåll
		 * Spara ner på fil
		 * Läsa upp från kö
		 */
		
		JButton button = new JButton("Add");
		
		button.setSize(button.getPreferredSize());
		button.setLocation(300, 20);
		
		panel.add(button);
		
		
	}
	
	private void buildLowerPart()
	{
		
	}
	
	private void buildMenu()
	{
		
	}
	
	private void createFrame()
	{
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initFields()
	{
		inputField = new JTextField();
		contentPane = this.getContentPane();
		queue = new Queue<>();
	}
	
	public static void main(String[] args)
	{
		GUI gui = new GUI("Integer Queue");
		gui.setVisible(true);
	}
}
