package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameView extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int WINDOW_WIDTH = 530;
	public static final int WINDOW_HEIGHT = 646;
	final int FPS = 60;
	Thread thread;
	PlayController playcontroller;
	public static SoundController music = new SoundController();
	public static SoundController bgm = new SoundController();


	GameView() {

		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);

		// implement key listener
		this.addKeyListener(new KeyController());
		this.setFocusable(true);

		playcontroller = new PlayController();
	}

	public void launchGame() {
		thread = new Thread(this);
		thread.start();
	}

	private void update() {

		if (KeyController.rPressed == true) {
			playcontroller.resetGame();
			KeyController.rPressed = false;
		}

		if (!KeyController.pausePressed && !playcontroller.gameOver && KeyController.enterPressed) {
			playcontroller.update();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		playcontroller.draw(g2);
	}

	@Override
	public void run() {
		// game loop implementation
		double delta = 0;
		double drawInterval = 1000000000 / FPS;
		long lastTime = System.nanoTime();
		long currentTime;

		while (thread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
}
