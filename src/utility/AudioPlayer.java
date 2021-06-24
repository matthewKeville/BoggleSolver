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

    //I need to make the thread sleep to stay alive
    private final int LOOPURGENCY = Integer.MAX_VALUE;
    private Map<String,Clip> audioClips;
    private Map<String,LoopThread> loopThreads;
    // Associate clip names with groups
    private Map<String,List<String>> audioGroups;
    //store mute state of clips
    private Map<String,Boolean> audioMute;
 
    public AudioPlayer()
    {
        audioClips = new HashMap();
        loopThreads = new HashMap();
        audioGroups = new HashMap();
        audioMute = new HashMap();
    } 

    public void fireAudioEvent(AudioEvent e) {
        //add audio source
        if ( e instanceof AddAudioSourceEvent) {
            System.out.println(e.getEventDetails());
            AddAudioSourceEvent aase = (AddAudioSourceEvent) e;
            addAudioSource(aase.getSourceName(),aase.getSourcePath());

            
            //set mute state to default true 
            audioMute.put(aase.getSourceName(),false);
            System.out.println("audio mute group updated ");
            System.out.println(audioMute);

            //Add clip to group if exist
        
            //if the audio source is belonging to a group
            //check if the group already exists, if it does
            //add it to the group, otherwise construct it
            String group = aase.getSourceGroup();
            if (group != null) {
                //group exists 
                if (audioGroups.containsKey(group)) {
                    List<String> groupList = audioGroups.get(group); 
                    groupList.add(group);
                    audioGroups.put(group,groupList); 
                //group doesn't exist
                } else {
                    List<String> sources = new ArrayList();
                    sources.add(aase.getSourceName());
                    audioGroups.put(group,sources);
                }
                System.out.println("Groups updated");
                System.out.println(audioGroups);
            }
        
        //play audio source
        } else if (e instanceof PlayClipEvent) {
            System.out.println(e.getEventDetails());
            PlayClipEvent pce = (PlayClipEvent) e;
            play(pce.getSourceName(),pce.getVolume());

        //Loop audio source 
        } else if (e instanceof LoopClipEvent) {
            System.out.println(e.getEventDetails());
            LoopClipEvent lce = (LoopClipEvent) e;
            loop(lce.getSourceName(),lce.getVolume()); 

        //Mute a certain clip
        } else if (e instanceof MuteClipEvent) {
            System.out.println(e.getEventDetails());
            MuteClipEvent mce = (MuteClipEvent) e;
            toggleMute(mce.getSourceName(),mce.isMute());

        //Mute a clip group
        } else if (e instanceof MuteClipGroupEvent) {
            System.out.println(e.getEventDetails());
            MuteClipGroupEvent mcge = (MuteClipGroupEvent) e;
        } 
    }

    //create a clip from the source path and add it to the audioClips map
    //indexed by sourceName
    private void addAudioSource(String sourceName,String sourcePath) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(sourcePath));
            Clip c = AudioSystem.getClip();
            c.open(ais);
            audioClips.put(sourceName,c);  
        } catch (Exception e) {
            System.out.println("Error creating clip for " + sourceName);
        }
    }

    //play the clip that belongs to name in the 
    //audioClips map
    private void play(String name,int volume) {
        if (audioClips.containsKey(name)) {
            Clip c = audioClips.get(name);
            c.setMicrosecondPosition(0);
            c.start(); 
        // source name doesn't exist
        } else {
            String errResponse = "Audio Player Recieved Play Event for ";
            errResponse += name + " but it doesn't exist";
            System.err.println(errResponse);
        }
    }

    //loop the audio clip belonging to name
    private void loop(String name,int volume) {
        Clip c = audioClips.get(name);
        LoopThread lt = new LoopThread(name,c,LOOPURGENCY,0);
        lt.start();
        System.out.println(lt.toString());
        loopThreads.put(name,lt);
    }


    //////////////////
    //////////////////

    // mute or unmute the given clip name
    // if the clip is looping and its to be muted
    // pause the thread otherwise unpause the
    // the thread 
    private void toggleMute(String name,boolean isMute) {
        //change clip mute state
        audioMute.put(name,isMute); 
        System.out.println("Audio Mute Groups Updated");
        System.out.println(audioMute);       

 
        //is the clip looping?
        if (loopThreads.containsKey(name)) {
            if (isMute) {
                System.out.println(" pausing " + name);
                loopThreads.get(name).pauseLoop();
                
            } else {
                //start a new LoopThread from the state of 
                //the previously terminated thread
                System.out.println(" resuming " + name);
                LoopThread lt = loopThreads.get(name);
                lt = new LoopThread(lt.getClipName(),lt.getClip(),LOOPURGENCY,lt.getLastPosition());
                lt.start(); 
                loopThreads.put(name,lt);
            }
            //ad hoc test 
            System.out.println(loopThreads.get(name).toString());
        }
    }

    public class LoopThread extends Thread {
        private boolean looping;
        private String name;
        private Clip c;
        //save the state of the clip if it is
        //paused
        private int position;
        //how long can the thread wait until it runs again
        private int urgency;

        public LoopThread(String name,Clip c,int urgency,int position) {
            super();
            this.name = name;
            this.c = c;
            this.urgency = urgency;
            this.position = position;
            this.looping = true;
        }

        public void run() {
            c.setFramePosition(position);
            //c.setMicrosecondPosition(position);
            c.loop(Clip.LOOP_CONTINUOUSLY);
            while(looping) {
                try {
                    sleep(urgency); // I am unsure if the thread needs to sleep at all
                                    // I have to read into the Audio System more ...
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }

        //pause the thread, pause the loop 
        public void pauseLoop() {
            looping = false;
            this.position = c.getFramePosition();

            c.loop(0);
            c.flush();
            c.stop();
                       
            System.out.println("Stopping Loop for " + this.name);
            System.out.println("Saved state at " + this.position);
        }

        public int getLastPosition() {
            return this.position;
        }

        public String getClipName() {
            return this.name;
        }

        public Clip getClip() {
            return this.c;
        }
       
        public String toString() {
            return " Name : " + this.name + " position " + this.position + " looping " + looping;
        }

    } 


}
