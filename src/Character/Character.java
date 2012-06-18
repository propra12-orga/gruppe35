package Character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import main.Global;
import main.Menu;
import Bomb.Bomb;
import Level.Levellist;

/**
 * 
 * 
 * The Character class specifies the Player controlled Characters.
 * 
 * "name" String specifies the Charakter name "speed" defines how fast the
 * Charakter can move "pixsizex" and "pixsizey" define the size of the Character
 * in Pixel which is important for drawing, but also for moving. "posx" and
 * "posy" specify the current position of the character "maxbombs" defines the
 * maximum number of bombs the player can have in the Level at the same time
 * "bombs" is the current number of active bombs "bombrange" is the range the
 * bombs placed by this Character possess "bombtimer" is the time in seconds it
 * takes for a bomb places by this Character to explode "lifes" is the number of
 * times this Character can die before it is game over
 * 
 * The placebomb() method allows the Character to create a new Bomb on the Field
 * it is standing on The kill() method is called to kill this character The
 * move() method is called whenever the Player presses the movement keys. It
 * then moves in the corresponding direction if no blocking terrain or other
 * problem occurs The spawn() method (re)places the Character in the Level in
 * the beginning or when it dies.
 * <P>
 * 
 * @author Peet
 */

public class Character {
	protected String name;
	protected double speed;
	protected int pixsizex = (int) (Global.sqsize * 0.5); // 25
	protected int pixsizey = (int) (Global.sqsize * 0.8); // 40
	protected double posx;
	protected double posy;
	protected int maxbombs;
	protected int bombs = 0;
	protected int bombrange;
	protected int bombtimer;
	protected int lifes;
	protected boolean movingUp = false;
	protected boolean movingDown = false;
	protected boolean movingRight = false;
	protected boolean movingLeft = false;
	public Image characterImage;
	public Image characterImageStanding;
	protected Control control;

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

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

	public Character(String name, double speed, int maxbombs, int bombrange,
			int bombtimer, int lifes, Image characterImage,
			Image characterImageStanding) {
		this.name = name;
		this.speed = speed;
		this.maxbombs = maxbombs;
		this.bombrange = bombrange;
		this.bombtimer = bombtimer;
		this.lifes = lifes;
		this.control = new Control(name);
		this.characterImage = characterImage;
		this.characterImageStanding = characterImageStanding;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getMaxbombs() {
		return maxbombs;
	}

	public boolean isMoving() {
		if (this.movingRight || this.movingLeft || this.movingDown
				|| this.movingUp) {
			return (true);
		} else {
			return (false);
		}
	}

	public void setMaxbombs(int maxbombs) {
		this.maxbombs = maxbombs;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public int getBombrange() {
		return bombrange;
	}

	public void setBombrange(int bombrange) {
		this.bombrange = bombrange;
	}

	public int getBombtimer() {
		return bombtimer;
	}

	public void setBombtimer(int bombtimer) {
		this.bombtimer = bombtimer;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public void placebomb() {
		if ((bombs < maxbombs)
				&& (Levellist.activeLevel.getField((int) (posx), (int) (posy))
						.getBomb() == null)) {
			bombs++;
			Bomb bomb = new Bomb(Levellist.activeLevel, (int) (posx),
					(int) (posy), this, bombtimer, bombrange);
			bomb.start();
		}
	}

	public void kill() {
		System.out.println(this.name + " dies!");
		lifes--;
		// Verlasse Feld
		Levellist.activeLevel.getField((int) (posx), (int) (posy)).leave(this);
		if (lifes <= 0) {
			System.out.println("Game over for " + this.name);
			Menu.panelvisible = false;
			Menu.feld.initialize();
		} else {
			spawn();
		}
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

	public void spawn() {
		posx = Levellist.activeLevel.getSpawnx();
		posy = Levellist.activeLevel.getSpawny();
		System.out.println(name + " spawns at " + posx + "," + posy);
		if (!Levellist.activeLevel.getField((int) (posx), (int) (posy)).enter(
				this)) {
			System.out.println("Invalid Spawn point for " + name);
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
