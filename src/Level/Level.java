package Level;

import java.awt.Graphics;

import javax.swing.JPanel;

import main.Global;
import Fields.Field;

/**
 * The Level class keeps track of alle the Fields and their positions and of all
 * the spawnpoints. The spawnpoints are saved in the "spawnpoints" jagged array,
 * where the first coordinate specifies the number and the second coordinate the
 * x and y position The "xsize" and "ysize" variables define how many Fields the
 * Level contains in each direction. The "locked" has no purpose yet, however it
 * can be used in the future to make the player unlock Levels by completing
 * former Levels.
 * <P>
 * 
 * @author Peet
 * 
 */

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

	public void drawComponent(Graphics g, JPanel panel) {
		for (int y = 0; y < this.getXsize(); y++) {
			for (int x = 0; x < this.getYsize(); x++) {
				g.drawImage(this.getField(x, y).getImage(), x * Global.sqsize,
						y * Global.sqsize, Global.sqsize, Global.sqsize, panel);
			}
		}
	}

}
