package model.audio;
/*
    This event indicates to the listener to
    loop the audio clip associated with the 
*/
public class LoopClipEvent implements AudioEvent {

    private String sourceName;
    
    public LoopClipEvent(String sourceName) {
        this.sourceName = sourceName;
    }
    
    public String getSourceName() {
        return sourceName;
    }
    
    public String getEventDetails()
    {
        return "Looping Audio Clip " + sourceName;
    }
}
