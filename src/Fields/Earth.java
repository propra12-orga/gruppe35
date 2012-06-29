package Fields;

import main.Global;

/**
 * 
 * 
 * The Earth class is a child of the Field class and is simply the type of field
 * which is solid, but transforms into Floor when it is destroyed
 * <P>
 * 
 * @author Peet
 */

public class Earth extends Field {
	// Kopiere dieses Feld
	public Earth copy() {
		return new Earth(this);
	}

	// Copy Constructor
	public Earth(Earth earth) {
		super(earth);
	}

	// Default Constructor
	public Earth() {
		this.solid = true;
		this.transformto = new Floor();
		this.image = Global.mauer;
		this.spawnsPowerup = true;
	}

}