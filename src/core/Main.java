package core;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GameView gameview = new GameView();

		frame.add(gameview);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		gameview.launchGame();
	}
}