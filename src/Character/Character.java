package Character;

import Bomb.Bomb;
import Level.Levellist;

/** Character sind Bomberman, Gegner usw. **/
public class Character {
	protected String name;
	protected double speed;
	protected double spawnx;
	protected double spawny;
	protected int pixsizex = 25;
	protected int pixsizey = 40;
	protected double posx;
	protected double posy;
	protected int maxbombs;
	protected int bombs = 0;
	protected int bombrange;
	protected int bombtimer;
	protected int lifes;

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

	public Character(String name, double spawnx, double spawny, double speed,
			int maxbombs, int bombrange, int bombtimer, int lifes) {
		this.name = name;
		this.spawnx = spawnx;
		this.spawny = spawny;
		this.speed = speed;
		this.maxbombs = maxbombs;
		this.bombrange = bombrange;
		this.bombtimer = bombtimer;
		this.lifes = lifes;
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
		return ((int) (posx * 50 - pixsizex * 0.5));
	}

	public int getDrawy() {
		return ((int) (posy * 50 - pixsizey * 0.5));
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

	public void placebomb() {
		if (bombs < maxbombs) {
			bombs++;
			Bomb bomb = new Bomb(Levellist.currentlevel, (int) (posx),
					(int) (posy), this, bombtimer, bombrange);
			bomb.start();
		}
	}

	public void kill() {
		System.out.println(this.name + " dies!");
		lifes--;
		if (lifes <= 0) {
			System.out.println("Game over for " + this.name);
		} else {
			spawn();
		}
	}

	public void move(int dirx, int diry) {
		double newposx = (posx) + speed * dirx;
		double newposy = (posy) + speed * diry;
		double newborderposx = (posx + dirx * pixsizex * 0.5) + speed * dirx;
		double newborderposy = (posx + diry * pixsizey * 0.5) + speed * diry;
		int oldx = (int) (posx);
		int oldy = (int) (posy);
		int newborderx = (int) (newborderposx);
		int newbordery = (int) (newborderposy);
		int newx = (int) (newposx);
		int newy = (int) (newposy);

		if (((newx - oldx) != 0) || ((newy - oldy) != 0)) {
			// Kann dieses Feld überhaupt betreten werden?
			if (Levellist.currentlevel.getField(newx, newy).enter(this)) {
				// Kann betreten werden
				Levellist.currentlevel.getField(oldx, oldy).leave(this);
				posx = newposx;
				posy = newposy;
			} else {
				// Gehe bis zum Rand
				posx = newposx - dirx * pixsizex * 0.5;
				posx = newposy - diry * pixsizey * 0.5;

			}
		} else {

			// Würde der Rand ein neues Feld betreten?
			if (((newborderx - oldx) != 0) || ((newbordery - oldy) != 0)) {
				// Kann dieses Feld überhaupt betreten werden?
				if (Levellist.currentlevel.getField(newx, newy).enterborder(this)) {
					//Kann betreten werden
					//Gehe weiter
					posx = newposx;
					posy = newposy;
				} else {
					// Gehe bis zum Rand
					posx = newposx - dirx * pixsizex * 0.5;
					posx = newposy - diry * pixsizey * 0.5;
				}
			} else {
				posx = newposx;
				posy = newposy;
			}
		}
	}

	public void spawn() {
		posx = spawnx;
		posy = spawny;
		System.out.println(name + " spawns at " + posx + "," + posy);
		if (!Levellist.currentlevel.getField((int) (posx), (int) (posy)).enter(
				this)) {
			System.out.println("Invalid Spawn point for " + name);
		}
	}
}
