package core;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

public class SoundController {
	static Clip bgmClip;
	URL sound[] = new URL[10];

	public SoundController() {
		sound[0] = getClass().getResource("/bgm.wav");
		sound[1] = getClass().getResource("/line.wav");
		sound[2] = getClass().getResource("/gameover.wav");
		sound[3] = getClass().getResource("/rotation.wav");
		sound[4] = getClass().getResource("/floor.wav");
	}

	public void play(int x, boolean bgm) {
		try {
			AudioInputStream audioinputstream = AudioSystem.getAudioInputStream(sound[x]);
			Clip clip = AudioSystem.getClip();

			if (bgm) {
				bgmClip = clip;
			}

			clip.open(audioinputstream);
			clip.addLineListener(new LineListener() {

				@Override
				public void update(LineEvent event) {
					if (event.getType() == Type.STOP) {
						clip.close();
					}
				}
			});
			audioinputstream.close();
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loopBGM() {
		bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
		FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-17.0f); // Reduce volume by 17 decibels.
	}

	public static void stopBGM() {
		bgmClip.stop();
		bgmClip.close();
	}
}
