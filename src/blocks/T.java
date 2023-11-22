package blocks;

import java.awt.Color;

public class T extends Block {
	public T() {
		create(new Color(252, 138, 131)); // pink
	}

	public void setXY(int x, int y) {
		// . 1 .
		// 2 0 3

		tile[0].x = x;
		tile[0].y = y;
		tile[1].x = tile[0].x;
		tile[1].y = tile[0].y - Tile.SIZE;
		tile[2].x = tile[0].x - Tile.SIZE;
		tile[2].y = tile[0].y;
		tile[3].x = tile[0].x + Tile.SIZE;
		tile[3].y = tile[0].y;
	}

	public void getDirection1() {
		// . * .
		// * * *

		tempTile[0].x = tile[0].x;
		tempTile[0].y = tile[0].y;
		tempTile[1].x = tile[0].x;
		tempTile[1].y = tile[0].y - Tile.SIZE;
		tempTile[2].x = tile[0].x - Tile.SIZE;
		tempTile[2].y = tile[0].y;
		tempTile[3].x = tile[0].x + Tile.SIZE;
		tempTile[3].y = tile[0].y;

		updateXY(1);
	}

	public void getDirection2() {
		// * .
		// * *
		// * .
		
		tempTile[0].x = tile[0].x;
		tempTile[0].y = tile[0].y;
		tempTile[1].x = tile[0].x + Tile.SIZE;
		tempTile[1].y = tile[0].y;
		tempTile[2].x = tile[0].x;
		tempTile[2].y = tile[0].y - Tile.SIZE;
		tempTile[3].x = tile[0].x;
		tempTile[3].y = tile[0].y + Tile.SIZE;

		updateXY(2);

	}

	public void getDirection3() {
		// * * *
		// . * .
		
		tempTile[0].x = tile[0].x;
		tempTile[0].y = tile[0].y;
		tempTile[1].x = tile[0].x;
		tempTile[1].y = tile[0].y + Tile.SIZE;
		tempTile[2].x = tile[0].x + Tile.SIZE;
		tempTile[2].y = tile[0].y;
		tempTile[3].x = tile[0].x - Tile.SIZE;
		tempTile[3].y = tile[0].y;

		updateXY(3);
	}

	public void getDirection4() {
		// . *
		// * *
		// . *
		
		tempTile[0].x = tile[0].x;
		tempTile[0].y = tile[0].y;
		tempTile[1].x = tile[0].x - Tile.SIZE;
		tempTile[1].y = tile[0].y;
		tempTile[2].x = tile[0].x;
		tempTile[2].y = tile[0].y + Tile.SIZE;
		tempTile[3].x = tile[0].x;
		tempTile[3].y = tile[0].y - Tile.SIZE;

		updateXY(4);
	}
}
