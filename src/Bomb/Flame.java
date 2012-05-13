package Bomb;

import java.util.concurrent.TimeUnit;

import Fields.Field;

public class Flame implements Runnable {
	protected int x;
	protected int y;
	protected int range;
	private Thread thread;
	private Field field;
	private static final int timer = 100;

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
	}
}
