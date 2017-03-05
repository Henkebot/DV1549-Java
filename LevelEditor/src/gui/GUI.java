package gui;

import editor.Editor;
import game.Game;

public class GUI
{
	private static Game	  game;
	private static Editor editor;

	public static void main(String[] args)
	{
		//editor = new Editor();
		game = new Game("jumpTest");
	}
}
