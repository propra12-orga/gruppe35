package Network;

// Dieses Programm sendet Benutzereingaben an
// einen Server und zeigt die Antworten an
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import main.DrawArray;
import main.GUI;
import main.GlobalGraphics;
import main.GlobalSounds;

/**
 * 
 * 
 * The Client class is used for playing the game in the Network. If playstile is
 * singleplayer, it connects to the local host, otherwise it will connect to a
 * specified remote host.
 * 
 * The Client receives information about sounds to play, graphics to paint and
 * sends information about controls given via the keyboard It inhabits a GUI to
 * show on the screen the graphics it has received the ObjectInputStream ois is
 * used for receiving serialized objects (graphics and sound) the
 * ObjectOutputStream oos is used for sending serialized objects (the controls)
 * 
 * The communication with the Servers takes place in the main() method
 * <P>
 * 
 * @author Peet, Friedrich
 * 
 */

public class Client extends JFrame {

	final public static GUI gui = new GUI();
	private static ObjectInputStream ois = null;
	private static ObjectOutputStream oos = null;
	private static Socket socket;

	static void error(String message) {
		System.err.println(message);
		System.exit(1);
	}

	public static OutputStream out;

	public static int dataset_up[] = { 0 };
	public static Boolean tasten[] = { false, false, false, false, false };

	public Client(String IPstring, int port) {
		try {
			Client.socket = new Socket(IPstring, port);
			Client.oos = new ObjectOutputStream(socket.getOutputStream());
			Client.ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Verbindung hergestellt (ICH BIN CLIENT)");
	}

	protected void finalize() {
		close();
	}

	private void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {

		SerializedBool bewegungen = new SerializedBool();

		String IPstring = args[0];
		System.out.print(IPstring);
		if (IPstring.equals("localhost"))
			System.out.println("localhost");
		int port = Integer.parseInt(args[1]);
		System.out.println(port);
		// so1.setArray(dataset_up);

		// Eigentlicher Client
		final Client client = new Client(IPstring, port);
		// Warte auf Initialisierungspaket
		DrawArray initializePackage = (DrawArray) ois.readObject();
		synchronized (GlobalGraphics.drawarray) {
			GlobalGraphics.drawarray = initializePackage;
		}

		// GUI initialisieren
		if (gui.runnable == null) {
			gui.runnable = new Runnable() {
				public void run() {
					gui.initialize();
				}
			};
		}
		javax.swing.SwingUtilities.invokeLater(gui.runnable);

		while (!gui.isInitialized()) {
			Thread.sleep(100);
		}

		// if (args.length != 2)
		// error("Verwendung: java UniversalClient " + "<server> <port>");

		while (!gui.isClosed()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// Sende, dass diese Verbindung noch besteht
			oos.reset();
			oos.writeBoolean(true);
			oos.flush();

			// Sound empfangen
			int soundID = ois.readInt();
			GlobalSounds.playSound(soundID);

			// Grafikpaket empfangen
			DrawArray drawArrayPackage = (DrawArray) ois.readObject();
			synchronized (GlobalGraphics.drawarray) {
				GlobalGraphics.drawarray = drawArrayPackage;
			}

			// Bewegungspaket schnüren
			bewegungen.setArray(tasten);
			// Bewegungspaket abschicken
			oos.reset();
			oos.writeObject(bewegungen);
			oos.flush();

			// Bewegungen aufzeichnen
			// for (int i = 0; i < tasten.length; i++)
			// tasten[i] = false;

			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Sende, dass diese Verbindung geschlossen wurde
		System.out.println("Client hat Verbindung geschlossen");
		oos.reset();
		oos.writeBoolean(false);
		oos.flush();
		// Schliesse Verbindungen
		client.close();
		System.exit(0);
	}

}

class ReceiveThread extends Thread {
	BufferedReader in;
	String message;

	static void error(String message) {
		System.err.println(message);
		System.exit(1);
	}

}