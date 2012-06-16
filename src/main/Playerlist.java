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
		Character player1 = new Character("Boltzmann", 0.02, 2, 2, 2, 3,
				Global.image1);
		Character player2 = new Character("Feynman", 0.01, 2, 2, 2, 1,
				Global.image2);

		list.add(player1);
		list.add(player2);
	}

}
