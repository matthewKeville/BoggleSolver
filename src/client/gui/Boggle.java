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
    //default layout manager is BorderLayout and it makes components take up
    // all the space : ( 
    
    //with ubuntu window manager, setSize doesn't work but Preferred does ...
    mainFrame.setSize(800,600);
    //mainFrame.setSize(1600,1200);
    mainFrame.getContentPane().setPreferredSize(new Dimension(800,600));
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setBackground(Color.BLACK);

    //create board view in future, board should be supplied to constructor
    //for purposes now, it is in BoardView
    BoardView bv = new BoardView();
    AnswerView av = new AnswerView();
    AnswerInputPanel aip = new AnswerInputPanel();

    //Note when using gbl , components can't span multiple rows unless
    //the component is centered in the top left of the grid , (its a bug)

    mainFrame.setLayout(new GridBagLayout());
    GridBagConstraints c1 = new GridBagConstraints();
    c1.gridx = 0;
    c1.gridy = 0;
    c1.anchor = GridBagConstraints.FIRST_LINE_END;
    //c1.weightx = .5;
    //c1.weighty = .8;
    mainFrame.add(bv,c1);
    GridBagConstraints c2 = new GridBagConstraints();
    c2.gridx = 1;
    c2.gridy = 0;
    c2.anchor = GridBagConstraints.FIRST_LINE_START;
    //c2.weightx = .5;
    //c2.weighty = 1;
    mainFrame.add(av,c2);

    GridBagConstraints c3 = new GridBagConstraints();
    c3.gridx = 0;
    c3.gridy = 1;
    //c3.weightx = .5;
    //c3.weighty = .2;
    c3.anchor = GridBagConstraints.FIRST_LINE_START;
    //c3.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(aip,c3);

    /*
    mainFrame.setLayout(new FlowLayout());
    mainFrame.add(bv);
    mainFrame.add(av);
    mainFrame.add(aip);
    */

    mainFrame.pack();
    mainFrame.setVisible(true);

  }


}
