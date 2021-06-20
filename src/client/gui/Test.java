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

import board.*;
import solver.*;

public class Boggle {

  private JFrame mainFrame;
  private JPanel boardView;
  private JPanel boardViewContainer;

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
    //with ubuntu window manager, setSize doesn't work but Preferred does ...
    mainFrame.getContentPane().setPreferredSize(new Dimension(500,500));
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setBackground(Color.BLACK);

    //create board view in future, board should be supplied to constructor
    //for purposes now, it is in BoardView
    BoardView bv = new BoardView();

    mainFrame.add(bv);
    mainFrame.pack();
    mainFrame.setVisible(true);

  }


}
