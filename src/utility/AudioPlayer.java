package utility;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
public class AudioPlayer {


    private Map<String,Clip> audioClips;
    //private List<AudioInputStream> streams;  
 
    //@param filePathMap - a map of sound names and the filePath
    // to that sound 
    public AudioPlayer(Map<String,String> filePathMap)
    {
        audioClips = new HashMap();
        try {
            for (String name : filePathMap.keySet())
            {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePathMap.get(name)));
                Clip c = AudioSystem.getClip();
                c.open(ais);
                audioClips.put(name,c);  
            }

        } catch (Exception e) {
            System.out.println("Error Creating AudioPlayer");
        }
    } 

    public void play(String name) {
        Clip c = audioClips.get(name);
        c.setMicrosecondPosition(0);
        c.start(); 
    }

}
