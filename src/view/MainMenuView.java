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



public class MainMenuView extends JPanel{

  private Color gold = new Color(255,215,0);
 
  private JButton singlePlayerButton;
  private JButton multiPlayerButton;
  private JButton settingsButton;
  
  public MainMenuView() {
    super();
    createAndShow(); 
  }


  private void createAndShow() {
    setLayout(new GridBagLayout());
    setBackground(gold);
    setOpaque(true);

    //Title
    JLabel title = new JLabel("BOGGLE"); 
    title.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    //gbc.fill = GridBagConstraints.HORIZONTAL;
    add(title,gbc);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));

    //Single Player Button
    singlePlayerButton = new JButton("Single Player");

    singlePlayerButton.setMaximumSize(new Dimension(200,20));
    singlePlayerButton.setPreferredSize(new Dimension(200,20));

    //add(singlePlayerButton,gbc);
    buttonPanel.add(singlePlayerButton);


    //Multi Player Button
    multiPlayerButton = new JButton("Multi Player");
    multiPlayerButton.setMaximumSize(new Dimension(200,20));
    multiPlayerButton.setPreferredSize(new Dimension(200,20));
    buttonPanel.add(multiPlayerButton);

   
    //Settings Button
    settingsButton = new JButton("Settings");
    
    settingsButton.setMaximumSize(new Dimension(200,20));
    settingsButton.setPreferredSize(new Dimension(200,20));
    buttonPanel.add(settingsButton);
    
   
 
    gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    add(buttonPanel,gbc);


 
    }


    public JButton getSinglePlayerButton() {
        return singlePlayerButton;
    }

    public JButton getmultiPlayerButton() {
        return multiPlayerButton;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }


    public void addSinglePlayerButtonListener(ActionListener singlePlayerListener) 
    {
       singlePlayerButton.addActionListener(singlePlayerListener); 
    }

    
    public void addMultiPlayerButtonListener(ActionListener multiPlayerListener) 
    {
       multiPlayerButton.addActionListener(multiPlayerListener); 
    }


    public void addSettingsButtonListener(ActionListener settingsListener) 
    {
       settingsButton.addActionListener(settingsListener); 
    }



}
