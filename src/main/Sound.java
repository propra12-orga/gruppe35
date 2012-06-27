package main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Sound implements Runnable {

	protected Thread thread = null;
	protected AudioClip sound;
	protected int duration;

	public Sound(String URL, int duration) {
		this.duration = duration;
		File urlFile = new File(URL);

		try {
			if (!urlFile.exists()) {
				throw new FileNotFoundException(urlFile.toString());
			}
			URL url = urlFile.toURI().toURL();
			sound = Applet.newAudioClip(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		
	}

	public synchronized void start() {
		if (thread == null) {
			if (duration > 0) {
				sound.play();
			} else {
				sound.loop();
			}
			thread = new Thread(this);
			thread.start();
		}
	}

	public synchronized void stop() {
		if (thread != null) {
			sound.stop();
			thread = null;
		}

	}

	public void run() {
		while (thread != null) {
			if (duration > 0) {
				// Keine Endlosschleife

				sound.play();
				try {
					TimeUnit.MILLISECONDS.sleep(duration);
				} catch (InterruptedException e) {
				}
				stop();
			} else {
				
				// Endlosschleife
			}
		}

	}

}
