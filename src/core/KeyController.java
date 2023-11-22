package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import blocks.Block;

public class KeyController implements KeyListener {
	Block m = new Block();
	PlayController pm = new PlayController();

	public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, spacePressed, enterPressed,
			rPressed;
	int enterCount = 0;
	int rCount = 0;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			if (pausePressed) {
				pausePressed = false;
				GameView.bgm.play(0, true);
				GameView.bgm.loopBGM();
			} else {
				pausePressed = true;
				SoundController.stopBGM();
			}
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}

		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;

			// prevent playing the music more then one time
			enterCount++;
			if (enterCount == 1) {
				GameView.music.play(0, true);
				GameView.music.loopBGM();
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			rPressed = true;
			pm.resetGame();
			SoundController.stopBGM();
			GameView.music.play(0, true);
			GameView.music.loopBGM();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
