package controller;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import view.MainMenuView;
import model.MainMenuModel;

//Listens for changes to the model 
//and applys them to the views

public class MainMenuController {

  private String gameMode;
  private MainMenuView mmv;
  private MainMenuModel mmm;
  

  public MainMenuController(MainMenuModel mmm, MainMenuView mmv) {
    super();
    this.mmm = mmm;
    this.mmv = mmv;

    //what to do when active sizeBox index changes
    mmv.addSizeBoxListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            int newSize = Integer.parseInt(((String) mmv.getSizeBox().getSelectedItem()));
            int oldSize = mmm.getSize();
            mmm.setSize(newSize);
            mmm.getSupport().firePropertyChange("size",oldSize,newSize);
            System.out.println("Game Size index changed : new size " + newSize);
        }
    });



    //what to do when active gameMode Index Changes
    mmv.addGameModeBoxListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String newGameMode = (String)  mmv.getGameModeBox().getSelectedItem();
            String oldGameMode = mmm.getGameMode();
            mmm.setGameMode(newGameMode);
            mmm.getSupport().firePropertyChange("gameMode",oldGameMode,newGameMode);
            System.out.println("Game Mode Changed " + newGameMode);
        }
    });

    //what to do when play button is clicked
    mmv.addPlayButtonListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mmm.setActive(false);
            mmm.getSupport().firePropertyChange("mainMenuActive",false,true);
            System.out.println("Switched to SinglePlayer View");
        }
    });

    //what to do when timed checkbox changes
    mmv.addTimedBoxListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            boolean newTimed = mmv.getTimedBox().isSelected();
            if (newTimed) {
                //tell SinglePlayerController
                System.out.println(" timed is true ");
            } else {
                //tell SinglePlayerController
                System.out.println(" timed is false ");
            }
            mmm.setTimed(newTimed);
            mmm.getSupport().firePropertyChange("timed",!newTimed,newTimed);
        }
    });


  }

  public void setActive(boolean x) {
    mmm.setActive(x);
  }

  public MainMenuModel getMainMenuModel() {
    return mmm;
  }

  
}
