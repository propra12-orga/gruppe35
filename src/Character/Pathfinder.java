package Character;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import Fields.Field;
import Level.Levellist;

/**
 * 
 * The Pathfinder class seeks for the shortest path between two points on a
 * quadratic grid. The method applied here to find this shortest path is the
 * well known A*-Algorithm.
 * 
 * The find method will find the desired path, when it is provided with the x
 * and y coordinates of the start point and the x and y coordinates of the end
 * point as its arguments.
 * 
 * All Fields which are solid are considered to be impassable for this Pathfinder.
 * 
 * When find was invoked, the getPath() method will return a Stack containing
 * int[2] which represent the path that has to be taken to go
 * from origin to destination.
 * 
 * If this stack is null or empty, there is no possible path.
 * <P>
 * 
 * @author Peet
 */

public class Pathfinder {

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

	public Pathfinder() {
		nodes = new Node[Levellist.activeLevel.getXsize()][Levellist.activeLevel
				.getYsize()];
	}

	public boolean find(int xo, int yo, int xd, int yd) {
		// Leere Listen
		openlist.clear();
		closedlist.clear();
		// Wandle Felder in Nodes um
		for (int x = 0; x < Levellist.activeLevel.getXsize(); x++) {
			for (int y = 0; y < Levellist.activeLevel.getYsize(); y++) {
				Field field = Levellist.activeLevel.getField(x, y);
				if (field.isSolid()) {
					nodes[x][y] = null;
				} else {
					nodes[x][y] = new Node(x, y, 0, 0);
				}
			}
		}
		// Endknoten bestimmen
		endNode = nodes[xd][yd];

		// Startknoten hinzufügen
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
			// der aktuelle Knoten ist nun abschließend untersucht
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

			if ((x < 0) || x >= Levellist.activeLevel.getXsize() || (y < 0)
					|| y >= Levellist.activeLevel.getYsize())
				continue;

			Node successor = nodes[x][y];
			// Falls der Nachfolgeknoten zu einem festen Feld gehört oder
			// auf der closedlist ist unternehme nichts
			if ((successor == null) || (closedlist.contains(successor)))
				continue;
			// g Wert für den neuen Weg berechnen: g Wert des Vorgängers +1
			int tentative_g = currentNode.g + 1;

			// wenn der Nachfolgeknoten bereits auf der Open List ist,
			// aber der neue Weg nicht besser ist als der alte - tue nichts
			if (openlist.contains(successor) && (tentative_g >= successor.g))
				continue;
			// Vorgängerzeiger setzen und g Wert merken
			successor.predecessor = currentNode;
			successor.g = tentative_g;
			// f Wert des Knotens in der Open List aktualisieren
			// bzw. Knoten mit f Wert in die Open List einfügen
			int h = Math.abs((x - endNode.x)) + Math.abs((y - endNode.y)); // Heuristische
			// Strecke bis
			// zum Ziel
			int f = tentative_g + h;
			// Neu einfügen mit neuer Priorität
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