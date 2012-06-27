// Dieses Programm arbeitet als Server und sendet
// die Benutzereingaben an die Clients
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class UniversalServer {
	static ServerSocket server = null;

	public static void main(String[] args) throws IOException {
		// if (args.length != 1) {
		// if (true == false){
		// System.err.println("Verwendung: java UniversalServer " + "<port>");
		// System.exit(1);
		// }
		server = new ServerSocket(5000);
		// System.out.println("Warte auf Verbindung auf Port " + args[0]);
		Socket sock = server.accept();
		System.out.println("Verbindung hergestellt, "
				+ "geben Sie ihre Daten ein");
		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();

		// BufferedReader keyboard = new BufferedReader(
		// new InputStreamReader(System.in));
		// new KeyListener());
		InputStreamReader keyboard = new InputStreamReader(System.in);
		// CharArrayReader keyboard = new CharArrayReader(Kram.buf);
		// (new ReceiveThrea())
		(new ReceiveThread(in, "Client: ")).start();
		while (true) {
			// eine Zeile einlesen
			int a = keyboard.read();
			String str = Integer.toString(a);
			if (str.equalsIgnoreCase("quit")) {
				sock.close();
				System.exit(1);
			}

			if (Kram.up == true) {
				Kram.c_up++;
				Kram.up = false;
			}
			if (Kram.left == true) {
				Kram.c_left++;
				Kram.left = false;
			}
			if (Kram.down == true) {
				Kram.c_down++;
				Kram.down = false;
			}
			if (Kram.right == true) {
				Kram.c_right++;
				Kram.right = false;
			}
			out.write(Kram.c_up);
			out.write(Kram.c_left);
			out.write(Kram.c_down);
			out.write(Kram.c_right);
		}

		// die Zeile an den Client senden

	}
}
