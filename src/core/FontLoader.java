package core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
	public static Font loadFont(String fontFileName, int fontSize) {
		try {
			InputStream fontStream = FontLoader.class.getResourceAsStream("/fonts/" + fontFileName);
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, fontSize);
			fontStream.close();
			return font;
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			return new Font("Arial", Font.PLAIN, fontSize);
		}
	}
}