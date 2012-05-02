import java.util.ArrayList;
import java.util.List;
import java.util.Random; //Random numbers

public class Level {
	protected int xsize;
	protected int ysize;
	protected Field[][] field;
	protected List<Field> Fieldtypes = new ArrayList<Field>();

	Level(int xsize, int ysize) {
		this.xsize = xsize;
		this.ysize = ysize;
		this.field = new Field[xsize][ysize];
	}

	void Setfield(int x, int y, Field field) {
		this.field[x][y] = field;
	}

	void AddFieldtype(Field field) {
		if (!Fieldtypes.contains(field))
			Fieldtypes.add(field);
	}

	void Random() {
		Random randomnum = new Random();
		for (int x = 0; x < xsize; x++) {
			for (int y = 0; y < ysize; y++) {
				Setfield(x, y,
						Fieldtypes.get(randomnum.nextInt(Fieldtypes.size())));
			}
		}
	}

	void Draw() {
		String line;
		for (int y = 0; y < ysize; y++) {
			line = "";
			for (int x = 0; x < xsize; x++) {
				if (field[x][y].solid = true) {
					line += "#";
				} else {
					line += "0";
				}
			}
			System.out.println(line);
		}
	}
}
