package Character;

public class Control {
	protected int up;
	protected int down;
	protected int right;
	protected int left;
	protected int placeBomb;

	public Control() {
		this.up = 40;
		this.down = 38;
		this.right = 39;
		this.left = 37;
		this.placeBomb = 10;
	}

	public Control(String Spieler) {
		if (Spieler == "Boltzmann") {
			this.up = 40;
			this.down = 38;
			this.right = 39;
			this.left = 37;
			this.placeBomb = 10;
		}
		if (Spieler == "Feynman") {
			this.up = 83;
			this.down = 87;
			this.right = 68;
			this.left = 65;
			this.placeBomb = 32;
		}
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getPlaceBomb() {
		return placeBomb;
	}

	public void setPlaceBomb(int placeBomb) {
		this.placeBomb = placeBomb;
	}

}
