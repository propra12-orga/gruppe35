import java.util.concurrent.TimeUnit;

class Bombertest {
	public static void main(String args[]) {

		Level level1 = new Level(10, 10);
		Field floor = new Field(false);
		Field block = new Field(true);
		level1.AddFieldtype(floor);
		level1.AddFieldtype(block);
		level1.Random();
		level1.Draw();

		Character guy = new Character();
		System.out.println("guy legt eine Bombe");
		guy.Placebomb();
		System.out
				.println("guy versucht noch eine Bombe zu legen, aber er darf nur eine haben");
		guy.Placebomb(); // Nichts passiert weil guy nur 1 Bombe haben darf
		System.out.println("Warten wir kurz ab");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
		}
		System.out.println("guy legt wieder eine Bombe");
		guy.Placebomb();
		System.out.println("Warten wir kurz ab");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
		}

		Character john = new Character("John", 1.0, 1.0, 3.0, 4, 1, 1, 3);
		System.out.println("John ist ein Supertyp! Er kann 4 Bomben legen");
		john.Placebomb();
		john.Placebomb();
		john.Placebomb();
		john.Placebomb();
	}
}
