package model.audio;
/*
    This event indidcates to the listener
    to mute the audio with the associated name    
*/
public class MuteClipGroupEvent implements AudioEvent {

    private String sourceGroup;
    private boolean mute;

    public MuteClipGroupEvent(String sourceGroup,boolean mute)
    {
        this.sourceGroup = sourceGroup;
        this.mute = mute;  
    }

    public String getSourceGroup() {
        return sourceGroup;
    }

    public boolean isMute() {
        return mute;
    }

    public String getEventDetails() 
    {
        return "Mute Group : " + sourceGroup + " = " + mute;
   }
}
