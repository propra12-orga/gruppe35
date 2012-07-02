package Powerup;

import main.GlobalGraphics;
import main.Poweruplist;
import Character.Player;

public class Bup extends Powerup {
	public void pickup(Player player) {
		// Nicht mehr zeichnen
		synchronized (Poweruplist.list) {
			Poweruplist.list.remove(this);
		}
		player.setMaxbombs(player.getMaxbombs() + 1); // Bombenreichweite um 1
														// erhöhen
	}

	public Bup(int x, int y) {
		super(x, y);
		imageID = 21;
	}

}
