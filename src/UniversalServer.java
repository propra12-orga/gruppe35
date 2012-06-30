// Dieses Programm arbeitet als Server und sendet
// die Benutzereingaben an die Clients
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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
		// String str;
		// BufferedReader keyboard = new BufferedReader(new InputStreamReader(
		// System.in));
		// CharArrayReader keyboard = new CharArrayReader(Kram.buf);
		// (new ReceiveThrea())
		ReceiveThread a = (new ReceiveThread(in, "Client: "));
		a.start();
		String str3 = a.give();
		System.out.println(str3 + '\n');
		while (true) {
			System.out.println(str3 + '\n');
			// eine Zeile einlesen
			// String str2 = keyboard.readLine();

			// if (str2.equalsIgnoreCase("quit")) {
			// sock.close();
			// System.exit(1);
			// }
			// System.out.print(garbage);
			if (str3 == "pressed") {
				System.out.println("Pfeiltaste hoch angekommen!");
				//Kram.c_up = 0;
			}
			// die Zeile an den Client senden
			out.write((str3 + '\n').getBytes());
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
