package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * The DrawArray class contains everything that a GUI needs to draw everything.
 * Its main purpost is to be serialized and send as a Graphics Package from a
 * Server to a Client, where it is then processed by the GUI.
 * <P>
 * 
 * @author Peet
 */

public class DrawArray implements Serializable {
	public ArrayList<int[]> array = new ArrayList<int[]>();
	public String[] playernames;
	public int[] playerlifes;
	public int[] levelSize;

	// arrayItem[0] = drawx;
	// arrayItem[1] = drawy;
	// arrayItem[2] = pixsizex;
	// arrayItem[3] = pixsizey;
	// arrayItem[4] = imageID;

	public void add(int drawx, int drawy, int pixsizex, int pixsizey,
			int imageID) {
		int[] drawItem = { drawx, drawy, pixsizex, pixsizey, imageID };
		array.add(drawItem);
	}

}
