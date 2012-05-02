import java.util.*;

public class Character {
	protected String name = "Guy";
	protected int maxhp = 10;
	protected int hp = maxhp;
	protected int speed = 6;
	protected boolean valid=true;
	protected List<Power> powerlist = new ArrayList<Power>();

	public Character(String name, int maxhp, int speed) {
		this.name = name;
		this.maxhp = maxhp;
		this.hp = maxhp;
		this.speed = speed;
	}

	public Character() {
		this("Guy", 10, 6); // Call other constructor
	}

	public String Getname() {
		return (name);
	}

	public String Gethp() {
		return (hp + "/" + maxhp);
	}

	public void AddPower(Power power) {
		if (!powerlist.contains(power))
			powerlist.add(power);
	}

	public void Attack(int powerindex, Character target) {
		Power power = powerlist.get(powerindex);
		if (power.Attack()) {
			System.out.println(this.name + " hits " + target.Getname()
					+ " with " + power.Getname() + " and deals "
					+ power.Getdamage() + " damage!");
			target.hp -= power.Getdamage();
			System.out.println(target.Getname() + " has " + target.Gethp()
					+ " HP left.");
			if (target.hp <= 0)
				target.Kill();
		} else {
			System.out.println(this.name + " misses " + target.Getname()
					+ " with " + power.Getname() + "!");
		}
	}

	public void Kill() {
		System.out.println(this.name + " goes down!");
		valid=false;
	}

}
