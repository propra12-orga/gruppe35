package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.Bomblist;
import main.Enemylist;
import main.Flamelist;
import main.GlobalGraphics;
import main.Playerlist;
import main.Poweruplist;
import Level.Levellist;

public class Server extends Thread {

	public static void main(String[] args) throws Exception {
		final Server server = new Server();
		// Lade erstes Level
		Levellist.load(0);
	}

	private ServerSocket serverSocket;

	// public static void main(String argv[]) throws Exception {
	// new ArrayMultiplier();
	// }

	public Server() throws Exception {
		serverSocket = new ServerSocket(4000);
		System.out.println("Server listening on port 4000.");
		this.start();
	}

	// Destruktor muss Verbindungen beenden
	protected void finalize() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for connections.");
				Socket client = serverSocket.accept();
				System.out.println("Accepted a connection from: "
						+ client.getInetAddress());

				Connect connect = new Connect(client);
				connect.initialize();

			} catch (Exception e) {
			}
		}
	}
}

class Connect extends Thread {
	private Socket clientSocket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public Connect() {
	}

	// Destruktor muss Verbindungen beenden
	protected void finalize() {
		if (clientSocket != null) {
			try {
				clientSocket.close();
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

	public Connect(Socket clientSocket) {

		this.clientSocket = clientSocket;
		try {

			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());

		} catch (Exception e1) {
			// printStackTrace();
			try {
				clientSocket.close();
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			return;
		}

	}

	public void initialize() {
		// Schnüre Initialisierungspaket
			createGraphicsPackage();
		try {
			oos.reset();
			oos.writeObject(GlobalGraphics.drawarray);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
	private void createGraphicsPackage(){
		// Grafikpaket schnüren
		// Altes paket löschen
		GlobalGraphics.drawarray.array.clear();
		//Levelgröße hinzufügen
		int[] levelSize = new int[]{Levellist.activeLevel.getXsize(),Levellist.activeLevel.getYsize()};
		GlobalGraphics.drawarray.levelSize = levelSize;
		// Felder hinzufügen
		if (Levellist.activeLevel != null) {
			for (int y = 0; y < Levellist.activeLevel.getYsize(); y++) {
				for (int x = 0; x < Levellist.activeLevel.getXsize(); x++) {
					GlobalGraphics.drawarray.add(x
							* GlobalGraphics.sqsize, y
							* GlobalGraphics.sqsize,
							GlobalGraphics.sqsize,
							GlobalGraphics.sqsize,
							Levellist.activeLevel.getField(x, y)
									.getImageID());

				}
			}
		}

		// Bomben hinzufügen
		for (int i = 0; i < Bomblist.list.size(); i++) {

			GlobalGraphics.drawarray.add(Bomblist.list.get(i)
					.getDrawx(), Bomblist.list.get(i).getDrawy(),
					Bomblist.list.get(i).getPixsizex(), Bomblist.list
							.get(i).getPixsizey(), Bomblist.list.get(i)
							.getImageID());
		}

		// Flammen hinzufügen
		for (int i = 0; i < Flamelist.list.size(); i++) {
			GlobalGraphics.drawarray.add(Flamelist.list.get(i)
					.getDrawx(), Flamelist.list.get(i).getDrawy(),
					Flamelist.list.get(i).getPixsizex(), Flamelist.list
							.get(i).getPixsizey(), Flamelist.list
							.get(i).getImageID());

		}

		// Player hinzufügen
		for (int i = 0; i < Playerlist.list.size(); i++) {
			GlobalGraphics.drawarray.add(Playerlist.list.get(i)
					.getDrawx(), Playerlist.list.get(i).getDrawy(),
					Playerlist.list.get(i).getPixsizex(),
					Playerlist.list.get(i).getPixsizey(),
					Playerlist.list.get(i).getImageID());
			// Playerlist.list.get(i).DrawComponent(g, panel);
		}

		// Gegner hinzufügen
		for (int i = 0; i < Enemylist.list.size(); i++) {
			GlobalGraphics.drawarray.add(Enemylist.list.get(i)
					.getDrawx(), Enemylist.list.get(i).getDrawy(),
					Enemylist.list.get(i).getPixsizex(), Enemylist.list
							.get(i).getPixsizey(), Enemylist.list
							.get(i).getImageID());
		}

		// Powerups hinzufügen
		for (int i = 0; i < Poweruplist.list.size(); i++) {
			GlobalGraphics.drawarray.add(Poweruplist.list.get(i)
					.getDrawx(), Poweruplist.list.get(i).getDrawy(),
					Poweruplist.list.get(i).getPixsizex(),
					Poweruplist.list.get(i).getPixsizey(),
					Poweruplist.list.get(i).getImageID());
		}
		// Statbox hinzufügen
		GlobalGraphics.drawarray.statsRect = new int[] {
				GlobalGraphics.sqsize
						* Levellist.activeLevel.getXsize(), 0, 100, 100 };
		// Namen und Leben der Spieler hinzufügen
		GlobalGraphics.drawarray.playerlifes = new int[Playerlist.list
				.size()];
		GlobalGraphics.drawarray.playernames = new String[Playerlist.list
				.size()];
		for (int i = 0; i < Playerlist.list.size(); i++) {
			GlobalGraphics.drawarray.playerlifes[i] = Playerlist.list
					.get(i).getLifes();
			GlobalGraphics.drawarray.playernames[i] = Playerlist.list
					.get(i).getName();

		}
	}

	public void run() {
		SerializedBool sebool = null;

		Boolean tasten[] = new Boolean[4];

		int result[] = { 0, 0, 0 };
		boolean connected = true;
		while (connected) {
			connected = false;
			try {
				//Besteht die Verbindung überhaupt noch?
				connected = ois.readBoolean();
				createGraphicsPackage();
				// Grafikpaket abschicken
				oos.reset();
				oos.writeObject(GlobalGraphics.drawarray);
				oos.flush();

				// Steuerungspaket empfangen
				sebool = (SerializedBool) ois.readObject();
				tasten = sebool.getArray();

				if (tasten[0] == true)
					result[0]++; // oben
				if (tasten[2] == true)
					result[0]--; // unten
				if (tasten[1] == true)
					result[1]--; // links
				if (tasten[3] == true)
					result[1]++; // rechts
				if (tasten[4] == true)
					result[2]++; // bombe
				


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Client von Server getrennt");
	}
}