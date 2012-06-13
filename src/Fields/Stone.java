package Fields;

/**
 * 
 * The Stone class is a child of the Field class and is simply the type of field
 * which is solid, and does not change even when bombs explode in its vicinity
 * <P>
 * 
 * @author Peet
 */

public class Stone extends Field {

	// Kopiere dieses Feld
	public Stone copy() {
		return new Stone(this);
	}

	// Copy Constructor
	public Stone(Stone stone) {
		super(stone);
	}

	// Default Constructor
	public Stone() {
		this.solid = true;
		this.destructable = false;
		this.transformto = null;
	}
}