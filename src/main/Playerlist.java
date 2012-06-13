package main;

import java.util.ArrayList;
import Character.Character;

/**
 * The Playerlist class keeps track of all the Characters which are currently
 * present. At the moment only two Players are supported
 * <P>
 * 
 * @author Peet
 */

public class Playerlist {
	public static ArrayList<Character> list = new ArrayList<Character>();

	public static void load() {
		list.clear();
		Character player1 = new Character("Player 1", 0.2, 2, 2, 2, 3);
		Character player2 = new Character("Player 2", 0.1, 2, 2, 2, 1);

		list.add(player1);
		list.add(player2);
	}

}
