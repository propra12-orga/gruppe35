public class Character {
	protected String name;
	protected double speed;
	protected double posx;
	protected double posy;
	protected int maxbombs;
	protected int bombs = 0;
	protected int bombrange;
	protected int bombtimer;
	protected int lifes;

	public Character(String name, double posx, double posy, double speed,
			int maxbombs, int bombrange, int bombtimer, int lifes) {
		this.name = name;
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.maxbombs = maxbombs;
		this.bombrange = bombrange;
		this.bombtimer = bombtimer;
		this.lifes = lifes;

	}

	public Character() {
		this("Guy", 0.0, 0.0, 1.0, 1, 1, 2, 3); // Call other constructor
	}

	public String Getname() {
		return (name);
	}

	public double Getposx() {
		return (posx);
	}

	public double Getposy() {
		return (posy);
	}

	public double Getspeed() {
		return (speed);
	}

	public int Getlifes() {
		return (lifes);
	}

	public int Getbombs() {
		return (bombs);
	}

	public void Setbombs(int bombs) {
		this.bombs = bombs;
	}

	public void Placebomb() {
		if (bombs < maxbombs) {
			bombs++;
			Bomb bomb = new Bomb((int) (posx + 0.5), (int) (posy + 0.5), this,
					bombtimer, bombrange);
			bomb.start();
		}
	}

	public void Kill() {
		System.out.println(this.name + "dies!");
		lifes--;
		if (lifes <= 0)
			System.out.println("Game over for " + this.name);

	}

}
