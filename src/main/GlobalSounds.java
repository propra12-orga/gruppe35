package main;


/**
 * 
 * Reserved for Sound related stuff drawarray contains information about
 * everything that has to be painted Also all the image paths are contained in
 * this class as well as their IDs
 * <P>
 * 
 * @author Peet
 */

public class GlobalSounds {
	public static int sound;
	
	public static void playSound(int i) {
		switch (i) {
		case 1:
			new Sound("src/sounds/Explosion.wav", 1000).start();
			break;
		default:
			break;
		}
	}

}
