package Bomb;

import java.util.concurrent.TimeUnit;

import main.Flamelist;
import Fields.Field;

public class Flame implements Runnable {
	protected int x;
	protected int y;
	protected int pixsizex = 50;
	protected int pixsizey = 50;
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

	protected int range;
	private Thread thread;
	private Field field;
	private static final int timer = 1000;

	public int getDrawx() {
		return ((int) (x * 50 + (50 - pixsizex) * 0.5));
	}

	public int getDrawy() {
		return ((int) (y * 50 + (50 - pixsizey) * 0.5));
	}

	public Flame(Field field, int x, int y) {
		this.field = field;
		this.x = x;
		this.y = y;
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
		// Füge Flammen zum Feld hinzu
		field.addFlame(1);
		// Zur Flamelist hinzufügen
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
