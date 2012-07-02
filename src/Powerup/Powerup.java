package Powerup;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

import main.GlobalGraphics;
import main.Poweruplist;
import Character.Player;

public class Powerup {
	protected int imageID;
	protected int x, y;
	protected int pixsizex, pixsizey;
	private static Random rand = new Random();

	public Powerup(int x, int y) {
		pixsizex = GlobalGraphics.sqsize / 2;
		pixsizey = GlobalGraphics.sqsize / 2;
		this.x = x;
		this.y = y;
		this.imageID = -1;
		synchronized (Poweruplist.list) {
			Poweruplist.list.add(this);
		}
	}
	
	public int getImageID(){
		return(imageID);
	}
	
	public int getPixsizex(){
		return(pixsizex);
	}
	
	public int getPixsizey(){
		return(pixsizey);
	}

	public int getDrawx() {
		return ((int) ((x + 0.5) * GlobalGraphics.sqsize - pixsizex * 0.5));
	}

	public int getDrawy() {
		return ((int) ((y + 0.5) * GlobalGraphics.sqsize - pixsizey * 0.5));
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
				return (new Rup(x, y));
			case 1:
				return (new Tup(x, y));
			case 2:
				return (new Bup(x, y));

			default:
			}
		}
		return (null);
	}
}
