package Editor;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import Editor.Field;

/**
 * 
 * The Purpose of the ExitPathfinder is to check, if there exists a viable path
 * from a point to the Exit of a level.
 * 
 * The ExitPathfinder does the same as the PathFinder class, except that Earth
 * Fields are considered passable, because they can be destroyed with bombs.
 * <P>
 * 
 * @author Fabian
 */

public class ExitPathfinder {

	private class Node implements Comparable<Node> {
		int x;
		int y;
		int f;
		int g;
		Node predecessor = null;

		private Node(int x, int y, int f, int g) {
			this.x = x;
			this.y = y;
			this.f = f;
			this.g = g;
		}

		@Override
		public int compareTo(Node n) {
			if (this.f < n.f)
				return (-1);
			if (this.f > n.f)
				return (1);
			return (0);
		}
	}

	private PriorityQueue<Node> openlist = new PriorityQueue<Node>();
	private ArrayList<Node> closedlist = new ArrayList<Node>();
	private Node[][] nodes;
	private Node endNode;

	public ExitPathfinder() {
		nodes = new Node[GLevelEditor.level.getXsize()][GLevelEditor.level
				.getYsize()];
	}

	public boolean find(int xo, int yo, int xd, int yd) {
		// Leere Listen
		openlist.clear();
		closedlist.clear();
		// Wandle Felder in Nodes um
		for (int x = 0; x < GLevelEditor.level.getXsize(); x++) {
			for (int y = 0; y < GLevelEditor.level.getYsize(); y++) {
				Field field = GLevelEditor.level.getField(x, y);
				if (field.isSolid() && field.isTransformable() == null) {
					nodes[x][y] = null;
				} else {
					nodes[x][y] = new Node(x, y, 0, 0);
				}
			}
		}
		// Endknoten bestimmen
		endNode = nodes[xd][yd];

		// Startknoten hinzuf�gen
		openlist.add(nodes[xo][yo]);

		do {
			// Knoten mit dem geringsten f Wert aus der Open List entfernen
			Node currentNode = openlist.poll();
			// Wurde das Ziel gefunden?
			if (currentNode == endNode) {
				return (true);
			}
			// Wenn das Ziel noch nicht gefunden wurde: Nachfolgeknoten
			// des aktuellen Knotens auf die Open List setzen
			expandNode(currentNode);
			// der aktuelle Knoten ist nun abschlie�end untersucht
			closedlist.add(currentNode);

		} while (!openlist.isEmpty());
		// die Open List ist leer, es existiert kein Pfad zum Ziel
		return (false);
	}

	private void expandNode(Node currentNode) {

		int x = 0;
		int y = 0;
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				x = currentNode.x + 1;
				y = currentNode.y;
				break;
			case 1:
				x = currentNode.x;
				y = currentNode.y + 1;
				break;
			case 2:
				x = currentNode.x - 1;
				y = currentNode.y;
				break;
			case 3:
				x = currentNode.x;
				y = currentNode.y - 1;
				break;

			default:
			}

			if ((x < 0) || x >= GLevelEditor.level.getXsize() || (y < 0)
					|| y >= GLevelEditor.level.getYsize())
				continue;

			Node successor = nodes[x][y];
			// Falls der Nachfolgeknoten zu einem festen Feld geh�rt oder
			// auf der closedlist ist unternehme nichts
			if ((successor == null) || (closedlist.contains(successor)))
				continue;
			// g Wert f�r den neuen Weg berechnen: g Wert des Vorg�ngers +1
			int tentative_g = currentNode.g + 1;

			// wenn der Nachfolgeknoten bereits auf der Open List ist,
			// aber der neue Weg nicht besser ist als der alte - tue nichts
			if (openlist.contains(successor) && (tentative_g >= successor.g))
				continue;
			// Vorg�ngerzeiger setzen und g Wert merken
			successor.predecessor = currentNode;
			successor.g = tentative_g;
			// f Wert des Knotens in der Open List aktualisieren
			// bzw. Knoten mit f Wert in die Open List einf�gen
			int h = Math.abs((x - endNode.x)) + Math.abs((y - endNode.y)); // Heuristische
			// Strecke bis
			// zum Ziel
			int f = tentative_g + h;
			// Neu einf�gen mit neuer Priorit�t
			successor.f = f;
			openlist.remove(successor);
			openlist.add(successor);

		}
	}

	public Stack<int[]> getPath() {
		Stack<int[]> path = new Stack<int[]>();
		Node currentNode = endNode;
		while (currentNode.predecessor != null) {
			int[] step = new int[2];
			step[0] = currentNode.x;
			step[1] = currentNode.y;

			path.push(step);
			currentNode = currentNode.predecessor;
		}

		return (path);
	}
}