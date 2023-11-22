package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HighScoreManager {
	private static final String HIGH_SCORE_FILE = "tetris_highscore.txt";

	public static void saveHighScore(int score) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
			writer.println(score);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int loadHighScore() {
		int highScore = 0;
		File file = new File(HIGH_SCORE_FILE);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			Scanner scanner = new Scanner(file);
			if (scanner.hasNextInt()) {
				highScore = scanner.nextInt();
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return highScore;
	}

}
