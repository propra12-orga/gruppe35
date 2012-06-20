package Character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import main.Global;
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

	public Image characterImage;
	public Image characterImageStanding;

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

	public Character(double speed, Image characterImage,
			Image characterImageStanding) {
		this.speed = speed;
		this.characterImage = characterImage;
		this.characterImageStanding = characterImageStanding;
		pixsizex = (int) (Global.sqsize * 0.5); // 25
		pixsizey = (int) (Global.sqsize * 0.8); // 40
	}

	public Character() {
		speed = 0.0;
		this.characterImage = null;
		this.characterImageStanding = null;
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
		return ((int) (posx * Global.sqsize - pixsizex * 0.5));
	}

	public int getDrawy() {
		return ((int) (posy * Global.sqsize - pixsizey * 0.5));
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
	}

	public void move(int dirx, int diry) {
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
			// Würde ein neues Feld betreten?
			if (((newx - oldx) != 0) || ((newy - oldy) != 0)) {
				// Kann dieses Feld überhaupt betreten werden?
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

	public void spawn(double spawnx, double spawny) {
		posx = spawnx;
		posy = spawny;
		if (!Levellist.activeLevel.getField((int) (posx), (int) (posy)).enter(
				this)) {
			System.out.println("Invalid Spawn point at " + (int) (posx) + ", "
					+ (int) (posy));
		}
	}

	public void DrawComponent(Graphics g, JPanel panel) {
		if (this.isMoving()) {
			g.drawImage(this.characterImage, this.getDrawx(), this.getDrawy(),
					this.getPixsizex(), this.getPixsizey(), panel);
		} else {
			g.drawImage(this.characterImageStanding, this.getDrawx(),
					this.getDrawy(), this.getPixsizex(), this.getPixsizey(),
					panel);
		}
	}
}
