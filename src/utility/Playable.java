package utility;
/*
    This interface is an abstraction of an audio signal

*/
public interface Playable {

    //Play the playable one time
    public void play();
   
    //Continuously loop the playable 
    public void loop();

    //@vol : linear volume index from a 
    //[0,1]
    public void setVolume(float vol);

    public void mute();
    
    public void unmute();




}
