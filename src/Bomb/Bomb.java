package Bomb;

import java.util.concurrent.TimeUnit;

import main.Bomblist;
import main.GlobalGraphics;
import main.GlobalSounds;
import Character.Player;
import Fields.Field;
import Level.Level;

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
	protected Player owner;
	protected int timer;
	protected int range;
	protected int x;
	protected int y;
	protected int pixsizex = (int) (GlobalGraphics.sqsize * 0.6);
	protected int pixsizey = (int) (GlobalGraphics.sqsize * 0.6);
	protected int imageID = 11;

	public int getImageID() {
		return (imageID);
	}

	private Thread thread = null;
	Level level;

	public int getPixsizex() {
		return pixsizex;
	}

	public int getDrawx() {
		return ((int) (x * GlobalGraphics.sqsize + (GlobalGraphics.sqsize - pixsizex) * 0.5));
	}

	public int getDrawy() {
		return ((int) (y * GlobalGraphics.sqsize + (GlobalGraphics.sqsize - pixsizey) * 0.5));
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

	public Bomb(Level level, int x, int y, Player owner, int timer, int range) {
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
		if (thread != null) {
			owner.setBombs(owner.getBombs() - 1); // Bombenanzahl des Besitzers
			// anpassen
			thread = null;
		}

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
		if (thread != null) {
			// try{
			// File f = new File( "src/sounds/Explosion.wav" );
			// final URL url=f.toURI().toURL();
			// final AudioClip Exp_sound = Applet.newAudioClip( url );
			// Exp_sound.play();
			//
			// } catch(Exception e1){
			// e1.printStackTrace();
			// }
			stop(); // Stoppe den Thread und passe Bombenzahl des Besitzers an
			System.out.println("Boom!");

			// Nicht mehr zeichnen
			synchronized (Bomblist.list) {
				Bomblist.list.remove(this);
			}
			// Zerstörung von Feldern und Bomben in Reichweite und Erzeugung von
			// Flammen

			// Feld auf dem diese Bombe liegt
			level.getField(x, y).setBomb(null); // Diese Bombe soll nur einmal
												// explodieren
			createFlame(x, y, 0);

			// x Richtung
			for (int posx = x + 1; posx <= (x + range); posx++) {
				// Erzeuge Flammen
				if (!createFlame(posx, y, 1))
					break;
			}
			// -x Richtung
			for (int posx = x - 1; posx >= (x - range); posx--) {
				// Erzeuge Flammen
				if (!createFlame(posx, y, 3))
					break;
			}
			// y Richtung
			for (int posy = y + 1; posy <= (y + range); posy++) {
				// Erzeuge Flammen
				if (!createFlame(x, posy, 2))
					break;
			}
			// -y Richtung
			for (int posy = y - 1; posy >= (y - range); posy--) {
				// Erzeuge Flammen
				if (!createFlame(x, posy, 4))
					break;
			}

			owner = null; // Besitzer dereferenzieren
			level = null; // Level dereferenzieren

			//new Sound("src/sounds/Explosion.wav", 1000).start();
			
			GlobalSounds.sound = 1;
		}
	}

	private boolean createFlame(int x, int y, int dir) {
		Field field = level.getField(x, y);
		if (field != null) {
			boolean isBombed = (field.getBomb() != null); // Liegt Bombe auf
															// Feld?
			boolean isSolid = field.isSolid(); // Ist Feld fest?
			field.destruction();
			// Falls das Feld zerstörbar ist, wird es in ein anderes umgewandelt
			boolean transformed = field.transform(level, x, y);
			if (isBombed)
				return (false); // Feld hat Bombe beinhaltet, erzeuge keine
								// Flamme
			if (transformed) {
				// Feld wurde transformiert, lade neues Feld
				field = level.getField(x, y);
			}

			// Falls das neue Feld solid ist wird keine
			// Flamme
			// erzeugt

			if (field.isSolid())
				return (false);
			// Alle Hürden genommen um Flamme zu erzeugen
			Flame flame = new Flame(field, x, y, dir);
			flame.start();
			// Falls altes Feld fest war, werden keine weiteren Flammen erzeugt
			if (isSolid)
				return (false);
			return (true);
		} else {
			return (false);
		}
	}

}
