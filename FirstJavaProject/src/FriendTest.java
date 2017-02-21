
public class FriendTest
{

	public static void main(String[] args)
	{
		Friend bestFriend = new Friend("Sigfrid", "Larsson", 1988);

		/*
		 * System.out.println("Min Bästa vän är " + bestFriend.getFirstName() +
		 * " " + bestFriend.getLastName() + " som är född år " +
		 * bestFriend.getBirthYear());
		 */

		// Anropar toString() den kan bara överlagrad
		System.out.println("Min bästa vän är " + bestFriend);
	}
}
