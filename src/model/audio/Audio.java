package model.audio;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
public class Audio implements Playable {
    private final int URGENCY = 100;    

    private String sourcePath;
    private String name;
    private Clip clip;
    private boolean muted;
    private boolean playing;
    private boolean looping;
    private boolean paused; //if the loop still exists but it has been muted
    private int loopPosition;
    private float volume;
    
    public Audio(String name,String sourcePath,float volume)
    {
        this.name = name;
        this.sourcePath = sourcePath;
        //create a Clip instance with sourcepath, set it to clip field
        newClip(); 
        this.volume = volume;
        setVolume(volume);
        muted = false; 
        paused = false;
        playing = false;
        looping = false;
        loopPosition = 1;
    }

    //play the clip one time through
    //should not be playing or looping
    public void play()
    {
        if (!muted) {
            if (playing) {
                System.out.println(name + " tried to play " + " but it is already playing ");
            } else if (looping) {
                System.out.println(name + " tried to play " + " but it is already looping" );
            } else {
                clip.setMicrosecondPosition(0);
                clip.start();
                clip.addLineListener(new LineListener(){
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            playing = false;
                            clip.flush();
                        }
                    }
                });
            }
        } else { System.out.println( name + " tried to play but it is muted :| ");}
    }


    //loop the clip until a mute event is called
    public void loop()
    {
        if (playing && !paused) { System.out.println(name + " tried to loop , but it's already playing");} 
        else if (looping && !muted && !paused) { System.out.println(name + " tried to start a new loop, but it's already looping and not muted");} 
        else {
            // I am unsure why , but If I try to loop the clip to close to playing it
            //even thought it's exited as indicated by the LineListener , it gets stuck
            //so I will just recreate the clip if a loop is needed
            newClip();
            looping = true;
            LoopThread loopThread = new LoopThread();
            loopThread.start(); 
        }
    }

    //@vol : a float in range [0,1]
    //@source : https://stackoverflow.com/questions/40514910/set-volume-of-java-clip
    //I'm unsatisfied with this setVolume , as .4f is barely audible
    //this will  require further investigation.
    public void setVolume(float vol)
    {
        if (vol < 0f || vol > 1f ) {
            throw new IllegalArgumentException(" Volume " + vol + " not valid, volume must be in range [0,1]");
        } else {
            volume = vol;
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
            
            

        }
        
    }

    public void mute()
    {
       muted = true;
    }

    public void unmute()
    {
        muted = false;
        if (looping) {
            loop();
            paused = false;
        }
    }

    private void newClip() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(sourcePath));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Error Creating Audio Instance");
            System.out.println(e.getStackTrace());
        }
    }

    //The loop thread will run while the Audio is set to loop
    //and the mute state is false
    //if mute state is true, then the loops position is stored
    //and will be restored upon unmuting
    private class LoopThread extends Thread {

        public void run() 
        {
            System.out.println("starting loop thread");
            clip.setFramePosition(loopPosition);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //loops should run until they are muted, or the object is explicitly told
            //to change state out of looping
            //variable just indicates to the Audio Class that
            //whether it is in a loop state or not 
            while (!muted && looping)
            {
                try {
                    sleep(URGENCY);
                } catch (Exception e) {
                    System.out.println(" Error making LoopThread sleep ");
                    e.printStackTrace();
                }
            }
            //when the loop stops store the the state of the clip
            loopPosition = clip.getFramePosition(); 
            //stop looping clip
            clip.loop(0);
            //clear the internal buffer
            clip.flush();
            clip.stop();

            if (muted) { 
                System.out.println("clip : " + name + " stopped looping , because it was muted");
                paused = true;
            }
            else if (!looping) { System.out.println("clip : " + name + " was instrcuted to stop looping");}
            else { System.out.println("clip : " + name + " stopped loop without instruction, something may be broken ");}
            
        }
    }

    



}
