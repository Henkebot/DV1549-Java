package uppgifter;

import javax.swing.JOptionPane;

/*
 * Gör ett java-program enligt följande:
– Låt användaren först mata in en sträng (exempelvis ”katten smyger i natten”) och
därefter ytterligare en sträng (exempelvis ”att”) och bestäm hur många gånger den
andra strängen förekommer i den första (två i exemplet ovan). Inmatning och utskrift
ska genomföras med dialogrutor.

 * 
 */
public class Uppgift3
{

	public static void main(String[] args)
	{
		String userInput = null;
		String keyword = null;
		
		userInput = JOptionPane.showInputDialog("Mata in en sträng");
		keyword = JOptionPane.showInputDialog("Mata in det ord du vill räkna");
		int occurrances = 0;
		
		for (int i = 0; i < userInput.length(); i++)
		{
			boolean found = true;
			for (int j = 0; j < keyword.length() && found; j++)
			{
				if(userInput.charAt(i) == keyword.charAt(j))
				{
					found = true;
					i++;
				}
				else
				{
					found = false;
					
				}
				
			}
			if(found) occurrances++;
				
		}
		
		JOptionPane.showMessageDialog(null, "Det fanns " + occurrances + " " + keyword + " i " + userInput);
		
		
	}

}
