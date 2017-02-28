package lektionTre;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class TestGUI extends JFrame
{
	private class ButtonListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(arg0.getActionCommand().equals("Hit Me") )
			{
				updateNrOfHits();				
			}else if(arg0.getActionCommand().equals("Clear"))
			{
				clearNrOfHits();
			}
		}

		
	}
	private final int WIDTH = 300;
	private final int HEIGHT = 400;
	
	private JTextField txtNrOfHits;
	private JButton hitter;
	private JButton clear;
	private Container contentPane;
	private int nrOfClicks;

	public TestGUI(String arg0)
	{
		super(arg0);
		
		initFields();
		initFrame();
	}

	private void updateNrOfHits()
	{
		txtNrOfHits.setText("Klickat " + ++nrOfClicks);
	}
	
	private void clearNrOfHits()
	{
		nrOfClicks = 0;
		txtNrOfHits.setText("");
	}

	private void initFrame()
	{
		this.setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		
		//this.setLayout(new FlowLayout());
		//this.add(contentPane);
	}



	private void initFields()
	{
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		txtNrOfHits = new JTextField("Antal klickar: " + nrOfClicks);
		txtNrOfHits.setBounds(20, 20, 30, 30);
		contentPane.add(txtNrOfHits);
		
		hitter = new JButton("Hit Me");
		//hitter.setBounds(r);
		hitter.addActionListener(new ButtonListener());
		contentPane.add(hitter);
		
		
		clear = new JButton("Clear");
		clear.setBounds(20, 20, 30, 30);
		clear.addActionListener(new ButtonListener());
		contentPane.add(clear);
		
		
		
	}



	public static void main(String[] args)
	{

		TestGUI gui = new TestGUI("Hejsan");

	

	}
}
