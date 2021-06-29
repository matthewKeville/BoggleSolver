package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

//Listens for changes to the model 
//and applys them to the views

public class MainMenuModel {

  private PropertyChangeSupport support;

  /* there is nothing enforcing the default instantiation
     of the mainMenuModel the the mainMenuView
     they only appear consistent because they have the same 
     default values, this is bad design
  */

  private int size = 4;
  private String gameMode;
  private boolean active; //what is active? the main menu or singleplayer?
  private boolean timed;

  public MainMenuModel() {
    support = new PropertyChangeSupport(this);    
    active = false; 
    timed = true;
  } 

  public int getSize() {
    return this.size;
  }

  public String getGameMode() {
    return this.gameMode;
  }

  public PropertyChangeSupport getSupport(){
    return this.support;
  }

  public void setActive(boolean x) {
    this.active = x;
  }

  public void setSize(int newSize) {
    this.size = newSize;
  }

  public void setGameMode(String newGameMode) {
    this.gameMode = newGameMode;
  }

 public void setTimed(boolean x) {
    this.timed = x;
 }

  //this should be swapped to model change listeners

  //pcl interface requirements
  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    support.addPropertyChangeListener(pcl);
  }  
  public void removePropertyChangeListener(PropertyChangeListener pcl) {
    support.removePropertyChangeListener(pcl);
  }

  
}
