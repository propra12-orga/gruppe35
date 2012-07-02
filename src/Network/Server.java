package Network;

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
	//Lade erstes Level
	Levellist.load(0);
	}

	private ServerSocket arrayServer;

	// public static void main(String argv[]) throws Exception {
	// new ArrayMultiplier();
	// }

	public Server() throws Exception {
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
		SerializedBool sebool = null;

		Boolean tasten[] = new Boolean[4];

		int result[] = { 0, 0, 0 };

		while (true) {
			try {
				// Grafikpaket schn�ren
				// Altes paket l�schen
				GlobalGraphics.drawarray.array.clear();
				// Felder hinzuf�gen
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
			
				//Bomben hinzuf�gen
				 for (int i = 0; i < Bomblist.list.size(); i++) {
			     
			 		GlobalGraphics.drawarray.add(Bomblist.list.get(i).getDrawx(), Bomblist.list.get(i).getDrawy(),
			 				Bomblist.list.get(i).getPixsizex(), Bomblist.list.get(i).getPixsizey(), Bomblist.list.get(i).getImageID());
			 	}
				
				// Flammen hinzuf�gen
				 for (int i = 0; i < Flamelist.list.size(); i++) {
				 GlobalGraphics.drawarray.add(Flamelist.list.get(i).getDrawx(), Flamelist.list.get(i).getDrawy(),
			 				Flamelist.list.get(i).getPixsizex(), Flamelist.list.get(i).getPixsizey(), Flamelist.list.get(i).getImageID());
				
				}
				
				// Player hinzuf�gen
				for (int i = 0; i < Playerlist.list.size(); i++) {
					 GlobalGraphics.drawarray.add(Playerlist.list.get(i).getDrawx(), Playerlist.list.get(i).getDrawy(),
				 				Playerlist.list.get(i).getPixsizex(), Playerlist.list.get(i).getPixsizey(), Playerlist.list.get(i).getImageID());
				// Playerlist.list.get(i).DrawComponent(g, panel);
				}
				
				// Gegner hinzuf�gen
				 for (int i = 0; i < Enemylist.list.size(); i++) {
					 GlobalGraphics.drawarray.add(Enemylist.list.get(i).getDrawx(), Enemylist.list.get(i).getDrawy(),
				 				Enemylist.list.get(i).getPixsizex(), Enemylist.list.get(i).getPixsizey(), Enemylist.list.get(i).getImageID());
				 }
				
				// Powerups hinzuf�gen
				for (int i = 0; i < Poweruplist.list.size(); i++) {
					 GlobalGraphics.drawarray.add(Poweruplist.list.get(i).getDrawx(), Poweruplist.list.get(i).getDrawy(),
				 				Poweruplist.list.get(i).getPixsizex(), Poweruplist.list.get(i).getPixsizey(),Poweruplist.list.get(i).getImageID());
				}
				//Statbox hinzuf�gen
				GlobalGraphics.drawarray.statsRect = new int[]{GlobalGraphics.sqsize
						 * Levellist.activeLevel.getXsize(), 0, 100,
						 100};
				//Namen und Leben der Spieler hinzuf�gen
				GlobalGraphics.drawarray.playerlifes = new int[Playerlist.list.size()];
				GlobalGraphics.drawarray.playernames = new String[Playerlist.list.size()];
				for(int i=0;i<Playerlist.list.size();i++){
					GlobalGraphics.drawarray.playerlifes[i] = Playerlist.list.get(i).getLifes();
					GlobalGraphics.drawarray.playernames[i] = Playerlist.list.get(i).getName();
			
				}
				//Grafikpaket abschicken
				oos.reset();
				oos.writeObject(GlobalGraphics.drawarray);
				oos.flush();
				
				//Steuerungspaket empfangen
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
				
				// close connections
				// ois.close();
				// oos.close();
				// client.close();
			} catch (Exception e) {
			}
		}

	}
}