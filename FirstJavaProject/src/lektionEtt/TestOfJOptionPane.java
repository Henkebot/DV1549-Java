import javax.swing.JOptionPane;

public class TestOfJOptionPane
{

	public static void main(String[] args)
	{
		// Visar ett messagef�nster
		/* JOptionPane.showMessageDialog(null, "Java �r lagom"); */

		// MessageF�nster med en titel och ikon
		/*
		 * JOptionPane.showMessageDialog(null,
		 * "Java �r fortfarande Lagom\nNy Rad", "Detta �r en titel",
		 * JOptionPane.QUESTION_MESSAGE);
		 */

		// Inmatning via f�nster
		String name = JOptionPane.showInputDialog("Mata in ditt namn ");

		/* JOptionPane.showMessageDialog(null, "Hejsan " + name); */

		String input = JOptionPane.showInputDialog("Mata in ditt f�rdelse�r: ");

		int age = 2017 - Integer.parseInt(input);

		JOptionPane.showMessageDialog(null, "Hejsan " + name + "\nDu �r " + age + " �r gammal.");
		// Avslutar programmet, skickar man 0 s� st�ngdes det av korrekt
		System.exit(0);

	}

}
