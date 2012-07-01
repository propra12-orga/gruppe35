import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ArrayMultiplier extends Thread {

	private ServerSocket arrayServer;

//	public static void main(String argv[]) throws Exception {
//		new ArrayMultiplier();
//	}

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
				System.out.println("never reached");
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
		System.out.println("huerde0");
		client = clientSocket;
		try {

			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
			System.out.println("ficker kaputt");
		} catch (Exception e1) {
			try {
				client.close();
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			return;
		}
		
	
			
		
		System.out.println("huerde1");
		this.start();
		System.out.println("huerde2");
	}

	public void run() {
		SerializedObject x = null;
		// SerializedObject y = null;
		int dataset1[] = new int[1];
		// int dataset2[] = new int[7];
		int result[] = new int[4];
		try {
			x = (SerializedObject) ois.readObject();
			// y = (SerializedObject) ois.readObject();
			dataset1 = x.getArray();
			// dataset2 = y.getArray();
			// create an array by multiplying two arrays
			// for (int i = 0; i < dataset1.length; i++) {
			// result[i] = dataset1[i] * dataset2[i];
			// }
			result[0] += dataset1[0];
			System.out.println("Et kütt raus: " + result[0]);
			// ship the object to the client
			SerializedObject output = new SerializedObject();
			output.setArray(result);
			oos.writeObject(output);
			oos.flush();
			// close connections
			ois.close();
			oos.close();
			client.close();
		} catch (Exception e) {
		}
	}
}