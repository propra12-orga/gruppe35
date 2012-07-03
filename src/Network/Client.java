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

	public Client() {
		try {
			Client.socket = new Socket("localhost", 4000);
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

		SerializedObject so1 = new SerializedObject();

		SerializedBool bewegungen = new SerializedBool();

<<<<<<< HEAD
		
	
=======
		// so1.setArray(dataset_up);
>>>>>>> 905ea0c1392e0eef8c6ab76401fc5a941e3c50a9

		// Eigentlicher Client
		final Client client = new Client();
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
			// Sende, dass diese Verbindung noch besteht
			oos.reset();
			oos.writeBoolean(true);
			oos.flush();

			//// Sound empfangen
			//int sound = ois.readInt();
			//GlobalSounds.playSound(sound);

			// Grafikpaket empfangen
			DrawArray drawArrayPackage = (DrawArray) ois.readObject();
			synchronized (GlobalGraphics.drawarray) {
				GlobalGraphics.drawarray = drawArrayPackage;
			}
			// // TEST OB GRAFIKPAKET GUT ANKOMMT
			// for (int i = 0; i < GlobalGraphics.drawarray.array.size(); i++) {
			// int[] drawItem = GlobalGraphics.drawarray.array.get(i);
			// System.out.println("DrawItem " + i + ": " + drawItem[0] + ","
			// + drawItem[1] + "," + drawItem[2] + "," + drawItem[3]
			// + "," + drawItem[4]);
			// }
			// for (int i = 0; i < GlobalGraphics.drawarray.playernames.length;
			// i++) {
			// System.out.println("Playername: "
			// + GlobalGraphics.drawarray.playernames[i] + " Leben = "
			// + GlobalGraphics.drawarray.playerlifes[i]);
			// }

			// Bewegungspaket schnüren
			so1.setArray(dataset_up);
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