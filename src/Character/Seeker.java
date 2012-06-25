package Character;

import java.util.Stack;

import main.Global;
import main.Playerlist;

/**
 * 
 * Seeker Enemies search for the nearest Player Character and try to approach it
 * They use a Pathfinder to get the shortest path to the Player If there exists
 * no path or it is already very close, it just tries to move towards the Player
 * 
 * When the Graphics of the Seeker and the Player overlap, the Seeker kills the
 * Player
 * 
 * <P>
 * 
 * @author Peet
 */

public class Seeker extends Enemy {

	Pathfinder pathfinder = new Pathfinder();
	Stack<int[]> path = null;

	int destx, desty;

	public Seeker(double spawnx, double spawny) {
		super(spawnx, spawny, 0.03, Global.seeker, Global.seekerstanding,
				Global.seekerdead);
	}

	// @Override
	protected void AIaction() {
		// Seek for nearest player as target
		int targetdist = 100;
		Player target = null;
		

		for (int i = 0; i < Playerlist.list.size(); i++) {
			Player player = Playerlist.list.get(i);
			if (player.isDead())
				continue;
			int newtargetdist = Math
					.abs((int) (player.getPosx()) - (int) (posx))
							+ Math.abs((int) (player.getPosy()) - (int) (posy));
			if (newtargetdist < targetdist) {
				targetdist = newtargetdist;
				target = player;
			}
		}

		if (target != null) {

			// Get Pixel distance between this enemy and the target

			int pixdistx = Math
					.abs((int) ((target.getPosx() - this.posx) * Global.sqsize))
					- (target.getPixsizey() + this.pixsizey) / 2;
			int pixdisty = Math
					.abs((int) ((target.getPosy() - this.posy) * Global.sqsize))
					- (target.getPixsizey() + this.pixsizey) / 2;
			
			boolean newpath = false;
			
			if (pixdistx < 0 && pixdisty < 0) {
				target.kill();
			} else {
				if (targetdist >= 1) {
					// Detect fastest route to target
					if (pathfinder.find((int) (posx), (int) (posy),
							(int) (target.getPosx()), (int) (target.getPosy()))) {
						// Path to target exists
						path = pathfinder.getPath();
						newpath = true;

					} else {
						// Use the old path (automatically)
					}
				}
			}

			// Movement
			int dirx = 0;
			int diry = 0;
			if (path == null || path.empty() || targetdist <= 1) {
				// No path given or already close to target, just try to move
				// towards target
				// Decrease the larger of x and y distance first
				if (pixdistx > pixdisty) {
					dirx = (int) (Math.signum(target.getPosx() - this.posx));
				} else {
					diry = (int) (Math.signum(target.getPosy() - this.posy));
				}
			} else {
				// Move along calculated path
				
				int x = (int) (this.posx);
				int y = (int) (this.posy);
				
				if(newpath || (x == destx && y == desty)){
					int[] step = path.pop();
					destx = step[0];
					desty = step[1];
				}
			
				dirx = destx - x;
				diry = desty - y;
			}

			move(dirx, diry);
		}
	}
}
