package Fields;

import java.util.LinkedList;
import java.util.List;

import Bomb.Bomb;
import Character.Character;

public class Field {
	// Aenderung
	protected boolean solid; // Ist das Feld fest oder nicht?
	protected LinkedList<Character> characterlist = new LinkedList<Character>(); // Character
	// auf
	// diesem
	// Feld
	protected Bomb bomb = null; // Eventuelle Bombe auf diesem Feld
	protected int flame = 0; // Flammen auf diesem Feld

	public boolean isSolid() {
		return solid;
	}

	public int getflame() {
		return flame;
	}

	public void addFlame(int i) {
		flame += i;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public List<Character> getCharacterlist() {
		return characterlist;
	}

	public Field() {
		this(false);
	}

	public Field(boolean solid) {
		this.solid = solid;
	}

	public Bomb getBomb() {
		return bomb;
	}

	public void setBomb(Bomb bomb) {
		this.bomb = bomb;
	}

	public void destruction() {
		// Falls eine Bombe auf diesem Feld liegt, lasse sie explodieren
		if (bomb != null)
			bomb.destruction();
		// Töte alle Character auf diesem Feld
		synchronized (characterlist) {
			while (characterlist.size() > 0) {
				characterlist.removeFirst().kill();
			}
		}

	}

	public boolean enter(Character character) {
		// Falls Flammen auf dem Feld sind wird der Charakter getötet der das
		// Feld betritt.
		if ((!solid) && (bomb == null)) {
			if (flame > 0) {
				character.kill();
			} else {
				synchronized (characterlist) {
					characterlist.add(character);
				}
			}
			return (true);
		} else {
			return (false);
		}
	}

	public void leave(Character character) {
		synchronized (characterlist) {
			characterlist.remove(character);
		}
	}

	// Kopiere dieses Feld
	public Field copy() {
		return new Field(this);
	}

	// Copy Constructor
	public Field(Field field) {
		this(field.solid);
	}

}
