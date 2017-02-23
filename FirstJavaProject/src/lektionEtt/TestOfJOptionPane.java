import javax.swing.JOptionPane;

public class TestOfJOptionPane
{

	public static void main(String[] args)
	{
		// Visar ett messagefönster
		/* JOptionPane.showMessageDialog(null, "Java är lagom"); */

		// MessageFönster med en titel och ikon
		/*
		 * JOptionPane.showMessageDialog(null,
		 * "Java är fortfarande Lagom\nNy Rad", "Detta är en titel",
		 * JOptionPane.QUESTION_MESSAGE);
		 */

		// Inmatning via fönster
		String name = JOptionPane.showInputDialog("Mata in ditt namn ");

		/* JOptionPane.showMessageDialog(null, "Hejsan " + name); */

		String input = JOptionPane.showInputDialog("Mata in ditt fördelseår: ");

		int age = 2017 - Integer.parseInt(input);

		JOptionPane.showMessageDialog(null, "Hejsan " + name + "\nDu är " + age + " år gammal.");
		// Avslutar programmet, skickar man 0 så stängdes det av korrekt
		System.exit(0);

	}

}
