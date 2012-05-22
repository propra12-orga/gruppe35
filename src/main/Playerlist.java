package main;

import java.util.ArrayList;

import Character.Character;

public class Playerlist {
	public static ArrayList<Character> list = new ArrayList<Character>();

	public static void load() {
		list.clear();
		Character player1 = new Character("Player 1", 0.5, 0.5, 0.2, 2, 2, 2, 3);
		list.add(player1);
	}

}
