package Character;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import main.Enemylist;
import Level.Levellist;

/**
 * 
 * The enemy class is the parent class for all AI controlled Characters
 * 
 * When starting a level the Enemies can be spawned with the spawn() method at
 * the spawnx,spawny position
 * 
 * If an enemy dies it is simply removed from the game
 * 
 * A thread calls the enemies AI every 100 milliseconds. The AI is controlled by
 * the AIaction() function. It defines what the Enemy actually does
 * 
 * <P>
 * 
 * @author Peet
 */

public class Enemy extends Character implements Runnable {

	private double spawnx;
	private double spawny;
	private Thread thread = null;
	
	class Decay extends TimerTask  
	{
	    public void run()  
	  {
	    synchronized (Enemylist.list) {
		 Enemylist.list.remove(Enemy.this);
		}
	  }
	}

	public Enemy(double spawnx, double spawny, double speed,
			int imageID, int imageIDstanding,int imageIDdead) {
		super(speed, imageID, imageIDstanding, imageIDdead);
		this.spawnx = spawnx;
		this.spawny = spawny;
	}

	public synchronized void stop() {
		if (thread != null)
			thread = null;
	}

	public synchronized void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
			synchronized (Enemylist.list) {
				Enemylist.list.add(this);
			}
		}
	}

	public void run() {
		while (thread != null) {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
			}
			AIaction();
		}
	}

	protected void AIaction() {
	}

	public void kill() {
		// Stoppe thread
		stop();
		// Verlasse Feld
		Levellist.activeLevel.getField((int) (posx), (int) (posy)).leave(this);
		// Tot
		dead = true;
		// Nach 2 Sekunden verwesen
		Timer timer = new Timer();
		timer.schedule(new Decay(), 2000);
	}

	public void spawn() {
		spawn(spawnx, spawny);
		start();
	}

}
