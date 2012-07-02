package Character;

import java.util.Timer;
import java.util.TimerTask;

import main.GlobalGraphics;
import Bomb.Bomb;
import Level.Levellist;

/**
 * 
 * 
 * The Player class specifies the Player controlled Characters.
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

public class Player extends Character {
	protected String name;
	protected int maxbombs;
	protected int bombs = 0;
	protected int bombrange;
	protected int bombtimer;
	protected int lifes;

	protected boolean movingUp = false;
	protected boolean movingDown = false;
	protected boolean movingRight = false;
	protected boolean movingLeft = false;

	protected Control control;

	class Respawn extends TimerTask {
		public void run() {
			Player.this.spawn();
		}
	}

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

	public Player(String name, double speed, int maxbombs, int bombrange,
			int bombtimer, int lifes, int imageID, int imageIDstanding,int imageIDdead) {
		super(speed, imageID, imageIDstanding, imageIDdead);
		this.name = name;
		this.maxbombs = maxbombs;
		this.bombrange = bombrange;
		this.bombtimer = bombtimer;
		this.lifes = lifes;
		this.control = new Control(name);

		pixsizex = (int) (GlobalGraphics.sqsize * 0.5); // 25
		pixsizey = (int) (GlobalGraphics.sqsize * 0.8); // 40

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
		if (!dead) {
			if ((bombs < maxbombs)
					&& (Levellist.activeLevel.getField((int) (posx),
							(int) (posy)).getBomb() == null)) {
				bombs++;
				Bomb bomb = new Bomb(Levellist.activeLevel, (int) (posx),
						(int) (posy), this, bombtimer, bombrange);
				bomb.start();
			}
		}
	}

	public void kill() {
		if (!dead) {
			System.out.println(this.name + " dies!");
			dead = true;
			lifes--;
			// Verlasse Feld
			Levellist.activeLevel.getField((int) (posx), (int) (posy)).leave(
					this);
			if (lifes <= 0) {
				System.out.println("Game over for " + this.name);
			} else {
				// Respawn nach 2 Sekunden
				Timer timer = new Timer();
				timer.schedule(new Respawn(), 2000);
			}
		}
	}

	public void spawn() {
		spawn(Levellist.activeLevel.getSpawnx(),
				Levellist.activeLevel.getSpawny());
	}

}
