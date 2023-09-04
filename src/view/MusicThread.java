package view;

import javax.sound.sampled.Clip;

public class MusicThread implements Runnable{
	public Boolean isMusicPlaying = false;
	public Clip clip;
	
	public MusicThread(Clip clip){
		this.clip = clip;
	}
	public void toggleMusic(){
		if (this.isMusicPlaying){
			this.isMusicPlaying = false;
			clip.stop();
		} else {
			this.isMusicPlaying = true;
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
    }
	
	public void run() {
		
	}
}
