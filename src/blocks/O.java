package blocks;

import java.awt.Color;

public class O extends Block {
	public O() {
		create(new Color(255, 219, 0)); // yellow
	}

	public void setXY(int x, int y) {
		// 0 2
		// 1 3
		
		tile[0].x = x;
		tile[0].y = y;
		tile[1].x = tile[0].x;
		tile[1].y = tile[0].y + Tile.SIZE;
		tile[2].x = tile[0].x + Tile.SIZE;
		tile[2].y = tile[0].y;
		tile[3].x = tile[0].x + Tile.SIZE;
		tile[3].y = tile[0].y + Tile.SIZE;
	}

	public void getDirection1() {
	}

	public void getDirection2() {
	}

	public void getDirection3() {
	}

	public void getDirection4() {
	}
}
