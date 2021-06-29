package model.audio;
/*
    This event indidcates to the listener
    to set the volume to the desired float
*/
public class VolumeClipEvent implements AudioEvent {

    private String sourceName;
    private float volume;

    public VolumeClipEvent(String sourceName,float volume)
    {
        this.sourceName = sourceName;
        this.volume = volume;
    }

    public String getSourceName() {
        return sourceName;
    }

    public float getVolume() {
        return volume;
    }

    public String getEventDetails() 
    {
        return "Set : " + sourceName + " to volume " + volume;
   }
}
