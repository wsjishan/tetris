package blocks;

import java.awt.Color;
import java.awt.Graphics2D;

import core.GameView;
import core.KeyController;
import core.PlayController;

public class Block {
	public Tile tile[] = new Tile[4];
	public Tile tempTile[] = new Tile[4];
	int autoDropCounter = 0;
	public int direction = 1;
	boolean leftCollision, rightCollision, bottomCollision;
	public boolean pieceActiveStatus = true;
	public boolean deactivating = false;
	int deactivateCounter = 0;

	public void create(Color c) {
		tile[0] = new Tile(c);
		tile[1] = new Tile(c);
		tile[2] = new Tile(c);
		tile[3] = new Tile(c);
		tempTile[0] = new Tile(c);
		tempTile[1] = new Tile(c);
		tempTile[2] = new Tile(c);
		tempTile[3] = new Tile(c);
	}

	public void setXY(int x, int y) {

	}

	public void updateXY(int direction) {
		checkRotationCollision();
		if (leftCollision == false && rightCollision == false && bottomCollision == false) {
			this.direction = direction;
			tile[0].x = tempTile[0].x;
			tile[0].y = tempTile[0].y;
			tile[1].x = tempTile[1].x;
			tile[1].y = tempTile[1].y;
			tile[2].x = tempTile[2].x;
			tile[2].y = tempTile[2].y;
			tile[3].x = tempTile[3].x;
			tile[3].y = tempTile[3].y;
		}
	}

	public void getDirection1() {

	}

	public void getDirection2() {

	}

	public void getDirection3() {

	}

	public void getDirection4() {

	}

	public void checkMovementCollision() {

		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		checkFixedTilesCollision();

		// for left side
		for (int i = 0; i < tile.length; i++) {
			if (tile[i].x == PlayController.x_left) {
				leftCollision = true;
			}
		}
		// for right side
		for (int i = 0; i < tile.length; i++) {
			if (tile[i].x + Tile.SIZE == PlayController.x_right) {
				rightCollision = true;
			}
		}
		// for bottom side
		for (int i = 0; i < tile.length; i++) {
			if (tile[i].y + Tile.SIZE == PlayController.y_bottom) {
				bottomCollision = true;
			}
		}
	}

	public void checkRotationCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		checkFixedTilesCollision();

		// for left side
		for (int i = 0; i < tile.length; i++) {
			if (tempTile[i].x < PlayController.x_left) {
				leftCollision = true;
			}
		}
		// for right side
		for (int i = 0; i < tile.length; i++) {
			if (tempTile[i].x + Tile.SIZE > PlayController.x_right) {
				rightCollision = true;
			}
		}
		// for bottom side
		for (int i = 0; i < tile.length; i++) {
			if (tempTile[i].y + Tile.SIZE > PlayController.y_bottom) {
				bottomCollision = true;
			}
		}

	}

	private void checkFixedTilesCollision() {

		for (int i = 0; i < PlayController.fixedTiles.size(); i++) {

			int targetX = PlayController.fixedTiles.get(i).x;
			int targetY = PlayController.fixedTiles.get(i).y;

			// for down side
			for (int i1 = 0; i1 < tile.length; i1++) {
				if (tile[i1].y + Tile.SIZE == targetY && tile[i1].x == targetX) { // p in 2nd logic.
					bottomCollision = true;
				}
			}

			// for left side
			for (int i1 = 0; i1 < tile.length; i1++) {
				if (tile[i1].x - Tile.SIZE == targetX && tile[i1].y == targetY) { // p in 2nd logic.
					leftCollision = true;
				}
			}

			// for right side
			for (int i1 = 0; i1 < tile.length; i1++) {
				if (tile[i1].x + Tile.SIZE == targetX && tile[i1].y == targetY) { // p in 2nd logic.
					rightCollision = true;
				}
			}
		}

	}

	public void quickDrop() {
		while (bottomCollision == false) {
			tile[0].y += Tile.SIZE;
			tile[1].y += Tile.SIZE;
			tile[2].y += Tile.SIZE;
			tile[3].y += Tile.SIZE;
			checkMovementCollision(); // Check for collisions at each step
		}
	}

	public void update() {
		if (KeyController.spacePressed) {
			quickDrop();
			KeyController.spacePressed = false; // Reset the flag
		}

		if (deactivating) {
			deactivating();
		}

		// move the mino
		if (KeyController.upPressed) {
			switch (direction) {
			case 1:
				getDirection2();
				break;
			case 2:
				getDirection3();
				break;
			case 3:
				getDirection4();
				break;
			case 4:
				getDirection1();
				break;
			}
			KeyController.upPressed = false;

			GameView.bgm.play(3, false);
		}

		checkMovementCollision();

		if (KeyController.downPressed) {
			if (bottomCollision == false) {
				tile[0].y += Tile.SIZE;
				tile[1].y += Tile.SIZE;
				tile[2].y += Tile.SIZE;
				tile[3].y += Tile.SIZE;

				autoDropCounter = 0;
			}
			KeyController.downPressed = false;
		}

		if (KeyController.leftPressed) {
			if (leftCollision == false) {
				tile[0].x -= Tile.SIZE;
				tile[1].x -= Tile.SIZE;
				tile[2].x -= Tile.SIZE;
				tile[3].x -= Tile.SIZE;
			}
			KeyController.leftPressed = false;
		}

		if (KeyController.rightPressed) {
			if (rightCollision == false) {
				tile[0].x += Tile.SIZE;
				tile[1].x += Tile.SIZE;
				tile[2].x += Tile.SIZE;
				tile[3].x += Tile.SIZE;
			}
			KeyController.rightPressed = false;
		}

		if (bottomCollision) {
			if (deactivating == false) {
				GameView.bgm.play(4, false);
			}
			deactivating = true;
		}

		else if (bottomCollision == false)

		{
			autoDropCounter++;
			if (autoDropCounter == PlayController.pieceDropInterval) {
				tile[0].y += Tile.SIZE;
				tile[1].y += Tile.SIZE;
				tile[2].y += Tile.SIZE;
				tile[3].y += Tile.SIZE;
				autoDropCounter = 0;
			}
		}
	}

	private void deactivating() {
		deactivateCounter++;

		if (deactivateCounter == 45) {
			deactivateCounter = 0;
			checkMovementCollision();

			if (bottomCollision) {
				pieceActiveStatus = false;
			}
		}
	}

	public void draw(Graphics2D g2) {
		int margin = 2;
		g2.setColor(tile[0].c);
		g2.fillRect(tile[0].x + margin, tile[0].y + margin, Tile.SIZE - (margin * 2), Tile.SIZE - (margin * 2));
		g2.fillRect(tile[1].x + margin, tile[1].y + margin, Tile.SIZE - (margin * 2), Tile.SIZE - (margin * 2));
		g2.fillRect(tile[2].x + margin, tile[2].y + margin, Tile.SIZE - (margin * 2), Tile.SIZE - (margin * 2));
		g2.fillRect(tile[3].x + margin, tile[3].y + margin, Tile.SIZE - (margin * 2), Tile.SIZE - (margin * 2));
	}
}
