package utility;
/*
    Add Audio Source event notifies the listener
    to create a Clip from the given path
    and associate it with the name provided
    the sourceGroup parameter is used to 
    associate audio sources with each other
    so a MuteGroupClip event knows which Clips to mute

*/
public class AddAudioSourceEvent implements AudioEvent {
    private String sourceName;
    private String sourceGroup;
    private String sourcePath;
    
    public AddAudioSourceEvent(String sourceName,String sourceGroup,String sourcePath) {
        this.sourceName = sourceName;
        this.sourceGroup = sourceGroup;
        this.sourcePath = sourcePath;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public String getSourceGroup() {
        return sourceGroup;
    }
    
    public String getSourcePath() {
        return this.sourcePath;
    }
        

    public String getEventDetails() {
        String details =  " Adding Audio Source " + sourcePath + " named " + sourceName + " in group ";
        details += (sourceGroup == null)?"None":sourceGroup;
        return details;
    }
}
