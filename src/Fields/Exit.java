package Fields;

import Character.Character;
import Character.Player;
import Level.Levellist;

/**
 * 
 * The Exit class is a child of the Field class and is very similar to the Floor
 * class, except for the overwritten enter() method Should a Character enter the
 * Exit, the next Level ist started.
 * 
 * <P>
 * 
 * @author Peet
 */

public class Exit extends Field {

	@Override
	public boolean enter(Character character) {
		if (character instanceof Player) {
			Levellist.next();
			return (false);
		} else {

			return (super.enter(character));
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
		this.solid = false;
		this.transformto = null;
	}

}
