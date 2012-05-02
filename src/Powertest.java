import java.util.*;

class Powertest {
	public static void main(String args[]) {
		Power pow1 = new Power();
		pow1.Info();
		Power pow2 = new Power("Punch",1, 0.75);
		pow2.Info();
		Power pow3 = new Power("Kick",3, 0.5);
		pow3.Info();

		List<Power> powerlist = new ArrayList<Power>(); // Create List of Powers
		powerlist.add(pow1);
		powerlist.add(pow2);
		powerlist.add(pow3);

		Character defaultguy = new Character();
		System.out.println("Hi, my name is " + defaultguy.Getname()
				+ "! I have " + defaultguy.Gethp() + " HitPoints.");

		Character john = new Character("John", 20, 6);
		System.out.println("Hi, my name is " + john.Getname() + "! I have "
				+ john.Gethp() + " HitPoints.");

		john.AddPower(pow1);
		john.AddPower(pow2);
		john.AddPower(pow3);
		john.Attack(0, defaultguy);
		john.Attack(1, defaultguy);
		john.Attack(2, defaultguy);
		john.Attack(2, defaultguy);
		john.Attack(2, defaultguy);
		john.Attack(2, defaultguy);
		john.Attack(2, defaultguy);
		john.Attack(2, defaultguy);
		
		Level level1 = new Level(10,10);
		Field floor = new Field(false);
		Field block = new Field(true);
		level1.AddFieldtype(floor);
		level1.AddFieldtype(block);
		level1.Random();
		level1.Draw();
		

	}
}
