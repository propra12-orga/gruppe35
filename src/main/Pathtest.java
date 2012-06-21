package main;

import java.util.Stack;

import Character.Pathfinder;
import Fields.Earth;
import Fields.Exit;
import Fields.Field;
import Fields.Floor;
import Fields.Stone;
import Level.Level;
import Level.Levellist;

public class Pathtest {

	public static void main(String[] args) {

		// Erstelle Level
		int xsize = 7;
		int ysize = 5;

		int spawnpoints[][] = null;
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

		// Setze eine Mauer
		for (int y = 1; y <= 3; y++) {
			level.setField(3, y, stone);
		}

		// Setze aktives Level
		Levellist.activeLevel = level;
		Levellist.activeLevelIndex = 0;
		
		//Suche Pfad von 1,2 nach 5,2
		Pathfinder pathfinder = new Pathfinder();
		pathfinder.find(1,2,5,2);
		Stack<int[]> path = pathfinder.getPath();
		while(!path.empty()){
			int[] step = path.pop();
			System.out.println("x= " +  step[0] + "; y= " + step[1]);
		}
		
	}
}