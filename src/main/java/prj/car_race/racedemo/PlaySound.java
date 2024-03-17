package prj.car_race.racedemo;

import java.io.File;
import javax.sound.sampled.*;
public class PlaySound {

    private Clip clip;

    void playSound() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(".\\shanghai-formula-1-grand-prix.wav")));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopSound(){
        if(clip!=null)
            clip.stop();
    }
    
    public static void main(String[] args) throws InterruptedException {
        PlaySound ps = new PlaySound();
        ps.playSound();
        Thread.sleep(15000);
        ps.stopSound();
    }
}
