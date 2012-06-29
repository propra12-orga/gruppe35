package Powerup;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

import main.Global;
import main.Poweruplist;
import Character.Character;
import Character.Player;

public class Powerup {
	protected Image image;
	protected int x, y;
	protected int pixsizex, pixsizey;
	private static Random rand = new Random();

	public Powerup(int x, int y){
		pixsizex = Global.sqsize/2;
		pixsizey = Global.sqsize/2;
		this.x = x;
		this.y = y;
		synchronized (Poweruplist.list) {
			Poweruplist.list.add(this);
		}
	}
	
	public void DrawComponent(Graphics g, JPanel panel) {

		g.drawImage(image, getDrawx(), getDrawy(), pixsizex, pixsizey, panel);

	}

	public int getDrawx() {
		return ((int) ((x + 0.5) * Global.sqsize - pixsizex * 0.5));
	}

	public int getDrawy() {
		return ((int) ((y + 0.5) * Global.sqsize - pixsizey * 0.5));
	}

	public void pickup(Player player) {
		// Nicht mehr zeichnen
		synchronized (Poweruplist.list) {
			Poweruplist.list.remove(this);
		}

	}

	public static Powerup random(int x, int y, double chance) {
		if (rand.nextDouble() < chance) {

			int i = rand.nextInt(3);
			switch (i) {
			case 0:
				return (new Rup(x,y));
			case 1:
				return (new Tup(x,y));
			case 2:
				return (new Bup(x,y));

			default:
			}
		}
		return (null);
	}
}
