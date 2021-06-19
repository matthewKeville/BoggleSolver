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

         
    
    boardView = new JPanel(new GridLayout(4,4));
    //boardView.setPreferredSize(new Dimension(40,40));
    boardView.setSize(new Dimension(20,20));
    boardView.setBackground(Color.cyan);


    //get a 4x4 boggle board
    BoardFactory bf = new BoardFactory();
    Board testBoard = bf.getInstance(4,"Classic");
    List<String> faces = testBoard.getFaces();
    for (int i = 0; i < faces.size(); i++) {
      Font labelFont = new Font("Serif",Font.PLAIN,20);

      JLabel label = new JLabel(faces.get(i));
      label.setPreferredSize(new Dimension(2,2));
      label.setForeground(Color.white);
      label.setBackground(Color.black);
      label.setOpaque(true);
      label.setFont(labelFont);
      Border lineBorder = BorderFactory.createLineBorder(Color.red);
      label.setBorder(lineBorder);
      label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      boardView.add(label); 
    }




    mainFrame.add(boardView);


    mainFrame.pack();
    mainFrame.setVisible(true);

  }


}
