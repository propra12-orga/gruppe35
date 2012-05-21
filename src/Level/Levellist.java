package Level;

import java.util.ArrayList;
import java.util.ListIterator;

import Fields.Exit;
import Fields.Field;
import Fields.Floor;
import Fields.Stone;

public class Levellist {
	public static ArrayList<Level> list = new ArrayList<Level>(); // Levelliste
	public static Level currentlevel;

	public static void load() {
		Field floor = new Floor(); // Boden
		Field stone = new Stone(); // Unzerstörbarer Block
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
		level1.setField(8, 8, exit);

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
		level2.setField(0, 8, exit);
		// Zur Levelliste hinzufügen
		list.add(level1);
		list.add(level2);

	}

	public static void next(){
		int i = list.indexOf(currentlevel);
		ListIterator<Level> it = list.listIterator(i); 
		if(it.hasNext()){
			currentlevel=it.next();
			currentlevel.setLocked(false); //Unlock the new level
		}else{
			System.out.println("This was the last level, you have won!");
		}
	}
}
