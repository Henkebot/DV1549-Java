
public class FriendTest
{

	public static void main(String[] args)
	{
		Friend bestFriend = new Friend("Sigfrid", "Larsson", 1988);

		/*
		 * System.out.println("Min B�sta v�n �r " + bestFriend.getFirstName() +
		 * " " + bestFriend.getLastName() + " som �r f�dd �r " +
		 * bestFriend.getBirthYear());
		 */

		// Anropar toString() den kan bara �verlagrad
		System.out.println("Min b�sta v�n �r " + bestFriend);
	}
}
