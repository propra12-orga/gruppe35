import java.util.Random; //Random numbers

public class Power {
	protected String name;
	protected int damage;
	protected double accuracy;

	// Constructor to initialize damage and accuracy
	public Power(String name, int damage, double accuracy) {
		this.name = name;
		this.damage = damage;
		this.accuracy = accuracy;
	}

	// Overloaded Constructor for empty arguments
	public Power() {
		this("Stab", 0, 1.0);
	}

	public boolean Attack() {
		Random randomnum = new Random();
		return (randomnum.nextDouble() < accuracy);
	}

	public int Getdamage() {
		return (damage);
	}

	public String Getname() {
		return (name);
	}

	public void Info() {
		System.out.println("Name: " + name + " Accuracy: " + (100 * accuracy)
				+ "% ;" + "Damage: " + damage + " ;");
	}
}
