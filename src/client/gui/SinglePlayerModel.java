package client.gui;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import board.*;
import solver.*;

//The SinglePlayerModel is an aggregate model that 
//models the BoardView, AnswerView, AnswerInputView, and GameMenuViews
//Likewise the SinglePlayerController is responsible for listening
//to changes in the model and the views

public class SinglePlayerModel{

  private PropertyChangeSupport support;

  //BoardView sub model Components

  private int size;
  private String gameMode;

  private BoardFactory boardFactory;
  private Board board;

  private SolverFactory solverFactory;
  private Solver boardSolver;

  //Answer sub Model Components
  private Map<Integer,List<String>> userAnswersMap;
  private int minAnswerSize;
  private int maxAnswerSize;

  //Answer Input sub Model Components
  private String answerInputResponse;
   

  //All
  private List<String> solutionList; 
  private boolean active; //is the user interacting
                          //with this model

  public SinglePlayerModel() {
    super();

    support = new PropertyChangeSupport(this);

    this.size = 4;
    this.gameMode = "Classic";
    this.boardFactory = new BoardFactory();
    this.board = boardFactory.getInstance(size,gameMode);
    this.solverFactory = new SolverFactory("src/res/corncob_lowercase.txt");
    this.boardSolver = solverFactory.getInstance(gameMode);
    this.minAnswerSize = 3;
    this.maxAnswerSize = 7;

    //create the map to accomodate the permitted answer constraints
    this.userAnswersMap = new HashMap(); //emtpy 
    this.answerInputResponse = "";   
    this.solutionList = new ArrayList();  //emtpy
    this.active = false;
  }

   //////////////////
   //Internal Control
   /////////////////

   //create an emtpy map of string lists adhering to 
   //the answer constraints set by the model
   public void resetUserAnswersMap() {
        this.userAnswersMap = new HashMap();
        for ( int i = minAnswerSize; i <= maxAnswerSize; i++ ) {
            this.userAnswersMap.put(i,new ArrayList<String>());
        }
   }


  /////////////////
  ///utility
  /////////////////

  
  // Indicate that the user is no longer viewing this data
  // data model
  public void setActive(boolean x) {
        this.active = x;
  }

  //given the current model state, generate a 
  //new game, find the solution, clear the user answers
  public void generateNewGame() {
    this.board = boardFactory.getInstance(size,gameMode);
    this.solutionList = boardSolver.solveWords(board);
    resetUserAnswersMap();
  }
  

  //Change the size
  public void setSize(int newSize) {
    this.size = newSize;
  }

  //Change the gameMode, change the Solver
  public void setGameMode(String newMode) {
    this.gameMode = newMode;
    this.boardSolver = solverFactory.getInstance(gameMode);
  }

  //change the models response string
  public void setAnswerInputResponse(String res) {
    this.answerInputResponse = res;
  }


  //Check if the word is already in the userAnswerMap
  public boolean answerExists(String ans) {
    List<String> answersOfSameSize = (ans.length()<maxAnswerSize)?userAnswersMap.get(ans.length()):userAnswersMap.get(maxAnswerSize);
    return answersOfSameSize.contains(ans);
  }
 
 
  //User Adds an answer to the model 
  public void addAnswer(String ans) {
    List<String> answersOfSameSize = new ArrayList<String>();
    if (ans.length() < minAnswerSize) {
        System.out.println("Something is broken, Controller should prevent answers < minSize to model.addAnswer");
        System.exit(0);
    } else if (ans.length() < maxAnswerSize ) {
        //retrieve list with matching answer length
        answersOfSameSize = userAnswersMap.get(ans.length());
    } else {
        //retrieve list with greatest answer length
        answersOfSameSize = userAnswersMap.get(maxAnswerSize);
    }  
    //answers already exist in the corresponding list
    if ( answersOfSameSize.size() != 0 ) {
        //find index sorted by size and alphabet 
        Iterator itty = answersOfSameSize.iterator();
        boolean indexFound = false;
        String current = "";
        while (itty.hasNext() && !indexFound) {
            current = (String) itty.next();
            if ( current.length() > ans.length()) {
                indexFound = true;
            } else if ( current.length() == ans.length() && ans.compareTo(current) < 0 ) {
                indexFound = true;
            }
        }
        //insert according to index
        if (!indexFound) {
            answersOfSameSize.add(ans);
        } else {
            answersOfSameSize.add(answersOfSameSize.indexOf(current),ans);
        }
    //no words exist in the list
    } else {
        answersOfSameSize.add(ans);
    }
   
  //remove me later pls 
  System.out.println(userAnswersMap.toString());     
  }
  

  /////////////////////
  //access
  ////////////////////

  public int getSize() {
    return this.size;
  }

  public String getGameMode() {
    return this.gameMode;
  }
    
  public Board getBoard() {
    return this.board;
  }
  
  public Map<Integer,List<String>> getUserAnswersMap() {
    return this.userAnswersMap;
  }

  public String getAnswerInputResponse() {
    return this.answerInputResponse;
  }
    
  public List<String> getSolutionList() {
    return this.solutionList;
  }

  public int getMaxAnswerSize() {
    return this.maxAnswerSize;
  }

  public int getMinAnswerSize() {
    return this.minAnswerSize;
  }


  public boolean getActive() {
    return this.active;
  }

  public PropertyChangeSupport getSupport() {
    return support;
  }

  //pcl interface requirements
  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    support.addPropertyChangeListener(pcl);
  }   
  public void removePropertyChangeListener(PropertyChangeListener pcl) {
    support.removePropertyChangeListener(pcl);
  }





  
}
