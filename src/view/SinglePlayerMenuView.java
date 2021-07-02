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
import java.util.Arrays;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;



public class SinglePlayerMenuView extends JPanel{

  private Color gold = new Color(255,215,0);
 
  private JComboBox gameModeBox; 
  private JComboBox sizeBox;
  private JCheckBox timedBox;
  private JButton returnButton;
  
  public SinglePlayerMenuView() {
    super();
    createAndShow(); 
  }

  public JComboBox getGameModeBox() {
    return gameModeBox;
  }
    
  public JComboBox getSizeBox() {
    return sizeBox;
  }

  public JCheckBox getTimedBox() {
    return timedBox;
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

    //timed checkbox
    timedBox = new JCheckBox(" Timed ? ");
    timedBox.setSelected(true);
    gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=2;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(timedBox,gbc);

    //Play Button
    returnButton = new JButton("Return");
    gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=3;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(returnButton,gbc);
    
    }

    public void addSizeBoxListener(ActionListener sizeListener) {
        sizeBox.addActionListener(sizeListener);
    }

    public void addGameModeBoxListener(ActionListener gameModeListener) {
        gameModeBox.addActionListener(gameModeListener);
    }

    public void addTimedBoxListener(ActionListener timedListener) {
        timedBox.addActionListener(timedListener);
    }

    public void addReturnListener(ActionListener returnListener) 
    {
       returnButton.addActionListener(returnListener); 
    }



}
