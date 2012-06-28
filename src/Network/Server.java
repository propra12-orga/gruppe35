package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
	static ServerSocket server = null;
	protected static int port = 5000;

	public static void main(String[] args) throws IOException {
		server = new ServerSocket(port);
		Socket sock = server.accept();

		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(
				System.in));
	}

	public Server(int port) {
		this.server = null;
		// this.clntsock = null;
		// this.in = null;

		this.port = port;
	}
}