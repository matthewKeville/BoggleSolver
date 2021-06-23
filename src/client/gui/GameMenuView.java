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
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


import board.*;
import solver.*;

/* This class will hold a JTextField for submitting answers
   and a JLabel that indicates whether a word doesn't exist
   or if a word is already used
*/

public class GameMenuView extends JPanel {

  //private Color gold = new Color(255,215,0);  

  private JButton leftButton;
  private JButton rightButton;
  private JButton shakeButton;
  private JButton exitGameButton;


  public GameMenuView() {
    super();
    //this.setBackground(gold);
    this.setOpaque(false);
    this.setPreferredSize(new Dimension(400,50));
    this.setLayout(new GridBagLayout());
    create();
  }

  private void create() {

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);
    //rotate left button
    leftButton = new JButton("Rotate Left");
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx =1;
    gbc.weighty =1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    this.add(leftButton,gbc); 
    //rotate right
    rightButton = new JButton("Rotate Right");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx =1;
    gbc.weighty =1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    this.add(rightButton,gbc); 
    //shake
    shakeButton = new JButton("Shake");
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx =1;
    gbc.weighty =1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    this.add(shakeButton,gbc);
    //quit 
    exitGameButton = new JButton("Exit Game");
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 0;
    gbc.weightx =1;
    gbc.weighty =1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    this.add(exitGameButton,gbc);

  }

  public void addRotateLeftListener(ActionListener rotateListener) {
    leftButton.addActionListener(rotateListener);
  }


  public void addRotateRightListener(ActionListener rotateListener) {
    rightButton.addActionListener(rotateListener);
  }


  public void addShakeListener(ActionListener shakeListener) {
    shakeButton.addActionListener(shakeListener);
  }
    
  public void addExitGameListener(ActionListener quitListener) {
    exitGameButton.addActionListener(quitListener);
  }


}
