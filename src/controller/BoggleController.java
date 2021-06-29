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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import view.BoggleView;
import controller.SinglePlayerController;
import controller.MainMenuController;

//Listens for changes set from the main menu controller
//then propagate them to the other game controllers
public class BoggleController implements PropertyChangeListener {

  BoggleView bv;
  SinglePlayerController spc; 
  MainMenuController mmc;
  
 
  public BoggleController(BoggleView bv,MainMenuController mmc,SinglePlayerController spc) {
    this.bv = bv;
    this.spc = spc;
    this.mmc = mmc;
    
    mmc.getMainMenuModel().addPropertyChangeListener(this);  //listen main menu model changes
    spc.getSinglePlayerModel().addPropertyChangeListener(this); //listen to single player model changes
  }

  public void propertyChange(PropertyChangeEvent evt) {
    //change the set size in the SinglePlayerGameControllea
    System.out.println("BC revieved event");
    //System.out.println(evt.toString());

    if (evt.getPropertyName().equals("size")) {
        spc.setBoardSize((Integer)evt.getNewValue()); 
    }

    //change game mode
    if (evt.getPropertyName().equals("gameMode")) {
        spc.setGameMode((String) evt.getNewValue());
    }

    // mainmenu is no longer the active panel
    if (evt.getPropertyName().equals("mainMenuActive")) {
        //switch card
        ((CardLayout) bv.getContentPane().getLayout()).show(bv.getContentPane(),BoggleView.SINGLEPLAYERPANEL); 

    }    
    
    // singleplayer is no longer the active panel
    if (evt.getPropertyName().equals("singlePlayerActive")) {
        //switch card
        ((CardLayout) bv.getContentPane().getLayout()).show(bv.getContentPane(),BoggleView.MAINMENUPANEL); 
    }

        
    
 
  }

  
}
