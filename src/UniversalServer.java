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
		//String str;
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(
				System.in));
		// CharArrayReader keyboard = new CharArrayReader(Kram.buf);
		// (new ReceiveThrea())
		(new ReceiveThread(in, "Client: ")).start();
		String str3 = (new ReceiveThread(in, "Client: ")).give();
		System.out.println(str3 + '\n');
		while (true) {
			// eine Zeile einlesen
			String str2 = keyboard.readLine();
			
			if (str2.equalsIgnoreCase("quit")) {
				sock.close();
				System.exit(1);
			}
			//System.out.print(garbage);
			if (Kram.c_up == 1){
				System.out.println("Pfeiltaste hoch angekommen!");
				Kram.c_up = 0;
			}
			// die Zeile an den Client senden
			out.write((str2 + '\n').getBytes());
		}

		
		
	}
}
