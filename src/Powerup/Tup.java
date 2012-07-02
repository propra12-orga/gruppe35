package Powerup;

import main.Poweruplist;
import Character.Player;

public class Tup extends Powerup {
	public void pickup(Player player) {
		// Nicht mehr zeichnen
		synchronized (Poweruplist.list) {
			Poweruplist.list.remove(this);
		}
		player.setSpeed(player.getSpeed() * 1.25); // Geschwindigkeit um 25%
													// erh�hen
	}

	public Tup(int x, int y) {
		super(x, y);
		imageID = 19;
		System.out.println("Tup at " + x + "," + y);
	}

}
