package uppgifter;

import java.util.Scanner;

public class Uppgift2
{
	public static void main(String[] args)
	{
		String[] strArr = new String[10];
		Scanner input = new Scanner(System.in);
		
		int iterator = 0;
		
		while(iterator != 5)
		{
			strArr[iterator++] = input.nextLine();
			strArr[strArr.length - iterator] = strArr[iterator - 1];
		}
	
		
		for (int i = 0; i < strArr.length; i++)
		{
			System.out.print(strArr[i] + " ");
		}
		
		String userInput = input.nextLine();
		int nrOfOccur = 0;
		for (int i = 0; i < strArr.length; i++)
		{
			if(strArr[i].contains(userInput))nrOfOccur++;
		}
		System.out.println("Number of Occurrances is: " + nrOfOccur);
		
		String noArrayHere = "";
		
		for (int i = 0; i < strArr.length; i++)
		{
			noArrayHere += strArr[i] + " ";
		}
		
		System.out.println(noArrayHere);
	}
}
