import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ArrayMultiplier extends Thread {

	private ServerSocket arrayServer;

	// public static void main(String argv[]) throws Exception {
	// new ArrayMultiplier();
	// }

	public ArrayMultiplier() throws Exception {
		arrayServer = new ServerSocket(4000);
		System.out.println("Server listening on port 4000.");
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for connections.");
				Socket client = arrayServer.accept();
				System.out.println("Accepted a connection from: "
						+ client.getInetAddress());

				Connect c = new Connect(client);
				
			} catch (Exception e) {
			}
		}
	}
}

class Connect extends Thread {
	private Socket client = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public Connect() {
	}

	public Connect(Socket clientSocket) {

		client = clientSocket;
		try {

			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());

		} catch (Exception e1) {
			// printStackTrace();
			try {
				client.close();
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			return;
		}

		this.start();

	}

	public void run() {
		SerializedBool x = null;

		Boolean tasten[] = new Boolean[4];
	
		int result[] = { 0, 0, 0 };
		while (true) {
			try {
				
				x = (SerializedBool) ois.readObject();
				tasten = x.getArray();
				
				
				if (tasten[0] == true)
					result[0]++;				// oben
				if (tasten[2] == true)
					result[0]--;				// unten
				if (tasten[1] == true)
					result[1]--;				// links
				if (tasten[3] == true)
					result[1]++;				// rechts
				
				if (tasten[4] == true)
					result[2]++;				// bombe
				
				
				// ship the object to the client
				SerializedObject output = new SerializedObject();
				output.setArray(result);
				
				oos.reset();
				oos.writeObject(output);
				oos.flush();
				
				// close connections
				// ois.close();
				// oos.close();
				// client.close();
			} catch (Exception e) {
			}
		}
		
	}
}