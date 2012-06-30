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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;

class UniversalClient extends JFrame implements KeyListener {
	static void error(String message) {
		System.err.println(message);
		System.exit(1);
	}

	public static OutputStream out;
	public JPanel panel;

	public void initialize() {
		Container cp = this.getContentPane();
		panel = new JPanel() {
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

	public static void main(String[] args) throws IOException {
		// if (args.length != 2)
		// error("Verwendung: java UniversalClient " + "<server> <port>");

		Socket sock = new Socket("localhost", 5000);

		System.out.println("Verbindung hergestellt, "
				+ "geben Sie ihre Daten ein");
		InputStream in = sock.getInputStream();
		out = sock.getOutputStream();
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(
				System.in));

		// Empfangsthread starten
		(new ReceiveThread(in, "Server: ")).start();

		final UniversalClient peter = new UniversalClient();
		while (true) {
			// eine Zeile einlesen
			String str = keyboard.readLine();
			if (str.equalsIgnoreCase("quit")) {
				sock.close();
				System.exit(1);
			}
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					peter.initialize();
				}
			});
			// die Zeile an den Server senden

			if (Kram.c_up == 1) {
				out.write(("pressed" + '\n').getBytes());
				System.out.println("Pfeiltaste hoch geschickt!");
				Kram.c_up = 0;
			}

			else
				out.write(("unpressed" + '\n').getBytes());

			// out.write(Kram.c_up);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (40 == e.getKeyCode()) {

			Kram.up = true;
			Kram.c_up = 1;
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

	public ReceiveThread(InputStream in, String message) {
		super();
		this.in = new BufferedReader(new InputStreamReader(in));
		this.message = message;
	}
	
	public String give() throws IOException{
		return in.readLine();
	}
	
	public void run() {
		try {
			while (true) {
				// eine Zeile empfangen
				String str = in.readLine();
				// die Zeile ausgeben
				if (str != null)
					//System.out.println(message + str);
					System.out.println("will ich nich sehen!" + '\n');
			}
		} catch (SocketException e) {
			error("Verbindung wurde getrennt");
		} catch (IOException e) {
			error(e.toString());
		}
	}

}