package test;
import utility.*;
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
        in.fireAudioEvent(new AddAudioSourceEvent("validword",null,"src/res/sound/validwordlow.wav"));
        in.fireAudioEvent(new AddAudioSourceEvent("shake",musicGroup,"src/res/sound/shake.wav"));
        in.fireAudioEvent(new AddAudioSourceEvent("music",sfxGroup,"src/res/sound/gameMusic.wav"));
        in.fireAudioEvent(new AddAudioSourceEvent("piano",sfxGroup,"src/res/sound/sadpiano.wav"));
 
        //loop shake and valid run for 3 seconds
        in.fireAudioEvent(new LoopClipEvent("piano",1));
        in.fireAudioEvent(new LoopClipEvent("shake",1));
        //in.fireAudioEvent(new LoopClipEvent("validword",1));

        try {
        //stop shake loop after 3 seconds
        Thread.sleep(3000);
        in.fireAudioEvent(new MuteClipEvent("shake",true));
        //after another 3 seconds stop validword and start shake
        Thread.sleep(3000);
        //in.fireAudioEvent(new MuteClipEvent("validword",true));
        //in.fireAudioEvent(new MuteClipEvent("shake",false));
        in.fireAudioEvent(new MuteClipEvent("piano",true));

        Thread.sleep(5000);

                
        in.fireAudioEvent(new MuteClipEvent("piano",false));
        
        } catch (Exception e) {
            e.printStackTrace();
        }
                
    }

    public static void main(String[] args)
    {
        AudioTest audioTest = new AudioTest();
        
    }


}
