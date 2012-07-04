package Level;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import main.Bomblist;
import main.Enemylist;
import main.Flamelist;
import main.Playerlist;
import main.Poweruplist;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Character.Enemy;
import Character.Player;
import Character.Seeker;
import Fields.Earth;
import Fields.Exit;
import Fields.Field;
import Fields.Floor;
import Fields.Stone;
import Network.Server;

/**
 * The Levellist class is responsible for loading and initializing Levels. It is
 * also responsible for changing the Level in case of victory When the program
 * is started, all File paths in the "Levels/" folder are loaded in the
 * "levellist" array. The "activelevel" specifies which Level is played at the
 * moment and the "activelevelindex" the corresponding index in the "levellist"
 * array.
 * 
 * The load() method reads out the XML file corresponding to a Level in the
 * "Levels/" directory, which was hopefully created with the Leveleditor tool.
 * The next() method makes advance to the next Level possible and will be called
 * in case of victory
 * 
 * The next() method lets the game go to the next Level
 * 
 * <P>
 * 
 * @author Peet
 */

public class Levellist {
	public static File[] levelList; // Liste
									// aller
									// Level
									// XML
									// Dateien
									// im
									// Levels
									// Ordner
	public static Level activeLevel = null;
	public static int activeLevelIndex = 0;

	// // Initialisierung
	// static {
	// // Lade Levels aus dem Levels Ordner
	// File levelsfolder = new File("Levels/");
	// levelList = levelsfolder.listFiles();
	// }

