package Fields;


public class Floor extends Field {
	protected boolean solid = false;
	
	// Kopiere dieses Feld
	public Floor copy() {
		return new Floor(this);
	}

	// Copy Constructor
	public Floor(Floor floor) {
		super(floor);
	}
	
	//Default Constructor
	public Floor(){
		solid = false;
	}
}
