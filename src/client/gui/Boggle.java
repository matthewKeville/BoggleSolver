package client.gui;
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

    public Boggle() {
        spm = new SinglePlayerModel();
        spv = new SinglePlayerView();  
        spc = new SinglePlayerController(spv,spm);
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
