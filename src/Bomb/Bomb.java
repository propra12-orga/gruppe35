package Bomb;

import java.util.concurrent.TimeUnit;

import main.Bomblist;
import Character.Character;
import Fields.Field;
import Level.Level;
//import main.Global;

/**
 * 
 * This is the Bomb class. It is responsible for running a timer thread, that
 * causes the bomb to explode when it expires. However, the bomb can also
 * explode when another bomb in its vicinity explodes.
 * 
 * The "owner" is the Character that has placed this bomb The "timer" is the
 * time in seconds it takes until this bomb explodes The "range" is the range of
 * the fire beam this bomb creates when it explodes "x" and "y" are the position
 * in Field coordinates of this bomb in the level The "pixsizex" and "pixsizey"
 * variables are important for the GUI for drawing only
 * 
 * The destruction() method is called when the bomb explodes. Then the timer
 * thread is stopped, the owner gets its bomb back, this bomb is stopped being
 * drawn and the Flames are created that cause the destruction of the
 * environment Flames can be stopped by solid obstacles or other bombs. The
 * createFlame() method creates a new Flame on the destroyed Field and tells the
 * Field that it just has been destroyed by a bomb.
 * <P>
 * 
 * @author Peet
 */

public class Bomb implements Runnable {
	protected Character owner;
	protected int timer;
	protected int range;
	protected int x;
	protected int y;
	protected int pixsizex = 60; // 30
	protected int pixsizey = 60; // 30
	// Friedrich: sqsize sollte global sein!
	protected int sqsize = 100; // Friedrich: Übereinstimmung mit GUI...

	public int getPixsizex() {
		return pixsizex;
	}

	public int getDrawx() {
		return ((int) (x * sqsize + (sqsize - pixsizex) * 0.5));
	}

	public int getDrawy() {
		return ((int) (y * sqsize + (sqsize - pixsizey) * 0.5));
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

	private Thread thread = null;
	Level level;

	public Bomb(Level level, int x, int y, Character owner, int timer, int range) {
		this.x = x;
		this.y = y;
		this.level = level;
		Field field = level.getField(x, y);
		field.setBomb(this);
		this.owner = owner;
		this.timer = timer;
		this.range = range;
	}

	public synchronized void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
			synchronized (Bomblist.list) {
				Bomblist.list.add(this);
			}
		}
	}

	public synchronized void stop() {
		if (thread != null)
			thread = null;
	}

	public void run() {
		while (thread != null) {
			try {
				TimeUnit.SECONDS.sleep(timer);
			} catch (InterruptedException e) {
			}
			destruction();
		}
	}

	public void destruction() {
		stop(); // Stoppe den Thread
		System.out.println("Boom!");
		owner.setBombs(owner.getBombs() - 1); // Bombenanzahl des Besitzers
												// anpassen
		// Nicht mehr zeichnen
		synchronized (Bomblist.list) {
			Bomblist.list.remove(this);
		}
		// Zerstörung von Feldern und Bomben in Reichweite und Erzeugung von
		// Flammen

		// Feld auf dem diese Bombe liegt
		level.getField(x, y).setBomb(null); // Diese Bombe soll nur einmal
											// explodieren
		createFlame(x, y);

		// x Richtung
		for (int posx = x + 1; posx <= (x + range); posx++) {
			// Erzeuge Flammen
			if (!createFlame(posx, y))
				break;
		}
		// -x Richtung
		for (int posx = x - 1; posx >= (x - range); posx--) {
			// Erzeuge Flammen
			if (!createFlame(posx, y))
				break;
		}
		// y Richtung
		for (int posy = y + 1; posy <= (y + range); posy++) {
			// Erzeuge Flammen
			if (!createFlame(x, posy))
				break;
		}
		// -y Richtung
		for (int posy = y - 1; posy >= (y - range); posy--) {
			// Erzeuge Flammen
			if (!createFlame(x, posy))
				break;
		}

		owner = null; // Besitzer dereferenzieren
		level = null; // Level dereferenzieren
	}

	private boolean createFlame(int x, int y) {
		Field field = level.getField(x, y);
		if (field != null) {
			field.destruction();
			// Falls das Feld zerstörbar ist, wird es in ein anderes umgewandelt
			field.transform(level, x, y);
			// Falls das Feld solid ist oder eine Bombe beinhaltet wird keine
			// Flamme
			// erzeugt

			if ((field.isSolid()) || (field.getBomb() != null)) {
				return (false);
			}
			Flame flame = new Flame(field, x, y);
			flame.start();

			return (true);
		} else {
			return (false);
		}
	}

}
