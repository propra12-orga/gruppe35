package main;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * 
 * Reserved for possible misc. global variables
 * <P>
 * 
 * @author Peet
 */

public class Global {
	public static int sqsize = 200;
	// Bilder laden
	public static final Image image1 = Toolkit.getDefaultToolkit().getImage(
			"src/img/boltzmann2.gif");
	public static final Image image2 = Toolkit.getDefaultToolkit().getImage(
			"src/img/feynman.gif");
	public static final Image boden = Toolkit.getDefaultToolkit().getImage(
			"src/img/boden.jpg");
	public static final Image mauer = Toolkit.getDefaultToolkit().getImage(
			"src/img/mauer.jpg");
	public static final Image mauersolid = Toolkit.getDefaultToolkit()
			.getImage("src/img/mauersolid.jpg");
	public static final Image bomb = Toolkit.getDefaultToolkit().getImage(
			"src/img/bomb.gif");
	public static final Image flame = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_central.gif");
	public static final Image flame_h = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_horizontal.gif");
	public static final Image flame_v = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_vertical.gif");
}
