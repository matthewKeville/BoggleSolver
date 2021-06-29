package model.audio;
/*
    This event indicates to the listener to
    play the audio clip associated with the 
*/
public class PlayClipEvent implements AudioEvent {

    private String sourceName;
    
    public PlayClipEvent(String sourceName) {
        this.sourceName = sourceName;
    }
    
    public String getSourceName() {
        return sourceName;
    }
    
    public String getEventDetails()
    {
        return " Playing Audio Clip " + sourceName;
    }
}
