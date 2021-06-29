package test;

import model.audio.*;
import controller.AudioPlayer;

import java.util.concurrent.TimeUnit;
/* This class tests if Audio Events 
   and Listeners are properly implemented
*/
public class AudioTest {

    public AudioTest() {
        runTest();
    }


    public class Initiator {

        private AudioEventListener ael;

        public Initiator() {

        } 

        public void addListener(AudioEventListener ael) {
            this.ael = ael;
        }
    
        public void fireAudioEvent(AudioEvent ae) {
            ael.fireAudioEvent(ae);
        }
    }


/*

    Known issues : A Long clip once stopped, does not resume properly
        //seems to be fixed : call to clip.close() killed the clip

    To be implemented Mute Groups and Mute Logic , and volume in the AudioPlayer 
    
    To be test : MuteGroups and there interplay with Mute
    


*/

    public void runTest() 
    {
        Initiator in = new Initiator();
        AudioPlayer ap = new AudioPlayer();

        String sfxGroup = "sfx";
        String musicGroup = "music";
        in.addListener(ap);

        in.fireAudioEvent(new AddAudioSourceEvent("validword",sfxGroup,"src/res/sound/validwordlow.wav",.5f));
        in.fireAudioEvent(new AddAudioSourceEvent("shake",sfxGroup,"src/res/sound/shake.wav",.5f));
        in.fireAudioEvent(new AddAudioSourceEvent("tech",musicGroup,"src/res/sound/gameMusic.wav",.5f));
        in.fireAudioEvent(new AddAudioSourceEvent("piano",musicGroup,"src/res/sound/sadpiano.wav",.5f));


 
        try {

        
        in.fireAudioEvent(new LoopClipEvent("shake"));

        
        //stop shake loop after 3 seconds
        Thread.sleep(3000);
        in.fireAudioEvent(new LoopClipEvent("tech"));
        //Thread.sleep(3000);
        //in.fireAudioEvent(new MuteClipEvent("piano",true));
        //Thread.sleep(4000);
        
        Thread.sleep(3000);
        in.fireAudioEvent(new LoopClipEvent("validword"));

        Thread.sleep(3000);
        in.fireAudioEvent(new MuteClipGroupEvent(sfxGroup,true));

        //in.fireAudioEvent(new MuteClipEvent("music",true));


        //Thread.sleep(4000);

        //in.fireAudioEvent(new VolumeClipEvent("music",1f));
        //in.fireAudioEvent(new PlayClipEvent("music"));
        //Thread.sleep(4000);                


        //in.fireAudioEvent(new VolumeClipEvent("music",.4f));


        //in.fireAudioEvent(new MuteClipEvent("music",false));
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        

                
    }

    public static void main(String[] args)
    {
        AudioTest audioTest = new AudioTest();
        
    }


}
