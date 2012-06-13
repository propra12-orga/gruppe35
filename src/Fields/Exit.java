package Fields;

import Character.Character;
import Level.Levellist;

/**
 * 
 *         The Exit class is a child of the Field class and is very similar to
 *         the Floor class, except for the overwritten enter() method Should a
 *         Character enter the Exit, the next Level ist started.
 * 
 *         <P>
 * 
 * @author Peet
 */

public class Exit extends Field {

	public boolean enter(Character character) {
		Levellist.next();
		return (false);
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
		this.solid = false;
		this.transformto = null;
	}

}
