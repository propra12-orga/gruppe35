package Fields;

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
	}

}