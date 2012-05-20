package Character;

import Bomb.Bomb;
import Level.Level;

/** Character sind Bomberman, Gegner usw. **/
public class Character {
	protected String name;
	protected double speed;
	protected double spawnx;
	protected double spawny;
	protected double posx;
	protected double posy;
	protected int maxbombs;
	protected int bombs = 0;
	protected int bombrange;
	protected int bombtimer;
	protected int lifes;
	protected Level level;

	public Character(String name, double spawnx, double spawny, Level level,
			double speed, int maxbombs, int bombrange, int bombtimer, int lifes) {
		this.name = name;
		this.spawnx = spawnx;
		this.spawny = spawny;
		this.speed = speed;
		this.maxbombs = maxbombs;
		this.bombrange = bombrange;
		this.bombtimer = bombtimer;
		this.lifes = lifes;
		this.level = level;
		spawn();
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void placebomb() {
		if (bombs < maxbombs) {
			bombs++;
			Bomb bomb = new Bomb(level, (int) (posx), (int) (posy), this,
					bombtimer, bombrange);
			bomb.start();
		}
	}

	public void kill() {
		System.out.println(this.name + " dies!");
		lifes--;
		if (lifes <= 0) {
			System.out.println("Game over for " + this.name);
			level = null;
		} else {
			spawn();
		}
	}

	public void move(int dirx, int diry) {
		double newposx = posx + speed * dirx;
		double newposy = posy + speed * diry;
		int oldx = (int) (posx);
		int oldy = (int) (posy);
		int newx = (int) (newposx);
		int newy = (int) (newposy);

		// Würde neues Feld betreten werden?
		if (((newx - oldx) != 0) || ((newy - oldy) != 0)) {
			// Kann dieses Feld überhaupt betreten werden?
			if (!level.getField(newx, newy).enter(this)) {
				// Kann nicht betreten werden, gehe nur bis zum Rand
				posx = newx - dirx * 1e-3;
				posy = newy - diry * 1e-3;
			} else {
				// Kann betreten werden, gehe weiter
				// Verlasse auch das alte Feld
				level.getField(oldx, oldy).leave(this);
				posx = newposx;
				posy = newposy;
			}
		} else {
			posx = newposx;
			posy = newposy;
		}
	}

	public void spawn() {
		posx = spawnx;
		posy = spawny;
		System.out.println(name + " spawns at " + posx + "," + posy);
		if (!level.getField((int) (posx), (int) (posy)).enter(this)) {
			System.out.println("Invalid Spawn point for " + name);
		}
	}
	
	public void nextlevel(Level nextlevel){
		level = nextlevel;
		spawn();
	}
}
