package main;

/**
 * 
 * Reserved for Sound related stuff. The int sound specifies the ID of the next
 * sound to be played. This is required to send this ID from Server to Client,
 * so that the Client can play the Sound.
 * 
 * Which sound is played for a specific ID is directly implemented in the
 * playSound method. If the ID does not correspond to an implemented sound,
 * simply no sound is played.
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
