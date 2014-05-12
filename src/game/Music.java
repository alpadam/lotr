package game;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Egy kis zenei al�fest�s
 * a j�t�k�lm�ny fokoz�sa �rdek�ben
 * 
 * A "Zene" felirat� gombbal kapcsolhat� ki-be
 */
public class Music {
	
	private static Clip clip;
	
	private static final Music INSTANCE = new Music();
	
	private Music() {
		try{
			clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File("lotr.wav")));
		}
		catch (Exception e){
			System.out.println("baj");
		}
	}
	
	public static Music getInstance() {
		return INSTANCE;
	}

	public static void musicPlay() {
		    try {
		        clip.start();
		        clip.loop(Clip.LOOP_CONTINUOUSLY);
		    } catch (Exception exc) {
		        exc.printStackTrace(System.out);
		    }
	}
	
	public static void musicStop() {
	    try {
	    	if(clip != null) {
	    		clip.stop();
	    	}
	    } catch (Exception exc){
	        exc.printStackTrace(System.out);
	    }
	}
	
}

