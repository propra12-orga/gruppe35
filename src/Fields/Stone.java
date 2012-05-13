package Fields;

public class Stone extends Field {

	// Kopiere dieses Feld
	public Stone copy() {
		return new Stone(this);
	}

	// Copy Constructor
	public Stone(Stone stone) {
		super(stone);
	}
	
	//Default Constructor
	public Stone(){
		solid = true;
	}
}