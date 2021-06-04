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
    private Solver bs;
    private BoardFactory bf;
    private BoardPrinter bp;

    private Map<List<Integer>,String> solution;
    private List<String> uniqueWords;

    private Board board;
    //boggle classic, boggle redux, boggle links, boggle chords
    private String gameType;
    private int size;
    private String wordFilePath;

    //default : classic, classicSolver, size is 4
	public GameWarden() {
        wordFilePath = "src/res/corncob_lowercase.txt";
        gameType = "Classic";
	    bs = new ClassicSolver(wordFilePath);
        this.size = 4;
        this.bf = new BoardFactory();
        this.bp = new BoardPrinter();
        this.board = bf.getInstance(size);	
	}

    //create and solve a new boggle board with the current settings
    public void shake() {
        this.board = bf.getInstance();
        this.solution =  bs.solve(this.board);
    }

   //reduce the solution map to only the unique words on the board
    //throws away paths that are different but yield the same word
    public List<String> getUniqueWords() {
        List<String> uniqueWords = new ArrayList<String>();
        Set keys = solution.keySet();
        Iterator keyIterator = keys.iterator();
        while(keyIterator.hasNext()) {
            List<Integer> path = (List<Integer>) keyIterator.next();
            String tmp = (String) solution.get(path);
            if (!uniqueWords.contains(tmp)) {
                uniqueWords.add(tmp);
            }
        }
        //sort the unique words
        Collections.sort(uniqueWords);
        return uniqueWords; 
    }   
 
   

    //Stats

    //determine if a word is in the solution set
	public boolean isWord(String word) {
		return getUniqueWords().contains(word);
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
