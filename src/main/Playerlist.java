package main;

import java.util.ArrayList;
import Character.Player;

/**
 * The Playerlist class keeps track of all the Characters which are currently
 * present. At the moment only two Players are supported
 * <P>
 * 
 * @author Peet
 */

public class Playerlist {
	public static ArrayList<Player> list = new ArrayList<Player>();

	public static void load() {
		list.clear();
		Player player1 = new Player("Boltzmann", 0.03, 2, 2, 2, 3, 0, 3, 5);
		Player player2 = new Player("Feynman", 0.03, 2, 2, 2, 3, 1, 4, 6);

		list.add(player1);
		list.add(player2);
	}

}
