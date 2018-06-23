package modelo;

import javax.sound.sampled.Clip;

public class MusicThread implements Runnable{
	public Boolean music = false;
	public Clip clip;
	
	public MusicThread(Clip clip){
		this.clip = clip;
		//this.listener = listener;
		//listener.addRunnable(this);
		
		//startStop.addKeyListener(listener);
	}
	public void music(){
		if (this.music){
			this.music = false;
			clip.stop();
		} else {
			this.music = true;
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
    }
	
	public void run() {
		
	}
}
