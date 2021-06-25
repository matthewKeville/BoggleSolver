package utility;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class AudioPlayer implements AudioEventListener {

    //Store the audios by name
    private Map<String,Audio> audioMap;
    //associate Audios by group name
    private Map<String,List<String>> audioGroupsMap;
    public AudioPlayer()
    {
        audioMap = new HashMap();
        audioGroupsMap = new HashMap();
    } 

    public void fireAudioEvent(AudioEvent e) {
        
        //add audio source
        if ( e instanceof AddAudioSourceEvent) {
            AddAudioSourceEvent aase = (AddAudioSourceEvent) e;
            System.out.println(aase.getEventDetails());
            
            //create an Audio Object
            //Default mute state is false
            //Default playing state is false
            Audio audio = new Audio(aase.getSourceName(),aase.getSourcePath(),aase.getVolume());            
            //add it to the audio map
            audioMap.put(aase.getSourceName(),audio);           
 
            //if the audio source is belonging to a group
            //if so add it to the group map
            String group = aase.getSourceGroup();
            if (group != null) {
                //group exists 
                if (audioGroupsMap.containsKey(group)) {
                    List<String> groupList = audioGroupsMap.get(group); 
                    groupList.add(aase.getSourceName());
                    audioGroupsMap.put(group,groupList); 
                //group doesn't exist
                } else {
                    List<String> sources = new ArrayList();
                    sources.add(aase.getSourceName());
                    audioGroupsMap.put(group,sources);
                }
                System.out.println("Groups updated");
                System.out.println(audioGroupsMap);
            }
        
        //play audio source
        } else if (e instanceof PlayClipEvent) {
            System.out.println(e.getEventDetails());
            PlayClipEvent pce = (PlayClipEvent) e;
            String sourceName = pce.getSourceName();
            Audio audio = audioMap.get(sourceName);
            audio.play(); 

        //Loop audio source 
        } else if (e instanceof LoopClipEvent) {
            System.out.println(e.getEventDetails());
            LoopClipEvent lce = (LoopClipEvent) e;
            String sourceName = lce.getSourceName();
            Audio audio = audioMap.get(sourceName);
            audio.loop();

        } else if ( e instanceof VolumeClipEvent) {
            System.out.println(e.getEventDetails());
            VolumeClipEvent vce = (VolumeClipEvent) e;
            String sourceName = vce.getSourceName();
            Audio audio = audioMap.get(sourceName);
            audio.setVolume(vce.getVolume()); 

        //Mute a certain clip
        } else if (e instanceof MuteClipEvent) {
            System.out.println(e.getEventDetails());
            MuteClipEvent mce = (MuteClipEvent) e;
            String sourceName = mce.getSourceName();
            Audio audio = audioMap.get(sourceName);
            if (mce.isMute()) {
                audio.mute();
            } else {
                audio.unmute();
            }

        //Mute an audio group
        //for each clip in the given group , mute them all 
        } else if (e instanceof MuteClipGroupEvent) {
            System.out.println(e.getEventDetails());
            MuteClipGroupEvent mcge = (MuteClipGroupEvent) e;
            String groupName = mcge.getSourceGroup();
            if (groupName != null && audioGroupsMap.containsKey(groupName)) {
                System.out.println("muting clip group : "  + groupName);
                //for each name in the group set 
                //retrieve the corresponding audio and 
                //and call mute or unmute depending on isMute
                List<String> sourceNames = audioGroupsMap.get(groupName);
                for (String audioName : sourceNames) 
                {
                    System.out.println("    muting : " + audioName);
                    Audio audio = audioMap.get(audioName);
                    if (audio == null) {
                        System.out.println(" you are trying to mute " + audioName + " but it is null ");
                    }
                    if ( mcge.isMute() ) {
                        audio.mute();
                    } else {
                        audio.unmute(); 
                    }
                }
            
            } else 
            {   
                System.out.println("tried mute group " + groupName + " but it doesn't exist");
            }

        } 
    }

}
