// Dieses Programm sendet Benutzereingaben an
// einen Server und zeigt die Antworten an
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

class UniversalClient extends JFrame implements KeyListener {
	static void error(String message) {
		System.err.println(message);
		System.exit(1);
	}

	public static OutputStream out;
	public JPanel panel;
	public static int dataset_up[] = { 0 };

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

		
		new ArrayMultiplier();
		

		try {

			Socket sock = new Socket("localhost", 4000);
			ois = new ObjectInputStream(sock.getInputStream());
			System.out.println("Ich lauf im Kreis");
			oos = new ObjectOutputStream(sock.getOutputStream());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		System.out.println("Verbindung hergestellt (ICH BIN CLIENT)");

		SerializedObject so1 = new SerializedObject();
		SerializedObject result = null;
		// int keypressed;

		int positionen[] = new int[4];
		so1.setArray(dataset_up);

		final UniversalClient peter = new UniversalClient();
		while (true) {

			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					peter.initialize();
				}
			});

			oos.writeObject(so1);
			oos.flush();

			try {
				result = (SerializedObject) ois.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			positionen = result.getArray();

			for (int i = 0; i < positionen.length; i++)
				System.out.print(positionen[i] + '\n');

			try {
				TimeUnit.MILLISECONDS.sleep(500);
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
		if (40 == e.getKeyCode()) {
			dataset_up[0]++;
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