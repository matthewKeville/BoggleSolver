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
import java.util.Arrays;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import board.*;
import solver.*;

//single player view will manage the playing of a single player 
//boggle game .

// words submitted to answer input field will be verified 
// for correctness and lifted into the AnswerView


public class MainMenuView extends JPanel{

  private Color gold = new Color(255,215,0);
 
  private JComboBox gameModeBox; 
  private JComboBox sizeBox;
  private JButton playButton;
  
  public MainMenuView() {
    super();
    createAndShow(); 
  }

  public JComboBox getGameModeBox() {
    return gameModeBox;
  }
    
  public JComboBox getSizeBox() {
    return sizeBox;
  }
    
  public JButton getPlayButton() {
    return playButton;
  }

  private void createAndShow() {
    setLayout(new GridBagLayout());
    setBackground(gold);
    setOpaque(true);
    //Game Mode Label
    JLabel gameModeLabel = new JLabel("Game Mode : "); 
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(gameModeLabel,gbc);

    //Game Mode Picker
    String[] gameModes = {"Classic","Redux","Links"};
    gameModeBox = new JComboBox(gameModes);
    gameModeBox.setSelectedIndex(0);
    gbc = new GridBagConstraints();
    gbc.gridx=1;
    gbc.gridy=0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(gameModeBox,gbc);

    
    //Size Label
    JLabel sizeLabel = new JLabel("Game Size : "); 
    gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(sizeLabel,gbc);

    //Size Picker
    String[] sizes = {"4","5","6","7","8","9","10"};
    sizeBox = new JComboBox(sizes);
    sizeBox.setSelectedIndex(0);
    gbc = new GridBagConstraints();
    gbc.gridx=1;
    gbc.gridy=1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(sizeBox,gbc);

    //Play Button
    playButton = new JButton("Play");
    gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=2;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(playButton,gbc);
    
    }

    public void addSizeBoxListener(ActionListener sizeListener) {
        sizeBox.addActionListener(sizeListener);
    }

    public void addGameModeBoxListener(ActionListener gameModeListener) {
        gameModeBox.addActionListener(gameModeListener);
    }

    public void addPlayButtonListener(ActionListener playListener) 
    {
       playButton.addActionListener(playListener); 
    }



}