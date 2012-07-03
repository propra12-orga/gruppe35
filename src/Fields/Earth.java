package Fields;

/**
 * 
 * 
 * The Earth class is a child of the Field class and is simply the type of field
 * which is solid, but transforms into Floor when it is destroyed It is also
 * capable of spawning Powerups when destroyed
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
		this.spawnsPowerup = true;
		this.exit = false;
		this.fieldtype = "E";
		this.imageID = 8;
	}

}