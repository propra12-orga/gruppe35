package Fields;

import Character.Character;
import Level.Levellist;

public class Exit extends Field {

	public boolean enter(Character character) {
		// Falls Flammen auf dem Feld sind wird der Charakter getötet der das
		// Feld betritt.
			Levellist.next();
			character.spawn();		
		return(false);
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
