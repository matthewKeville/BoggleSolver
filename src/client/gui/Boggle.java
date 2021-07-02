package client.gui;
//////////////////////
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;

//////////////////////
import controller.SinglePlayerController;
import controller.AudioPlayer;
import view.SinglePlayerView;
import view.MainMenuView;
import model.SinglePlayerModel;
public class Boggle extends JFrame {


    private SinglePlayerView spv;
    private SinglePlayerController spc;
    private SinglePlayerModel spm;
    private MainMenuView mmv;
    private AudioPlayer audioPlayer;

    private static final String MAINMENUPANEL = "main_menu";
    private static final String SINGLEPLAYERPANEL = "single_player";

    public Boggle() {
        super("Boggle");

        //create Audio Player
        audioPlayer = new AudioPlayer(); 
    
        //create single player
        spm = new SinglePlayerModel();
        spv = new SinglePlayerView();  
        spc = new SinglePlayerController(spv,spm);
        
        spc.addAudioListener(audioPlayer);
        spc.addAllAudio();

        //create main menu
        mmv = new MainMenuView();

        //setup JFrame settings
        createAndShow();

        //handle events 
        setStrategy();


    }

    private void createAndShow() {
        setSize(800,600);
        getContentPane().setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        //Card Layout used to switch between SinglePlayerGameView
        //and MainMenuView
        setLayout(new CardLayout());
        //Main Menu View
        add(mmv,MAINMENUPANEL);  
        //SinglePlayerView
        add(spv,SINGLEPLAYERPANEL);
        pack();
        setVisible(true);
  }

    private void setStrategy() {
        mmv.addSinglePlayerButtonListener( new ActionListener() 
        {   
            public void actionPerformed(ActionEvent e)  
            {
                //switch to singleplayerview
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(),SINGLEPLAYERPANEL); 
            }
            //((CardLayout) bv.getContentPane().getLayout()).show(bv.getContentPane(),BoggleView.SINGLEPLAYERPANEL); 
        }); 


        spv.getGameView().getGameMenuView().addExitGameListener( new ActionListener() 
        {   
            public void actionPerformed(ActionEvent e)  
            {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(),MAINMENUPANEL); 
            }
        }); 

    }
 
 
    public static void main(String[] args) {
        Boggle boggle = new Boggle();
    }

}
