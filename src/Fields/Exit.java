package Fields;

import Character.Character;
import Level.Levellist;

public class Exit extends Field {

	public boolean enter(Character character) {
		// Falls Flammen auf dem Feld sind wird der Charakter get�tet der das
		// Feld betritt.
		if ((!solid) && (bomb == null)) {
			if (flame > 0) {
				character.kill();
			} else {
				Levellist.next();
			}
			return (true);
		} else {
			return (false);
		}
	}

	// Kopiere dieses Feld
	public Exit copy() {
		return new Exit(this);
	}

	// Copy Constructor
	public Exit(Exit exit) {
		super(exit);
	}

	// Default Constructor
	public Exit() {
		solid = false;
	}

}
