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
    private Solver solver;
    private Board current; //board should be either programmed to an interface,
                           //or allow generality and constraints to comply with a 
                           //factory pattern
    private Map<List<Integer>,String> currentSolution;
    private Board previous;
    //boggle classic, boggle redux, boggle links, boggle chords
    //should be changed to enum
    private String gameMode;
    private int size;
    private String wordFilePath;

    //default : classic, classicSolver, size is 4
	public GameWarden() {
        wordFilePath = "src/res/corncob_lowercase.txt";
        gameMode = "classic";
	    solver = new ClassicSolver(wordFilePath);
        this.size = 4;
        current = new Board(size); 	
	}
   
    //switch the GameWarden's mode to the given
    //game name, will change solver 
    //should be changed to enum
    /*
    public void setMode(String mode) {
      if (mode.equals("classic")) {
        solver = new ClassicSolver();
        current = new Board();
      } else {
        //redux ?
      }
        //links ?
    }
    */
	
    // - helper method	- //
	//decompose the solution map, into a list of unique words
    private List<String> createUniqueWords() {
        List<String> uniqueWords = new ArrayList<String>();
        Set keys = currentSolution.keySet();
        Iterator keyIterator = keys.iterator();
        while(keyIterator.hasNext()) {
            List<Integer> path = (List<Integer>) keyIterator.next();
            String tmp = (String) currentSolution.get(path);
            if (!uniqueWords.contains(tmp)) {
                uniqueWords.add(tmp);
            }
        }
        //sort the unique words
        Collections.sort(uniqueWords);
        return uniqueWords; 
    
    }

    

    //Accessors

    public Board getBoard() {
      return current;
    }

    //Stats

    //determine if a word is in the solution set
	public boolean isWord(String word) {
		return createUniqueWords().contains(word);
	}

    //score a single user
    public int score(List<String> userWords) {
        //todo
        return 0;
    }
   
    //score a set of users at the same time | factors duplicate words 
    public List<Integer> scoreParty(List<List<String>> userWords) {
        //todo
        return new ArrayList<Integer>();
    }

	
	
}
