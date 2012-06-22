package Bomb;

import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import main.Flamelist;
import main.Global;
import Fields.Field;

/**
 * This is the Flame class. It is responsible for running a timer thread, that
 * causes the Flame to disappear when it expires. Flames on a field cause
 * characters to die when they enter it.
 * 
 * "x" and "y" are the position in Field coordinates of this bomb in the level
 * The "pixsizex" and "pixsizey" variables are important for the GUI for drawing
 * only The "timer" is the time in milliseconds it takes until this Flame
 * disappears "thread" is the timer thread that runs until this flame disappears
 * "field" is the Field this flame is placed on. When it disappears, the Field
 * stops killing Characters that enter it.
 * <P>
 * 
 * @author Peet
 */

public class Flame implements Runnable {
	protected int x;
	protected int y;
	protected int pixsizex = Global.sqsize;
	protected int pixsizey = Global.sqsize;
	protected Image image;

	public void DrawComponent(Graphics g, JPanel panel) {
		g.drawImage(this.image, this.getDrawx(), this.getDrawy(),
				this.getPixsizex(), this.getPixsizey(), panel);
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

	private Thread thread;
	private Field field;
	private static final int timer = 1000;

	public int getDrawx() {
		return ((int) (x * Global.sqsize + (Global.sqsize - pixsizex) * 0.5));
	}

	public int getDrawy() {
		return ((int) (y * Global.sqsize + (Global.sqsize - pixsizey) * 0.5));
	}

	public Flame(Field field, int x, int y, int dir) {
		this.field = field;
		this.x = x;
		this.y = y;
		
		switch (dir) {
		case 0:
			//center
			this.image = Global.flame;
			break;
		case 1: case 3:
			//horizontal
			this.image = Global.flame_h;
			break;
		case 2: case 4:
			//vertikal
			this.image = Global.flame_v;
			break;

		default:
		}
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
		// F�ge Flammen zum Feld hinzu
		field.addFlame(1);
		// Zur Flamelist hinzuf�gen
		synchronized (Flamelist.list) {
			Flamelist.list.add(this);
		}
	}

	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(timer);
		} catch (InterruptedException e) {
		}
		// Entferne Flammen wieder vom Feld
		field.addFlame(-1);
		// Dereferenzieren
		thread = null;
		field = null;
		synchronized (Flamelist.list) {
			Flamelist.list.remove(this);
		}
	}
}
