package view;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

//This import seems perverse,
//the client should be the entry
import client.gui.Boggle;


//Top Level View Container
//Attatches Sub views to the JFrame

public class BoggleView extends JFrame {

  public final static String MAINMENUPANEL = "Main Menu";
  public final static String SINGLEPLAYERPANEL = "Single Player";

  //Sub Views
  private SinglePlayerView spv;
  private MainMenuView mmv;

  public BoggleView(MainMenuView mmv,SinglePlayerView spv) {
    super("Boggle");
    this.mmv = mmv;
    this.spv = spv;
    createAndShow(); 
  }


  
   
  public SinglePlayerView getSinglePlayerView() {
    return spv;
  }
    
  public MainMenuView getMainMenuView() {
    return mmv;
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

    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Boggle b = new Boggle(); 
      }
    }); 
  }


}
