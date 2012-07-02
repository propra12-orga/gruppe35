package main;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * 
 * Reserved for possible misc. global variables
 * <P>
 * 
 * @author Peet
 */

public class GlobalGraphics {
	public static int sqsize = 50;
	public static ArrayList<Image> imageList = new ArrayList<Image>();
	public static DrawArray drawarray = new DrawArray();

	// Bilder laden
	public static final Image image1 = Toolkit.getDefaultToolkit().getImage(
			"src/img/boltzmann2.gif");
	public static final Image image2 = Toolkit.getDefaultToolkit().getImage(
			"src/img/feynman.gif");
	public static final Image image1standing = Toolkit.getDefaultToolkit()
			.getImage("src/img/boltzmann2.gif");
	public static final Image image2standing = Toolkit.getDefaultToolkit()
			.getImage("src/img/feynman.gif");
	public static final Image image1dead = Toolkit.getDefaultToolkit()
			.getImage("src/img/boltzmann2_dead.gif");
	public static final Image image2dead = Toolkit.getDefaultToolkit()
			.getImage("src/img/feynman_dead.gif");
	public static final Image boden = Toolkit.getDefaultToolkit().getImage(
			"src/img/boden.jpg");
	public static final Image mauer = Toolkit.getDefaultToolkit().getImage(
			"src/img/mauer.jpg");
	public static final Image mauersolid = Toolkit.getDefaultToolkit()
			.getImage("src/img/mauersolid.jpg");
	public static final Image exit = Toolkit.getDefaultToolkit().getImage(
			"src/img/exit.gif");
	public static final Image bomb = Toolkit.getDefaultToolkit().getImage(
			"src/img/bomb.gif");
	public static final Image flame = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_central.gif");
	public static final Image flame_h = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_horizontal.gif");
	public static final Image flame_v = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_vertical.gif");
	public static final Image intro = Toolkit.getDefaultToolkit().getImage(
			"src/img/intro.gif");
	public static final Image intro2 = Toolkit.getDefaultToolkit().getImage(
			"src/img/intro2.gif");
	public static final Image seeker = Toolkit.getDefaultToolkit().getImage(
			"src/img/heart.gif");
	public static final Image seekerstanding = Toolkit.getDefaultToolkit()
			.getImage("src/img/heart.gif");
	public static final Image seekerdead = Toolkit.getDefaultToolkit()
			.getImage("src/img/heart_dead.gif");
	public static final Image Tup = Toolkit.getDefaultToolkit().getImage(
			"src/img/Powerup_Tup.gif");
	public static final Image Rup = Toolkit.getDefaultToolkit().getImage(
			"src/img/Powerup_Rup.gif");
	public static final Image Bup = Toolkit.getDefaultToolkit().getImage(
			"src/img/Powerup_Bup.gif");
	
	static{
		imageList.add(image1); //0
		imageList.add(image2); //1
		imageList.add(flame); //2
		imageList.add(image1standing); //3
		imageList.add(image2standing); //4
		imageList.add(image1dead); //5
		imageList.add(image2dead); //6
		imageList.add(boden); //7
		imageList.add(mauer); //8
		imageList.add(mauersolid); //9
		imageList.add(exit); //10
		imageList.add(bomb); //11
		imageList.add(flame_h); //12
		imageList.add(flame_v); //13
		imageList.add(intro); //14
		imageList.add(intro2); //15
		imageList.add(seeker); //16
		imageList.add(seekerstanding); //17
		imageList.add(seekerdead); //18
		imageList.add(Tup); //19
		imageList.add(Rup); //20
		imageList.add(Bup); //21
	}

}