	// Lade das Level das zum activeLevelIndex gehört
	public static void load(int levelIndex) {
		if (Server.single) {
			File levelsfolder = new File("Levels/");
			levelList = levelsfolder.listFiles();
		} else {
			File levelsfolder = new File("Levels/Multi/");
			levelList = levelsfolder.listFiles();
		}

		try {
			File file = levelList[levelIndex];
			// Besorge Levelname aus Dateinamen ohne Extension
			String levelname;
			int index = file.getName().lastIndexOf('.');
			if (index > 0 && index <= file.getName().length() - 2) {
				levelname = file.getName().substring(0, index);
			} else {
				levelname = file.getName();
			}
			System.out.println("Levelname = " + levelname);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			Element rootElement = document.getDocumentElement();
			rootElement.normalize();

			// Besorge Levelgröße aus RootElement
			int xsize = Integer.parseInt(rootElement.getAttribute("xsize"));
			int ysize = Integer.parseInt(rootElement.getAttribute("ysize"));

			// Besorge NodeList der Felder aus dem fields Element und der
			// Spawnpoints aus dem spawnpoints Element und der Exits aus dem
			// exits Element
			NodeList searchNodeList;
			searchNodeList = rootElement.getElementsByTagName("fields");
			NodeList fieldNodes = searchNodeList.item(0).getChildNodes();

			searchNodeList = rootElement.getElementsByTagName("spawnpoints");
			NodeList spawnpointNodes = searchNodeList.item(0).getChildNodes();

			searchNodeList = rootElement
					.getElementsByTagName("spawnpointsbaer");
			NodeList baerNodes = searchNodeList.item(0).getChildNodes();

			searchNodeList = rootElement.getElementsByTagName("exits");
			NodeList exitNodes = searchNodeList.item(0).getChildNodes();

			// Lese Spawnpoints aus
			int spawnpointnum = spawnpointNodes.getLength();
			int spawnpoints[][] = new int[spawnpointnum][2];
			for (int i = 0; i < spawnpointnum; i++) {
				Element thisElement = (Element) spawnpointNodes.item(i);
				int x = Integer.parseInt(thisElement.getAttribute("x")) - 1;
				int y = Integer.parseInt(thisElement.getAttribute("y")) - 1;
				spawnpoints[i][0] = x;
				spawnpoints[i][1] = y;
			}

			// Lese Exits aus

			int exitnum = exitNodes.getLength();
			int exits[][] = new int[exitnum][2];
			for (int i = 0; i < exitnum; i++) {
				Element thisElement = (Element) exitNodes.item(i);
				int x = Integer.parseInt(thisElement.getAttribute("x")) - 1;
				int y = Integer.parseInt(thisElement.getAttribute("y")) - 1;
				exits[i][0] = x;
				exits[i][1] = y;
			}

			// Erstelle Level
			Level level = new Level(xsize, ysize, spawnpoints);

			Field floor = new Floor(); // Boden
			Field stone = new Stone(); // Unzerstörbarer Block
			Field earth = new Earth(); // Zerstörbarer Block
			Field exit = new Exit(); // Ausgang

			// Erstmal alles auf Floor setzen
			for (int x = 0; x < xsize; x++) {
				for (int y = 0; y < ysize; y++) {
					level.setField(x, y, floor);
				}
			}

			// Lese Felder aus und setze sie
			for (int i = 0; i < fieldNodes.getLength(); i++) {
				Element thisElement = (Element) fieldNodes.item(i);
				int x = Integer.parseInt(thisElement.getAttribute("x")) - 1;
				int y = Integer.parseInt(thisElement.getAttribute("y")) - 1;
				String type = thisElement.getAttribute("type");
				if ((type.equals("F")) || type.equals("f"))
					level.setField(x, y, floor);
				if ((type.equals("S")) || type.equals("s"))
					level.setField(x, y, stone);
				if ((type.equals("E")) || type.equals("e"))
					level.setField(x, y, earth);
			}
			// Lege Exits an
			for (int i = 0; i < exitnum; i++) {
				int x = exits[i][0];
				int y = exits[i][1];
				Field field = level.getField(x, y);
				if (field instanceof Floor) {
					// Offener Ausgang
					level.setField(x, y, exit);
				} else {

					// Versteckter Ausgang
					field.setTransformto(exit);
				}

			}

			// Setze aktives Level
			activeLevel = level;
			activeLevelIndex = levelIndex;
			// Lese BärSpawnpoints aus
			int baernum = baerNodes.getLength();
			int spawnpointsbaer[][] = new int[baernum][2];
			for (int i = 0; i < baernum; i++) {
				Element thisElement = (Element) baerNodes.item(i);
				int x = Integer.parseInt(thisElement.getAttribute("x")) - 1;
				int y = Integer.parseInt(thisElement.getAttribute("y")) - 1;
				spawnpointsbaer[i][0] = x;
				spawnpointsbaer[i][1] = y;
				Enemy Baer = new Seeker(spawnpointsbaer[i][0],
						spawnpointsbaer[i][1]);
				Enemylist.list.add(Baer);
				Baer.spawn();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void next() {
		if (activeLevelIndex < levelList.length - 1) {
			// Charaktere von Feldern entfernen
			for (int i = 0; i < Playerlist.list.size(); i++) {
				Player character = Playerlist.list.get(i);
				activeLevel.getField((int) (character.getPosx()),
						(int) (character.getPosy())).leave(character);
			}
			// Bomben anhalten
			for (int i = 0; i < Bomblist.list.size(); i++) {
				Bomblist.list.get(i).stop(); // Bombthreads stoppen
			}
			Bomblist.list.clear(); // Alle Bomben nicht mehr zeichnen
			Flamelist.list.clear(); // Alle Flammen nicht mehr zeichnen
			// Gegner anhalten
			for (int i = 0; i < Enemylist.list.size(); i++) {
				Enemylist.list.get(i).stop(); // Enemythreads stoppen
			}
			Enemylist.list.clear(); // Alle Enemies nicht mehr zeichnen

			Poweruplist.list.clear(); // Alle Powerups nicht mehr zeichnen

			// Neues Level laden
			activeLevelIndex++; // Index erhöhen
			load(activeLevelIndex); // Neues Level laden
			activeLevel.setLocked(false); // Neues Level freischalten

			// Charactere neu spawnen
			for (int i = 0; i < Playerlist.list.size(); i++) {
				Playerlist.list.get(i).spawn();
			}

			// Gegner spawnen

			for (int i = 0; i < Enemylist.list.size(); i++) {
				Enemylist.list.get(i).spawn();
			}

		} else {
			System.out.println("This was the last level, you have won!");
		}
	}

	public static void terminate() {
		// Bomben anhalten
		for (int i = 0; i < Bomblist.list.size(); i++) {
			Bomblist.list.get(i).stop(); // Bombthreads stoppen
		}
		Bomblist.list.clear(); // Alle Bomben nicht mehr zeichnen
		Flamelist.list.clear(); // Alle Flammen nicht mehr zeichnen
		// Gegner anhalten
		for (int i = 0; i < Enemylist.list.size(); i++) {
			Enemylist.list.get(i).stop(); // Enemythreads stoppen
		}
		Enemylist.list.clear(); // Alle Enemies nicht mehr zeichnen

		Poweruplist.list.clear(); // Alle Powerups nicht mehr zeichnen
		// Charactere löschen
		Playerlist.list.clear();

		// Level auf null setzen
		activeLevel = null;
		activeLevelIndex = 0;
	}
}
