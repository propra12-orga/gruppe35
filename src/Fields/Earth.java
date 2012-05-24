package Fields;

import Level.Level;

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
		solid = true;
	}
	
	public void transform(Level level,int x,int y){
		level.setField(x, y, new Floor());
	}

}