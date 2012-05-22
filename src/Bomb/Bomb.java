/**
 This is the bomb class, which creates thread, that runs for (timer) seconds.
 After that the bomb explodes, creating Flame classes in the blast range.
 The destruction() method can also be called from the Field class, when it is subject to flames.
 @author Peet
 */

package Bomb;

import java.util.concurrent.TimeUnit;

import main.Bomblist;
import Character.Character;
import Fields.Field;
import Level.Level;

public class Bomb implements Runnable {
	protected Character owner;
	protected int timer;
	protected int range;
	protected int x;
	protected int y;
	protected int pixsizex = 30;
	protected int pixsizey = 30;

	public int getPixsizex() {
		return pixsizex;
	}

	public int getDrawx() {
		return ((int) (x * 50 + (50 - pixsizex) * 0.5));
	}

	public int getDrawy() {
		return ((int) (y * 50 + (50 - pixsizey) * 0.5));
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
