package Character;

import main.GlobalGraphics;
import Level.Levellist;

/**
 * 
 * 
 * The Character class is the parent class for Player and Enemy
 * 
 * "speed" defines how fast the Charakter can move "pixsizex" and "pixsizey"
 * define the size of the Character in Pixel which is important for drawing, but
 * also for moving. "posx" and "posy" specify the current position of the
 * character
 * 
 * The kill() method is called to kill this character The move() method is
 * called whenever the Character is supposed to move. It then moves in the
 * corresponding direction if no blocking terrain or other problem occurs The
 * spawn() method (re)places the Character in the Level in the beginning or when
 * it dies.
 * <P>
 * 
 * @author Peet
 */

public class Character {

	protected double speed;
	protected int pixsizex;
	protected int pixsizey;
	protected double posx;
	protected double posy;
	protected boolean dead = false;
	protected int imageID;
	protected int imageIDstanding;
	protected int imageIDdead;
	
	public int getPixsizex() {
		return pixsizex;
	}

	public void setPixsizex(int pixsizex) {
		this.pixsizex = pixsizex;
	}

	public int getPixsizey() {
		return pixsizey;
	}

	public void setPixsizey(int pixsizey) {
		this.pixsizey = pixsizey;
	}

	public Character(double speed, int imageID,
			int imageIDstanding, int imageIDdead) {
		this.speed = speed;
		this.imageID = imageID;
		this.imageIDstanding = imageIDstanding;
		this.imageIDdead = imageIDdead;
		pixsizex = (int) (GlobalGraphics.sqsize * 0.5); // 25
		pixsizey = (int) (GlobalGraphics.sqsize * 0.8); // 40
	}
	
	public Character() {
		speed = 0.0;
		this.imageID = -1;
		this.imageIDstanding = -1;
		this.imageIDdead = -1;
		pixsizex = 0;
		pixsizey = 0;
	}

	public boolean isMoving() {
		return (true);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getPosx() {
		return posx;
	}

	public int getDrawx() {
		return ((int) (posx * GlobalGraphics.sqsize - pixsizex * 0.5));
	}

	public int getDrawy() {
		return ((int) (posy * GlobalGraphics.sqsize - pixsizey * 0.5));
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public void kill() {
		// Verlasse Feld
		Levellist.activeLevel.getField((int) (posx), (int) (posy)).leave(this);
		dead = true;
	}

	public void move(int dirx, int diry) {
		if (!dead) {
			int oldx = (int) (posx);
			int oldy = (int) (posy);
			double newposx = (posx) + speed * dirx;
			double newposy = (posy) + speed * diry;
			int newx = (int) (newposx);
			int newy = (int) (newposy);
			// wird der Rand des Spielfeldes nicht verlassen?
			if ((newposx > 0.0) && (newposx < Levellist.activeLevel.getXsize())
					&& (newposy > 0.0)
					&& (newposy < Levellist.activeLevel.getYsize())) {
				// W�rde ein neues Feld betreten?
				if (((newx - oldx) != 0) || ((newy - oldy) != 0)) {
					// Kann dieses Feld �berhaupt betreten werden?
					if (Levellist.activeLevel.getField(newx, newy).enter(this)) {
						// Kann betreten werden
						// Gehe weiter
						posx = newposx;
						posy = newposy;
						// Verlasse altes Feld
						Levellist.activeLevel.getField(oldx, oldy).leave(this);
					} else {
						// Kann nicht betreten werden
					}

				} else {
					// Kein neues Feld wird betreten, gehe einfach weiter
					posx = newposx;
					posy = newposy;
				}
			}
		}
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void spawn(double spawnx, double spawny) {
		posx = spawnx;
		posy = spawny;
		if (!Levellist.activeLevel.getField((int) (posx), (int) (posy)).enter(
				this)) {
			System.out.println("Invalid Spawn point at " + (int) (posx) + ", "
					+ (int) (posy));
		}
		System.out.println("Spawn at " + (int) (posx) + ", " + (int) (posy));
		dead = false;
	}
	

	public int getImageID() {
		if (this.dead) {
			return(imageIDdead);
		} else {
			if (this.isMoving()) {
				return(imageID);
			} else {
				return(imageIDstanding);
			}
		}
	}
}
