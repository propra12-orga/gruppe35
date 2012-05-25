package Level;

import Fields.Field;

public class Level {
	private int xsize;
	private int ysize;
	protected boolean locked = true;
	private int spawnpoints[][]; // [number][x,y]

	public double getSpawnx() {
		return (spawnpoints[0][0] + 0.5);
	}

	public double getSpawny() {
		return (spawnpoints[0][1] + 0.5);
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	protected Field[][] field; /* Array für alle Felder */

	public int getXsize() {
		return xsize;
	}

	public void setXsize(int xsize) {
		this.xsize = xsize;
	}

	public int getYsize() {
		return ysize;
	}

	public void setYsize(int ysize) {
		this.ysize = ysize;
	}

	public Level(int xsize, int ysize, int spawnpoints[][]) {
		this.xsize = xsize;
		this.ysize = ysize;
		this.spawnpoints = spawnpoints;
		this.field = new Field[xsize][ysize];
	}

	public void setField(int x, int y, Field field) {
		this.field[x][y] = field.copy();
	}

	public Field getField(int x, int y) {
		// Falls x und y nicht im Bereich ist, gebe nullpointer zurück
		if ((x >= 0) && (x < xsize) && (y >= 0) && (y < ysize)) {
			return (field[x][y]);
		} else {
			return (null);
		}
	}

	public void draw() {
		String line = "";
		for (int y = ysize - 1; y >= 0; y--) {
			for (int x = 0; x < xsize; x++) {
				if (field[x][y].isSolid() == true) {
					line += "# ";
				} else {
					line += "O ";
				}
			}
			System.out.println(line);
			line = "";
		}

	}

}
