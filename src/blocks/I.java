package blocks;

import java.awt.Color;

public class I extends Block {
	public I() {
		create(new Color(63, 163, 160)); // cyan
	}

	public void setXY(int x, int y) {
		// 1 0 2 3

		tile[0].x = x;
		tile[0].y = y;
		tile[1].x = tile[0].x - Tile.SIZE;
		tile[1].y = tile[0].y;
		tile[2].x = tile[0].x + Tile.SIZE;
		tile[2].y = tile[0].y;
		tile[3].x = tile[0].x + Tile.SIZE * 2;
		tile[3].y = tile[0].y;
	}

	public void getDirection1() {
		// * * * *

		tempTile[0].x = tile[0].x;
		tempTile[0].y = tile[0].y;
		tempTile[1].x = tile[0].x - Tile.SIZE;
		tempTile[1].y = tile[0].y;
		tempTile[2].x = tile[0].x + Tile.SIZE;
		tempTile[2].y = tile[0].y;
		tempTile[3].x = tile[0].x + Tile.SIZE * 2;
		tempTile[3].y = tile[0].y;

		updateXY(1);
	}

	public void getDirection2() {
		// *
		// *
		// *
		// *

		tempTile[0].x = tile[0].x;
		tempTile[0].y = tile[0].y;
		tempTile[1].x = tile[0].x;
		tempTile[1].y = tile[0].y - Tile.SIZE;
		tempTile[2].x = tile[0].x;
		tempTile[2].y = tile[0].y + Tile.SIZE;
		tempTile[3].x = tile[0].x;
		tempTile[3].y = tile[0].y + Tile.SIZE * 2;

		updateXY(2);

	}

	public void getDirection3() {
		getDirection1();
	}

	public void getDirection4() {
		getDirection1();
	}

}
