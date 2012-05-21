package Fields;

import Character.Character;
import Level.Level;

public class Exit extends Field {
	protected Level nextlevel;

	public Level getNextlevel() {
		return nextlevel;
	}

	public void setNextlevel(Level nextlevel) {
		this.nextlevel = nextlevel;
	}

	public boolean enter(Character character) {
		// Falls Flammen auf dem Feld sind wird der Charakter getötet der das
		// Feld betritt.
		if ((!solid) && (bomb == null)) {
			if (flame > 0) {
				character.kill();
			} else {
				if (nextlevel != null) {
					character.nextlevel(nextlevel);
				}
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
