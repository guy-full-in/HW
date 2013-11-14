import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Alarm {
	public static void play() {
		try {
		    File soundFile = new File("src/1.wav");
		    
		    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
		    
		    Clip clip = AudioSystem.getClip();

		    clip.open(ais);

		    FloatControl vc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

		    vc.setValue(-35);
		    
		    clip.setFramePosition(0);
		    clip.start();

		    Thread.sleep(clip.getMicrosecondLength()/1000);
		    clip.stop();
		    clip.close();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
