package gui;

import editor.Editor;
import game.Game;

public class GUI
{
	private Game		  game;
	private static Editor editor;

	public static void main(String[] args)
	{
		System.out.println("Starting editor!");
		editor = new Editor();
	}
}
