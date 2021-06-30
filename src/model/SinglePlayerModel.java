package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

//remove wildcards ...
import model.board.*;
import model.solver.*;
import model.*;

//The SinglePlayerModel models the entire process of playing a single player
//boggle game. This includes generating responses to the quality of words
// and the validity of words. The single player controller governs how the user
// interacts with the model via updates from the view, otherwise changes to 
// the model are internal (i.e.) timed events. The access to model variables
// are only intended for use by the SinglePlayerController or a parent of it

public class SinglePlayerModel{

  //Answer Values can be coupled with an internal model
  //range that dictates the AnswerValue based on GameMode
  //This construction is used to dynamically generate AnswerInput
  //Responses and sounds from outside the model, the idea
  //here is that Game Modes can come with there own AnswerGrade 
  //ranges to avoid explicit sounds per word length
  public enum AnswerGrade {
    FAIL,
    BOTTOM,
    LOW,
    MEDIUM,
    HIGH,
    TOP,
  }


  //PREGAME : no game is being played
  //GAME : answers are being processed from
  //    the SinglePlayerView and an (optional) 
  //    timer runs until the the gameDuration is 
  //    is exceeded
  //POSTGAME : answer input is disabled, the 
  //    solution is displayed in the view
  //    game stats are calculated
  public enum GameState {
    PREGAME,
    GAME,
    POSTGAME
  }
    
  //The property change support exists for the super controller
  //to switch between the singleplayerview and the main menuview
  //This can be removed and replaced with the modelChangeListener
  private PropertyChangeSupport support;
  
  private List<ModelChangeListener> modelChangeListeners;


  private int size;  
  private String gameMode; 

  private BoardFactory boardFactory;
  private Board board;

  private SolverFactory solverFactory;
  private Solver boardSolver;
  //the minimum accepted answer size and the 
  //maximum word length bin. All words > maxAnswerSize
  //are stored in the same index in the userAnswermap
  private int minAnswerSize;
  private int maxAnswerSize;
  //What the model thinks about the users answer
  private String answerInputResponse;

  //is the game being timed ?
  private boolean timed;
  //time in seconds
  private int gameDuration;
  //current time
  private int time;
  
  private final Timer timer = new Timer(1000,new ActionListener(){ public void actionPerformed(ActionEvent e){}});
 
  private GameState gameState; 

  //Map of users valid words indexed by size
  //Change userAnswersMap > answersBySize
  //stores user answers by size
  private Map<Integer,List<String>> userAnswersMap;

  //Map of valid words indexed by size (can be derived from solution)
  // name change solutionMap > solutionBySize
  private Map<Integer,List<String>> solutionMap;
   
  // all words and the paths to get to them
  private Map<String,List<List<Integer>>> solution;



  //this might not be necessary, but its currently
  //used to facilitate changing between main menu and single
  //player view
  //is the user interacting with the model?
  private boolean active; 

  //Currently there is nothing that binds the state of the model
  //to the main menu values, the binding is hard coded and I think this
  //is bad practice
  public SinglePlayerModel() {
    super();

    modelChangeListeners = new ArrayList();
    support = new PropertyChangeSupport(this);
    setupTimer();

    this.size = 4;
    this.minAnswerSize = 3;
    this.maxAnswerSize = 7;
    this.gameMode = "Classic";
    this.boardFactory = new BoardFactory();
    this.board = boardFactory.getInstance(size,gameMode);
    this.solverFactory = new SolverFactory("src/res/corncob_lowercase.txt");
    this.boardSolver = solverFactory.getInstance(gameMode);



    this.solution = new HashMap();
    this.solutionMap = new HashMap();

    this.userAnswersMap = new HashMap();
    this.answerInputResponse = "";   

    this.gameState = GameState.PREGAME;
    this.timed = true;
    this.gameDuration = 60;
    this.time = this.gameDuration;

    //might group into gameState
    this.active = false;
  }


   //////////////////
   //Internal Control
   ////////////////

   // * Depends on solutionList */
   //Transform the solution list into a map, that follows
   //the same indexing scheme for the userAnswersMap
   private void  generateSolutionMap() {

        //create map with empty lists
        for (int i = minAnswerSize; i <= maxAnswerSize; i++) {
            solutionMap.put(i,new ArrayList<String>());
        }

        //populate map
        for (String sol : solution.keySet()) {
            if (sol.length() < maxAnswerSize) {
                List<String> words = solutionMap.get(sol.length());
                words.add(sol); 
                solutionMap.put(sol.length(),words);
            } else {
                List<String> words = solutionMap.get(maxAnswerSize);
                words.add(sol);
                solutionMap.put(maxAnswerSize,words);
            }
        }
        System.out.println(solutionMap);
 
   }


   //create an emtpy map of string lists adhering to 
   //the answer constraints set by the model
   private void resetUserAnswersMap() {
        this.userAnswersMap = new HashMap();
        for ( int i = minAnswerSize; i <= maxAnswerSize; i++ ) {
            this.userAnswersMap.put(i,new ArrayList<String>());
        }
   }

