package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import blocks.Block;
import blocks.I;
import blocks.J;
import blocks.L;
import blocks.O;
import blocks.S;
import blocks.T;
import blocks.Tile;
import blocks.Z;

public class PlayController {

	// initializing width, height and coordinate of the play area
	public static final int PLAYAREA_WIDTH = 360;
	public static final int PLAYAREA_HEIGHT = 600;
	public static int x_left;
	public static int x_right;
	public static int y_top;
	public static int y_bottom;

	// initializing pieces and their coordinate
	static Block currentPiece;
	public static int PIECE_X_START;
	public static int PIECE_Y_START;
	public static Block nextPiece;
	public static int NEXTPIECE_X;
	public static int NEXTPIECE_Y;

	// initializing an array list which will be used to check collision and other
	// stuff
	public static ArrayList<Tile> fixedTiles = new ArrayList<>();

	public static int pieceDropInterval = 60;
	boolean gameOver;

	int score = 0;
	int level = 1;
	int lines = 0;

	PlayController() {
		// setting the value of the coordinates of the play area
		x_left = 0;
		x_right = x_left + PLAYAREA_WIDTH;
		y_top = 45;
		y_bottom = y_top + PLAYAREA_HEIGHT;

		// setting the coordinate and the current piece
		PIECE_X_START = x_left + (PLAYAREA_WIDTH / 2) - Tile.SIZE;
		PIECE_Y_START = y_top + Tile.SIZE;
		currentPiece = pickARandomPiece();
		currentPiece.setXY(PIECE_X_START, PIECE_Y_START);

		// setting the coordinate and the next piece
		NEXTPIECE_X = (x_right + 25) + 30;
		NEXTPIECE_Y = y_top + 60;
		nextPiece = pickARandomPiece();
		nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);
	}

	private Block pickARandomPiece() {
		Block block = null;
		int x = new Random().nextInt(7);
		switch (x) {
		case 0:
			block = new O();
			break;
		case 1:
			block = new T();
			break;
		case 2:
			block = new I();
			break;
		case 3:
			block = new L();
			break;
		case 4:
			block = new J();
			break;
		case 5:
			block = new Z();
			break;
		case 6:
			block = new S();
			break;
		}
		return block;
	}

	public void update() {
		// this method works when the current piece is deactivated (on bottom collision)

		if (currentPiece.pieceActiveStatus == false) {
			fixedTiles.add(currentPiece.tile[0]);
			fixedTiles.add(currentPiece.tile[1]);
			fixedTiles.add(currentPiece.tile[2]);
			fixedTiles.add(currentPiece.tile[3]);

			// check if the game is over or not after each piece become deactivated

			if (currentPiece.tile[0].x == PIECE_X_START && currentPiece.tile[0].y == PIECE_Y_START) {
				gameOver = true;

				// game over sound
				GameView.music.play(2, false);
				SoundController.stopBGM();

				// setting high score after the game is over
				int newScore = score;
				if (newScore > HighScoreManager.loadHighScore()) {
					HighScoreManager.saveHighScore(newScore);
				}
			}

			currentPiece.deactivating = false;

			// replacing the current piece to the next piece
			currentPiece = nextPiece;
			currentPiece.setXY(PIECE_X_START, PIECE_Y_START);
			nextPiece = pickARandomPiece();
			nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);

			checkDeleteLine();

		} else {
			currentPiece.update();
		}
	}

	public void resetGame() {
		gameOver = false;
		fixedTiles.clear();
		level = 1;
		lines = 0;
		score = 0;
		currentPiece = pickARandomPiece();
		currentPiece.setXY(PIECE_X_START, PIECE_Y_START);
		nextPiece = pickARandomPiece();
		nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);
	}

	private void checkDeleteLine() {
		int x = x_left;
		int y = y_top;
		int blockCount = 0;

		while (x < x_right && y < y_bottom) {
			for (int i = 0; i < fixedTiles.size(); i++) {
				if (fixedTiles.get(i).x == x && fixedTiles.get(i).y == y) {
					blockCount++;
				}
			}

			x += Tile.SIZE;

			if (x == x_right) {

				if (blockCount == 12) {
					for (int i = fixedTiles.size() - 1; i >= 0; i--) {
						if (fixedTiles.get(i).y == y) {
							fixedTiles.remove(i);
						}
					}

					lines++;

					if (lines % 5 == 0 && pieceDropInterval > 5) {
						level++;
						pieceDropInterval -= 5;
					}

					score += (25 * level) * lines;

					// shift the line down
					for (int i = 0; i < fixedTiles.size(); i++) {
						if (fixedTiles.get(i).y < y) {
							fixedTiles.get(i).y += Tile.SIZE;
						}
					}

					GameView.bgm.play(1, false);
				}

				blockCount = 0;
				x = x_left;
				y += Tile.SIZE;
			}
		}
	}

	public void draw(Graphics2D g2) {
		// draw title
		g2.setColor(new Color(85, 37, 51));
		Font customFont_size20 = FontLoader.loadFont("PressStart2P.ttf", 20);
		g2.setFont(customFont_size20);
		g2.drawString("TETRIS", x_left + 128, y_top - 10);

		// grid line for next area
		int nextAreaX = x_right + 25;
		int nextAreaY = y_top;
		int nextAreaWidth = 120;
		int nextAreaHeight = 120;
		int gridSizeNext = 30;
		g2.setColor(new Color(50, 50, 50));
		g2.setStroke(new BasicStroke(0));
		for (int x = nextAreaX + gridSizeNext; x < nextAreaX + nextAreaWidth; x += gridSizeNext) {
			// Vertical lines in the next block area
			g2.drawLine(x, nextAreaY, x, nextAreaY + nextAreaHeight);
		}
		for (int y = nextAreaY + gridSizeNext; y < nextAreaY + nextAreaHeight; y += gridSizeNext) {
			// Horizontal lines in the next block area
			g2.drawLine(nextAreaX, y, nextAreaX + nextAreaWidth, y);
		}

		// play area border
		int borderWidth = 0;
		g2.setColor(new Color(50, 50, 50));
		g2.setStroke(new BasicStroke(borderWidth));
		g2.drawRect(x_left - borderWidth, y_top - borderWidth, PLAYAREA_WIDTH + 2 * borderWidth,
				PLAYAREA_HEIGHT + 2 * borderWidth);

		// next piece box area
		int x = x_right + 100;
		int y = y_bottom - 600;
		g2.drawRect(x_right + 25, y_top, 120, 120);
		g2.setColor(new Color(50, 50, 50));
		Font customFont_size10 = FontLoader.loadFont("PressStart2P.ttf", 10);
		g2.setFont(customFont_size10);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("NEXT", x_right + 35, y_top - 5);

		// draw score
		g2.drawRect(x_right + 25, y_top + 135, 120, 465);

		g2.drawString("SCORE", x_right + 35, y_top + 160);
		g2.drawString("" + score, x_right + 35, y_top + 180);

		g2.drawString("HIGH SCORE", x_right + 35, y_top + 220);
		g2.drawString("" + HighScoreManager.loadHighScore(), x_right + 35, y_top + 240);

		g2.drawString("LEVEL", x_right + 35, y_top + 280);
		g2.drawString("" + level, x_right + 35, y_top + 300);

		g2.drawString("LINE", x_right + 35, y_top + 340);
		g2.drawString("" + lines, x_right + 35, y_top + 360);

		// draw instructions and credit
		Font customFont_size6 = FontLoader.loadFont("PressStart2P.ttf", 6);
		g2.setFont(customFont_size6);
		g2.drawString("W OR ↑ - ROTATE", x_right + 35, y_top + 400);
		g2.drawString("D OR → - RIGHT", x_right + 35, y_top + 420);
		g2.drawString("S OR ↓ - DOWN", x_right + 35, y_top + 440);
		g2.drawString("A OR ← - LEFT", x_right + 35, y_top + 460);
		g2.drawString("SPACE - DROP", x_right + 35, y_top + 480);
		g2.drawString("P - PAUSE/PLAY", x_right + 35, y_top + 500);
		g2.drawString("R - RESET", x_right + 35, y_top + 520);
		g2.setColor(new Color(50, 50, 50));
		g2.drawString("© CREATED BY", x_right + 35, y_top + 580);
		g2.drawString("  WAHID SADIK", x_right + 35, y_top + 590);

		// draw the current piece
		if (currentPiece != null) {
			currentPiece.draw(g2);
		}

		// draw the next piece
		nextPiece.draw(g2);

		// draw the fixed tiles
		for (int i = 0; i < fixedTiles.size(); i++) {
			fixedTiles.get(i).draw(g2);
		}

		// draw enter
		if (KeyController.enterPressed == false) {
			g2.setColor(new Color(85, 37, 51));
			g2.fillRect(x_left, y_top + 270, 528, 90);
			g2.setColor(Color.gray);
			Font customFont_size17 = FontLoader.loadFont("PressStart2P.ttf", 17);
			g2.setFont(customFont_size17);
			x = x_left + 90;
			y = y_top + 325;
			g2.drawString("PRESS ENTER TO START", x, y);
		}

		// draw pause
		if (KeyController.pausePressed) {
			g2.setColor(new Color(233, 184, 36));
			g2.fillRect(x_left, y_top + 270, 528, 90);
			g2.setColor(Color.white);
			Font customFont_size35 = FontLoader.loadFont("PressStart2P.ttf", 35);
			g2.setFont(customFont_size35);
			x = x_left + 155;
			y = y_top + 335;
			g2.drawString("PAUSED", x, y);
		}

		// draw game over
		g2.setColor(Color.white);
		Font customFont_size40 = FontLoader.loadFont("PressStart2P.ttf", 40);
		g2.setFont(customFont_size40);
		if (gameOver) {
			g2.setColor(new Color(85, 37, 51));
			g2.fillRect(x_left, y_top + 270, 528, 90);
			g2.setColor(Color.white);
			Font customFont_size30 = FontLoader.loadFont("PressStart2P.ttf", 30);
			g2.setFont(customFont_size30);
			x = x_left + 117;
			y = y_top + 330;
			g2.drawString("GAME OVER!", x, y);
		}
	}
}