package Character;

import main.Enemylist;
import Level.Levellist;

/**
 * 
 * The enemy class is the parent class for all AI controlled Characters
 * 
 * When starting a level the Enemies can be spawned with the spawn() method at
 * the spawnx,spawny position
 * 
 * If an enemy dies it is simply removed from the game
 * 
 * <P>
 * 
 * @author Peet
 */

public class Enemy extends Character {

	double spawnx;
	double spawny;

	public Enemy(double spawnx, double spawny) {
		super();
		this.spawnx = spawnx;
		this.spawny = spawny;
	}

	public void kill() {
		// Verlasse Feld
		Levellist.activeLevel.getField((int) (posx), (int) (posy)).leave(this);
		// Nicht mehr zeichnen
		Enemylist.list.remove(this);
	}

	public void spawn() {
		spawn(spawnx, spawny);
	}

}
