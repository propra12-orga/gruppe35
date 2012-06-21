package Character;

import main.Playerlist;

/**
 * 
 * Seeker Enemies search for the nearest Player Character and try to approach it
 * 
 * 
 * <P>
 * 
 * @author Peet
 */

public class Seeker extends Enemy {
	
	Pathfinder pathfinder = new Pathfinder();

	public Seeker(double spawnx, double spawny) {
		super(spawnx, spawny);
	}

	// @Override
	public void AIaction() {
		// Seek for nearest player as target
		double targetdistsq = 1000000.0;
		Player target = null;

		for (int i = 0; i < Playerlist.list.size(); i++) {
			Player player = Playerlist.list.get(i);
			double newtargetdistsq = (player.getPosx()) * (player.getPosx())
					+ (player.getPosy()) * (player.getPosy());
			if (newtargetdistsq < targetdistsq) {
				targetdistsq = newtargetdistsq;
				target = player;
			}
		}
		//Detect fastest route to target
		
		pathfinder.find((int)(posx),(int)(posy),(int)(target.getPosy()),(int)(target.getPosy()));
	}
	
}
