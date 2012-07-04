package Powerup;

import main.Poweruplist;
import Character.Player;

public class Rup extends Powerup {
	public void pickup(Player player) {
		// Nicht mehr zeichnen
		synchronized (Poweruplist.list) {
			Poweruplist.list.remove(this);
		}
		player.setBombrange(player.getBombrange()+1); //Bombenreichweite um 1 erhöhen
	}
	
	public Rup(int x, int y){
		super(x,y);
		imageID = 20;
		System.out.println("Rup at " + x + "," + y);
	}
	

}
