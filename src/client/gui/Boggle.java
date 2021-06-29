package client.gui;

//////////////////////
import controller.SinglePlayerController;
import controller.MainMenuController;
import controller.BoggleController;
import controller.AudioPlayer;
import view.SinglePlayerView;
import view.MainMenuView;
import view.BoggleView;
import model.BoggleModel;
import model.SinglePlayerModel;
import model.MainMenuModel;
//Create all the MVC's
public class Boggle {

    private BoggleView bv;
    private BoggleModel bm;
    private BoggleController bc;

    private SinglePlayerView spv;
    private SinglePlayerController spc;
    private SinglePlayerModel spm;

    private MainMenuModel mmm;
    private MainMenuView mmv;
    private MainMenuController mmc;

    private AudioPlayer audioPlayer;

    public Boggle() {
        //create Audio Player
        audioPlayer = new AudioPlayer(); 

        spm = new SinglePlayerModel();
        spv = new SinglePlayerView();  
        spc = new SinglePlayerController(spv,spm);
        
        spc.addAudioListener(audioPlayer);
        spc.addAllAudio();

        //shoud have Main Menu Model
        mmv = new MainMenuView();
        mmm = new MainMenuModel();
        mmc = new MainMenuController(mmm,mmv);

        bm = new BoggleModel();
        bv = new BoggleView(mmv,spv);
        bc = new BoggleController(bv,mmc,spc);

    }

    public static void main(String[] args) {
        Boggle boggle = new Boggle();
    }

}
