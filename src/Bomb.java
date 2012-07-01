import java.util.concurrent.TimeUnit;

public class Bomb implements Runnable {
	protected Character owner;
	protected int timer;
	protected int range;
	protected int x;
	protected int y;

	public Bomb(int x, int y, Character owner, int timer, int range) {
		this.x = x;
		this.y = y;
		this.owner = owner;
		this.timer = timer;
		this.range = range;
	}

	public void start() {
		Thread bombthread;
		bombthread = new Thread(this);
		bombthread.start();
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(timer);
		} catch (InterruptedException e) {
		}
		Explode();
	}

	public void Explode() {
		System.out.println("Boom!");
		owner.Setbombs(owner.Getbombs() - 1);
	}

}
