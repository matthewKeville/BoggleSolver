package utility;
/*
    This event indicates to the listener to
    play the audio clip associated with the 
    audioName at the desired volume
*/
public class PlayClipEvent implements AudioEvent {

    private String sourceName;
    private int volume;
    
    public PlayClipEvent(String sourceName, int volume) {
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
        return " Playing Audio Clip " + sourceName + " at volume " + volume;
    }
}