  private void setupTimer() {
    ActionListener updateTime = new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            //times up spongebob
            if (time <= 0) {
                time = 0;
                //switch gamestate
                gameState = GameState.POSTGAME;
                answerInputResponse = ""; 
                //stop timer
                timer.stop();
            } else {
                time--;
            }
            fireModelChangeEvent();
        }
    };
    timer.setDelay(1000);
    timer.addActionListener(updateTime);
  }

  private void startTimer() {
    time = gameDuration;
    timer.start();
    fireModelChangeEvent();
  }


  ////////////////////
  //External Control
  ////////////////////

  //shake (new Game) 
  public void shake() {
    System.out.println("board was shook");
    //new board
    this.board = boardFactory.getInstance(size,gameMode);
    //new solve
    this.solution = boardSolver.solve(board);
    generateSolutionMap();
    //clear user vars
    answerInputResponse = "";
    resetUserAnswersMap();
    //reset timer
    timer.stop();
    time = gameDuration;
    //change game state to preGame
    gameState = GameState.PREGAME;
    //notify controller
    fireModelChangeEvent();
  }

  public void play() {
    shake();
    if (timed) {
        startTimer();
    }
    gameState = GameState.GAME;
    fireModelChangeEvent();
  }


  public void end() {
    if (timed) {
        timer.stop();
        time = 0;
    }
    gameState = GameState.POSTGAME;
    answerInputResponse = "";
    fireModelChangeEvent();
  }

  // Check if the given answer exists in the current board
  // If it does add it to the model 
  // set the model answerInputResponse accordingy to the 
  // the situation {valid,already found,not found, invalid input}
  public AnswerGrade addAnswerAttempt(String ans) {

            boolean valid = true;
            System.out.println("Add New Word Attempt");
            AnswerGrade answerGrade = AnswerGrade.FAIL;

            //trim leading and trailing ws
            String newWord = ans; 
            newWord = newWord.trim();

            //check if there are spaces in between
            if (newWord.contains(" ")) {
               //this should belong to the model 
               answerInputResponse = "Your word can't contain spaces";
               valid = false; 
            }

            //check if word is already found
            if (answerExists(newWord)) {
               answerInputResponse = "You already found this word!";
               valid = false;
            }

            //if word is in the answerList
            if (!solution.keySet().contains(newWord)) {
                valid = false;
                answerInputResponse = " Umm no , this is awkward ";
            }
            //only add if valid
            if (valid) {
                addAnswer(newWord);
                if (newWord.length() == 3 || newWord.length() == 4) {
                        answerInputResponse = "Nice";
                        answerGrade = AnswerGrade.BOTTOM;
                } else if (newWord.length() == 5) {
                        answerInputResponse = "Nice";
                        answerGrade = AnswerGrade.LOW;
                } else if (newWord.length() == 6) {
                        answerInputResponse = "Great";
                        answerGrade = AnswerGrade.MEDIUM;
                } else if (newWord.length() == 7) {
                        answerInputResponse = "Impressive";
                        answerGrade = AnswerGrade.HIGH;
                } else {
                        answerInputResponse = "Incredible";
                        answerGrade = AnswerGrade.TOP;
                }
            } else { answerGrade = AnswerGrade.FAIL; }
            fireModelChangeEvent();
            return answerGrade;
  }




  //rotateLeft
  public void rotateLeft() {
    board.rotateLeft();
    fireModelChangeEvent();
  }

  //rotateRight 
  public void rotateRight() {
    board.rotateRight();
    fireModelChangeEvent();
  }


  
  // Indicate that the user is no longer viewing this data
  // data model
  public void setActive(boolean x) {
        this.active = x;
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

  // determine if the game has a time limit
  public void setTimed(boolean x) {
    this.timed = x;
    fireModelChangeEvent();
  }

  //Check if a word is already in the userAnswerMap
  private boolean answerExists(String ans) {
    if (ans.length() < minAnswerSize) { return false; }
    List<String> answersOfSameSize = (ans.length()<maxAnswerSize)?userAnswersMap.get(ans.length()):userAnswersMap.get(maxAnswerSize);
    return answersOfSameSize.contains(ans);
  }
 

 
  //facilitates adding an answer to the user answer map
  //intended to be called by  addAnswerAttempt
  private void addAnswer(String ans) {
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

  public int getMaxAnswerSize() {
    return this.maxAnswerSize;
  }

  public int getMinAnswerSize() {
    return this.minAnswerSize;
  }

  public int getTime() {
    return time;
  }

  public boolean isTimed() {
    return timed;
  }

  public boolean getActive() {
    return this.active;
  }

  public GameState getGameState() {
    return gameState;
  }

  public Map<Integer,List<String>> getSolutionMap() {
    return solutionMap;
  }

  public Map<String,List<List<Integer>>> getSolution() {
    return solution;
  }

  //pcl interface requirements

  public PropertyChangeSupport getSupport() {
    return support;
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    support.addPropertyChangeListener(pcl);
  }   
  public void removePropertyChangeListener(PropertyChangeListener pcl) {
    support.removePropertyChangeListener(pcl);
  }

  //add  model change event listener
  public void addModelChangeListener(ModelChangeListener mcel) {
    modelChangeListeners.add(mcel);
  }

  //broadcast model change event to all listeners
  private void fireModelChangeEvent() {
    ModelChangeEvent mce = new ModelChangeEvent("SinglePlayerModel");
    for (ModelChangeListener mcel : modelChangeListeners) {
        mcel.modelChange(mce);
    }
  }

    



  
}
