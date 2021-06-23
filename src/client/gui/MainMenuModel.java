package client.gui;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

//Listens for changes to the model 
//and applys them to the views

public class MainMenuModel {

  private PropertyChangeSupport support;

  private int size = 4;
  private String gameMode;
  private boolean active;

  public MainMenuModel() {
    support = new PropertyChangeSupport(this);    
    active = false; 
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

  //pcl interface requirements
  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    support.addPropertyChangeListener(pcl);
  }  
  public void removePropertyChangeListener(PropertyChangeListener pcl) {
    support.removePropertyChangeListener(pcl);
  }

  
}
