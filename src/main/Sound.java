package main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class Sound {

	public Sound(String URL) {
		File urlFile = new File(URL);

		try {
			if (!urlFile.exists()) {
				throw new FileNotFoundException(urlFile.toString());
			}
			URL url = urlFile.toURI().toURL();
			AudioClip sound = Applet.newAudioClip(url);

			sound.play();
			Thread.sleep(1000);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
