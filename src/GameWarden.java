import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
/**
 * @author matthewkeville
 * The GameWarden class is responsible for
   Board Generation - Board creation
   Board Management - Board History
   Board Presentation
   Word Validation   
   Scoring
   Game Analytics { missed words, missed supers, missed subs } 

   isWord(String word) -> bool
   score(List<String> words) -> int
   ### score (List<list<string>> words ) -> List<int> | for multiplayer
   pathsOf(String word) -> List<List<words>>
   missedWords(List<string>) -> List<words> 
   missedSupers ..
   missedSubs ..
   missedPlurals ..
 *
 */
public class GameWarden {
    private Solver bs;
    private BoardFactory bf;
    private BoardPrinter bp;
    private SolverFactory sf;

    private Map<String,List<List<Integer>>> solution;
    private List<String> uniqueWords;

    private Board board;
    //boggle classic, boggle redux, boggle links, boggle chords
    private String gameType;
    private int size;

    private final String wordFilePath = "src/res/corncob_lowercase.txt";

    //Establish Default settings and create a new board
    //default : classic, classicSolver, size is 4
	public GameWarden() {
        gameType = "Classic";
        this.size = 4;
        this.sf = new SolverFactory(wordFilePath);
	    this.bs = sf.getInstance("Classic"); 
        this.bf = new BoardFactory();
        this.bp = new BoardPrinter();
        shake();
	}

    //create and solve a new boggle board with the current settings
    public void shake() {
        this.board = bf.getInstance(size,gameType);
        this.solution =  bs.solve(this.board);
        this.uniqueWords = bs.solveWords(this.board);
        this.bp.setBoard(this.board);
    }

    //load a known board into the GameWarden
    public void loadGame(Board board,String gameType) {
        this.board = board;
        this.bs = sf.getInstance(gameType);
        this.solution = bs.solve(board);
        this.uniqueWords = bs.solveWords(board); //the double solve is kinda redundant
        this.bp.setBoard(board);
    }

    public void setSize(int size) {
        this.size = size;
        shake();
    }

    public int getSize() {
        return size;
    }
    
    public  void setGameType(String gameType) {
        this.gameType = gameType;
        this.bs = sf.getInstance(this.gameType);
    }

    //add a field called display type to change board display
    public String getBoardDisplay() {
        //return this.bp.getBoardDisplay();
        return this.bp.getPrettyBoardDisplay(); 
    }

    public Board getBoard() {
        return board;
    }
      
    public String getGameType() {
        return gameType;
    }

    public void rotateBoardLeft() {
        this.bp.rotateLeft();
    }
    
    public void rotateBoardRight() {  
        this.bp.rotateRight();
    }
   

    public List<String> getUniqueWords() {
        return uniqueWords;
    }
   

    //stats
 
    public int getNumberOfWords() {
        return uniqueWords.size();
    }


    //determine if a word is in the solution set
	public boolean isWord(String word) {
		return uniqueWords.contains(word);
	}

    //score a single user
    /* 3,4 -1 , 5 -2 , 6-3, 7 -4 , 8+ - 11
    */
    public int score(List<String> userWords) {
        //todo
        int score  = 0;
        for (String s : userWords) 
        {
          if (s.length() < 4) {
            score+=1;
          } else if (s.length() < 5) {
            score+=2;
          } else if (s.length() < 6) {
            score+=3;
          } else if (s.length() < 7) {
            score+=4;
          } else {
            score+=11;
          }
        }
        return score;
    }

    public List<String> missedPlurals(List<String> userWords) {
        List<String> missed = new ArrayList();
        for (String s : userWords) {
            String ps = s.concat("s");
            if (uniqueWords.contains(ps) && !userWords.contains(ps) ) {
                missed.add(ps);
            }
        } 
        return missed; 
    } 
   
    //score a set of users at the same time | factors duplicate words 
    public List<Integer> scoreParty(List<List<String>> userWords) {
        //todo
        return new ArrayList<Integer>();
    }
	
	
}
