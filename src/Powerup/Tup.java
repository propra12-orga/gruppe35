package Powerup;

import main.Global;
import main.Poweruplist;
import Character.Player;

public class Tup extends Powerup {
	public void pickup(Player player) {
		// Nicht mehr zeichnen
		synchronized (Poweruplist.list) {
			Poweruplist.list.remove(this);
		}
		player.setSpeed(player.getSpeed() * 1.25); // Geschwindigkeit um 25%
													// erhöhen
	}

	public Tup(int x, int y) {
		super(x, y);
		image = Global.Tup;
		System.out.println("Tup at " + x + "," + y);
	}

}
