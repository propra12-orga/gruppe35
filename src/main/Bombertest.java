package main;

import java.util.concurrent.TimeUnit;

import Character.Character;
import Fields.Field;
import Fields.Floor;
import Fields.Stone;
import Level.Level;

class Bombertest {
	public static void main(String args[]) {
		// Level 1 besteht aus Stein und Boden Feldern
		Level level1 = new Level(9, 9);
		Field floor = new Floor(); // Boden
		Field stone = new Stone(); // Unzerstörbarer Block

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
		
		level1.draw();

		/*
		 * Debug Field floor2 = new Floor(); level1.setField(0,0,floor2);
		 * level1.setField(9,9,floor2);
		 */
		Character guy2 = new Character("Guy2",0.5,0.5, 1.0, 2, 2, 2, 3);
		guy2.move(1, 0);
		guy2.move(1, 0);
		guy2.move(1, 0);
		System.out.println("Guy2 is now at " + guy2.getPosx() + "," + guy2.getPosy() );
		System.out.println("Guy2 places bomb");
		guy2.placebomb();
		
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
		}
		guy2.move(1, 0);
		guy2.move(1, 0);
		guy2.move(1, 0);
		System.out.println("Guy2 is now at " + guy2.getPosx() + "," + guy2.getPosy() );
		System.out.println("Guy2 places bomb");
		guy2.placebomb();
		System.out.println("Guy2 moves away");
		guy2.move(1, 0);
		guy2.move(1, 0);
		guy2.move(1, 0);
		System.out.println("Guy2 is now at " + guy2.getPosx() + "," + guy2.getPosy() );
		try {
			TimeUnit.MILLISECONDS.sleep(2050);
		} catch (InterruptedException e) {
		}
		System.out.println("Guy2 was able to escape, but he now moves on the still burning field");
		guy2.move(-1, 0);
		
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
		}
		System.out.println("Now it is save for Guy2, he moves on the formerly burning fields!");
		guy2.move(1, 0);
		guy2.move(1, 0);
		guy2.move(1, 0);
		System.out.println("Nothing happens!");
		System.out.println("Guy2 places bomb at " + guy2.getPosx() + "," + guy2.getPosy() + " and  tries to move back and forth!");
		guy2.placebomb();
		guy2.move(-1, 0);
		guy2.move(1, 0);
		System.out.println("Guy2 is now at " + guy2.getPosx() + "," + guy2.getPosy() );
		System.out.println("He cannot reenter the field where the bomb was placed");
		

	}
}
