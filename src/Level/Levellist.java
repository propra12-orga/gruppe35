package Level;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.ListIterator;

import main.Bomblist;
import main.Flamelist;
import main.Menu;
import main.Playerlist;
import Fields.Earth;
import Fields.Exit;
import Fields.Field;
import Fields.Floor;
import Fields.Stone;

public class Levellist {
	public static ArrayList<Level> list = new ArrayList<Level>(); // Levelliste
	public static Level currentlevel = null;

	public static void load() {
		list.clear();
		Field floor = new Floor(); // Boden
		Field stone = new Stone(); // Unzerstörbarer Block
		Field earth = new Earth(); // Zerstörbarer Block
		Field exit = new Exit(); // Ausgang
		Level level1 = new Level(9, 9);
		Level level2 = new Level(9, 9);
		// Erstelle Level 1
		for (int x = 0; x < level1.getXsize(); x++) {
			for (int y = 0; y < level1.getYsize(); y++) {
				if (((x % 2 > 0) && (y % 2 > 0))) {
					level1.setField(x, y, stone);
				} else {
					level1.setField(x, y, floor);
				}
			}
		}
		level1.setField(2, 1, exit);

		// Erstelle Level 2
		for (int x = 0; x < level2.getXsize(); x++) {
			for (int y = 0; y < level2.getYsize(); y++) {
				if (((x % 2 > 0) && (y % 2 > 0))) {
					level2.setField(x, y, stone);
				} else {
					level2.setField(x, y, floor);
				}

			}
		}
		level2.setField(8, 8, earth);
		level2.setField(7, 8, earth);
		level2.setField(6, 8, earth);
		level2.setField(5, 8, earth);
		level2.setField(0, 8, exit);
		// Zur Levelliste hinzufügen
		list.add(level1);
		list.add(level2);

		currentlevel = level1;
		currentlevel.setLocked(false); // Unlock the first level

	}

	public static void load2() {
		try {
			File file = new File("/Levels/Level1.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			document.getDocumentElement().normalize();
			System.out.println("Root element "
					+ document.getDocumentElement().getNodeName());
			NodeList node = document.getElementsByTagName("student");
			System.out.println("Information of the students");

			for (int i = 0; i < node.getLength(); i++) {
				Node firstNode = node.item(i);

				if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) firstNode;
					NodeList firstNameElemntList = element
							.getElementsByTagName("firstname");
					Element firstNameElement = (Element) firstNameElemntList
							.item(0);
					NodeList firstName = firstNameElement.getChildNodes();
					System.out.println("First Name:"
							+ (firstName.item(0).getNodeValue()));

					NodeList lastNameElementList = element
							.getElementsByTagName("lastname");
					Element lastNameElement = (Element) lastNameElementList
							.item(0);
					NodeList lastName = lastNameElement.getChildNodes();
					System.out.println("Last Name :"
							+ (lastName.item(0).getNodeValue()));

					NodeList addressList = element
							.getElementsByTagName("address");
					Element addressElement = (Element) addressList.item(0);
					NodeList address = addressElement.getChildNodes();
					System.out.println("Address : "
							+ ((Node) address.item(0)).getNodeValue());

					NodeList cityList = element.getElementsByTagName("city");
					Element cityElement = (Element) cityList.item(0);
					NodeList city = cityElement.getChildNodes();
					System.out.println("City : "
							+ ((Node) city.item(0)).getNodeValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void next() {
		int i = list.indexOf(currentlevel) + 1;
		ListIterator<Level> it = list.listIterator(i);
		if (it.hasNext()) {
			currentlevel = it.next();
			currentlevel.setLocked(false); // Unlock the new level
			Bomblist.list.clear(); // Alle Bomben nicht mehr zeichnen
			Flamelist.list.clear(); // Alle Flammen nicht mehr zeichnen
			Playerlist.list.get(0).spawn(); // Character neu spawnen
		} else {
			System.out.println("This was the last level, you have won!");
			Menu.panelvisible = false;
			Menu.feld.initialize();
		}
	}
}
