package client.gui;
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

import board.*;
import solver.*;

public class Boggle {

  private JFrame mainFrame;

  public Boggle() {
    createAndShow(); 
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Boggle b = new Boggle(); 
      }
    }); 
  }


  private void createAndShow() {
    mainFrame = new JFrame("Boggle");
    //default layout manager is BorderLayout and it makes components take up
    // all the space : ( 
    
    //with ubuntu window manager, setSize doesn't work but Preferred does ...
    mainFrame.setSize(800,600);
    mainFrame.getContentPane().setPreferredSize(new Dimension(800,600));
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setBackground(Color.BLACK);
    mainFrame.setLayout(new FlowLayout());

    //SinglePlayerView
    SinglePlayerView spv = new SinglePlayerView();
    //SinglePlayerController
    SinglePlayerController spc = new SinglePlayerController(spv);

    //add to main frame
    mainFrame.add(spv);
    mainFrame.pack();
    mainFrame.setVisible(true);

  }


}
