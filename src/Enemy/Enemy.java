package Enemy;

import main.Global;

public class Enemy {
	protected String name;
	protected double speed;
	protected int pixsizex = (int) (Global.sqsize * 0.5); // 25
	protected int pixsizey = (int) (Global.sqsize * 0.8); // 40
	protected double posx;
	protected double posy;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getPosx() {
		return posx;
	}
	public void setPosx(double posx) {
		this.posx = posx;
	}
	public double getPosy() {
		return posy;
	}
	public void setPosy(double posy) {
		this.posy = posy;
	}
}
