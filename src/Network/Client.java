package Network;

// Dieses Programm sendet Benutzereingaben an
// einen Server und zeigt die Antworten an
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.DrawArray;
import main.GlobalGraphics;

class Client extends JFrame implements KeyListener {
	static void error(String message) {
		System.err.println(message);
		System.exit(1);
	}

	public static OutputStream out;
	public JPanel panel;
	public static int dataset_up[] = { 0 };
	public static Boolean tasten[] = { false, false, false, false, false };

	public void initialize() {
		Container cp = this.getContentPane();
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

			}
		};
		this.addKeyListener(this);

		GridBagConstraints panelc = new GridBagConstraints();
		panelc.gridx = 0;
		panelc.gridy = 0;
		panelc.gridwidth = 2;
		panelc.fill = GridBagConstraints.BOTH;
		panelc.weightx = 1.0;
		panelc.weighty = 1.0;
		cp.setLayout(new GridBagLayout());
		cp.add(panel, panelc);
		this.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		// if (args.length != 2)
		// error("Verwendung: java UniversalClient " + "<server> <port>");

		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		Socket sock = new Socket("localhost", 4000);
		oos = new ObjectOutputStream(sock.getOutputStream());
		ois = new ObjectInputStream(sock.getInputStream());
		System.out.println("Ich lauf im Kreis");

		System.out.println("Verbindung hergestellt (ICH BIN CLIENT)");

		SerializedObject so1 = new SerializedObject();

		SerializedBool bewegungen = new SerializedBool();

		int positionen[] = new int[3];
		// so1.setArray(dataset_up);

		final Client client = new Client();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				client.initialize();
			}
		});

		while (true) {
			// Grafikpaket empfangen
			DrawArray drawArrayPackage = (DrawArray) ois.readObject();
			synchronized (GlobalGraphics.drawarray) {
				GlobalGraphics.drawarray = drawArrayPackage;
			}
			// TEST OB GRAFIKPAKET GUT ANKOMMT
			for (int i = 0; i < GlobalGraphics.drawarray.array.size(); i++) {
				int[] drawItem = GlobalGraphics.drawarray.array.get(i);
				System.out.println("DrawItem " + i + ": " + drawItem[0] + ","
						+ drawItem[1] + "," + drawItem[2] + "," + drawItem[3]
						+ "," + drawItem[4]);
			}
			for (int i = 0; i < GlobalGraphics.drawarray.playernames.length; i++) {
				System.out.println("Playername: "
						+ GlobalGraphics.drawarray.playernames[i] + " Leben = "
						+ GlobalGraphics.drawarray.playerlifes[i]);
			}

			// Bewegungspaket schnüren
			so1.setArray(dataset_up);
			bewegungen.setArray(tasten);
			// Bewegungspaket abschicken
			oos.reset();
			oos.writeObject(bewegungen);
			oos.flush();

			// Bewegungen aufzeichnen
			for (int i = 0; i < tasten.length; i++)
				tasten[i] = false;

			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// out.close();
		// in.close();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (38 == e.getKeyCode()) {
			tasten[0] = true;
		}
		if (37 == e.getKeyCode()) {
			tasten[1] = true;
		}
		if (40 == e.getKeyCode()) {
			tasten[2] = true;
		}
		if (39 == e.getKeyCode()) {
			tasten[3] = true;
		}
		if (10 == e.getKeyCode()) {
			tasten[4] = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

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