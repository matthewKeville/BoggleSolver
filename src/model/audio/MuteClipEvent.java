package model.audio;
/*
    This event indidcates to the listener
    to mute the audio with the associated name    
*/
public class MuteClipEvent implements AudioEvent {

    private String sourceName;
    private boolean mute;

    public MuteClipEvent(String sourceName,boolean mute)
    {
        this.sourceName = sourceName;
        this.mute = mute;  
    }

    public String getSourceName() {
        return sourceName;
    }

    public boolean isMute() {
        return mute;
    }

    public String getEventDetails() 
    {
        return "Mute : " + sourceName + " = " + mute;
   }
}
