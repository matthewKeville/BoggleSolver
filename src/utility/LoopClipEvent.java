package utility;
/*
    This event indicates to the listener to
    loop the audio clip associated with the 
    audioName at the desired volume indefinitely
*/
public class LoopClipEvent implements AudioEvent {

    private String sourceName;
    private int volume;
    
    public LoopClipEvent(String sourceName, int volume) {
        this.sourceName = sourceName;
        this.volume = volume;
    }
    
    public String getSourceName() {
        return sourceName;
    }
    
    public int getVolume() {
        return volume;
    }

    public String getEventDetails()
    {
        return "Looping Audio Clip " + sourceName + " at volume " + volume;
    }
}
