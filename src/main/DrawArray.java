package main;

import java.util.ArrayList;

public class DrawArray {
	public ArrayList<int[]> array = new ArrayList<int[]>();
	public int[] statsRect;
	public String[] playernames;
	public int[] playerlifes;
	public int[] playerlifeposx;
	public int[] playernameposx;
	

	// drawArrayItem[0] = drawx;
	// drawArrayItem[1] = drawy;
	// drawArrayItem[2] = pixsizex;
	// drawArrayItem[3] = pixsizey;
	// drawArrayItem[4] = imageID;

	public void add(int drawx, int drawy, int pixsizex, int pixsizey,
			int imageID) {
		int[] drawItem = { drawx, drawy, pixsizex, pixsizey, imageID };
		array.add(drawItem);
	}
	


}
