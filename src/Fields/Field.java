package Fields;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import Bomb.Bomb;
import Character.Character;
import Character.Player;
import Level.Level;
import Powerup.Powerup;

/**
 * This is the Field class, which provides the basis for all special types of
 * fields that can appear in a Level. Each Field knows if it is "solid" ( =
 * passable for Characters or fire), the "bomb" it might contain and flames it
 * might contain. In case the Field is destroyed, it will transform to another
 * Field specified by the "transformto" variable. Also all Characters contained
 * in the "characterlist" will be killed and then removed from the list.
 * 
 * enter() and leave() keep track of the Characters entering and leaving the
 * field and interact with them. destruction() kills all characters on the
 * field, and lets a possible bomb on the field explode
 * 
 * <P>
 * 
 * @author Peet
 */

public class Field {
	// Aenderung
	protected boolean solid; // Ist das Feld fest oder nicht?
	protected LinkedList<Character> characterlist = new LinkedList<Character>(); // Character
	// auf
	// diesem
	// Feld
	protected Bomb bomb = null; // Eventuelle Bombe auf diesem Feld
	protected int flame = 0; // Flammen auf diesem Feld
	protected Field transformto;
	protected Image image; // Grafik
	protected Powerup powerup = null;
	protected boolean spawnsPowerup;
	public boolean exit;

	public Field isTransformable() {
		return transformto;
	}

	public Image getImage() {
		return (image);
	}

	public boolean isExit(){
		return exit;
	}
	public boolean isSolid() {
		return solid;
	}

	public int getFlame() {
		return flame;
	}

	public void addFlame(int i) {
		flame += i;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	public void markAsExit(boolean exit){
		this.exit=exit;
	}

	public List<Character> getCharacterlist() {
		return characterlist;
	}

	public Field() {
		this(false, null, null, false);
	}

	public Field(boolean solid, Field transformto, Image image,
			boolean spawnsPowerup) {
		this.solid = solid;
		this.transformto = transformto;
		this.image = image;
		this.spawnsPowerup = spawnsPowerup;
	}

	public Bomb getBomb() {
		return bomb;
	}

	public void setBomb(Bomb bomb) {
		this.bomb = bomb;
	}

	public void destruction() {
		// Falls eine Bombe auf diesem Feld liegt, lasse sie explodieren
		if (bomb != null)
			bomb.destruction();
		// Töte alle Character auf diesem Feld
		synchronized (characterlist) {
			while (characterlist.size() > 0) {
				characterlist.removeFirst().kill();
			}
		}
	}

	public void setTransformto(Field field) {
		transformto = field;
	}

	public boolean transform(Level level, int x, int y) {
		if (transformto != null) {

			level.setField(x, y, transformto);
			if (spawnsPowerup) {
				level.getField(x, y).powerup = Powerup.random(x, y, 0.5); // Eventuell
				// Powerup
				// spawnen
			}
			return (true);
		}
		return (false);
	}

	public boolean enter(Character character) {
		// Falls Flammen auf dem Feld sind wird der Charakter getötet der das
		// Feld betritt.
		if ((!solid) && (bomb == null)) {
			if (flame > 0) {
				character.kill();
				return (false);
			} else {
				synchronized (characterlist) {
					characterlist.add(character);
				}
				if (character instanceof Player) {
					if (powerup != null) {
						powerup.pickup((Player) (character));
						powerup = null;
					}
				}
				return (true);
			}

		} else {
			return (false);
		}
	}

	public void leave(Character character) {
		synchronized (characterlist) {
			characterlist.remove(character);
		}
	}

	// Kopiere dieses Feld
	public Field copy() {
		return new Field(this);
	}

	// Copy Constructor
	public Field(Field field) {
		this(field.solid, field.transformto, field.image, field.spawnsPowerup);
	}

}
