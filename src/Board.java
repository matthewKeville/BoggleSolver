import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/*
 * a boggle board is a square board of letters, either 4x4 (normal boggle)
 *  
 *  this board object represents a boggle instance
 *  which includes a list of 16 characters
 *  
 *  the board object holds the solution to the boggle instance which
 *  is a map of the words on the board and the paths that
 *  produce the words for the instance.
 *  
 *  
 *  
 *  TODO -> CREATE UNIQUE WORDS SHOULD NOT BE A FUNCTION, UNIQUE WORDS SHOULD BE IMPLICIT TO THE DATA STRUCTURE
 *  CONSIDER A ADT -> SET WHICH CAN ONLY CONTAINT UNIQUE WORDS, THIS FUNCTIONALITY COULD BE REMOVED
 *  
 */
public class Board {
	private List<Character> letters;
	
	//variables assigned after solution is set
	private int totalWords;						
	private Map<List<Integer>,String> solution;	//index paths and corresponding words
	private List<String> uniqueWords;			//list of all unique words
	
	
	//Constructs a Board with random initialization
	public Board() {
		this.letters = generate();
		this.uniqueWords = null;
		this.solution = null;
		this.totalWords = 0;
	}
	
	//Constructs a board from a a set of letters
	public Board(String boardString) {
		letters = new ArrayList<Character>();
		int stringLength = boardString.length();
		for (int i = 0; i < stringLength; i++) {
			letters.add(boardString.charAt(i));
		}
		this.uniqueWords = null;
		this.solution = null;
		this.totalWords = 0;
	}
	
	//returns the Letters list of the board
	public List<Character> getLetters(){
		return letters;
	}
	

	//print the board in a 4x4 grid
	public void printBoard(){
		String result = "";
		for (int i = 0; i < 4; i++) {
			String rowString = "";
			for(int j = 0; j < 4; j++) {
				rowString+= letters.get((i*4)+j);
			}
			rowString+="\n";
			result+=rowString;
		}
		System.out.println(result);
	}
	
	//assigns the board a solution and creates a the unique words list
	public void setSolution(Map<List<Integer>,String> solution) {
		this.solution = solution;
		createUniqueWords();
	}
	
	
	/**@precondition setSolution has been called
	 */
	public void printUniqueWords() {
		for (String s: uniqueWords) {
			System.out.println(s);
		}
	}
	
	/**@precondition setSolution has been called
	 */
	public List<String> getUniqueWords(){
		return uniqueWords;
	}
	
	public String getBoardString() {
		String boardString="";
		for ( Character c : letters) {
			boardString+=c;
		}
		return boardString;
	}
	
	//
	//PRIVATE FUNCTIONS
	//
	
	
	//return a list of characters that encode the boggle instance
	private List<Character> generate(){
		Random rand = new Random();
		List<Character> letters = new ArrayList<Character>();
		List<String> dice = 
			Arrays.asList(
							"aocsph",
							"aeange",
							"afpksf",
							"attowo",	
							"evlrdy",
							"tlryte",
							"sieneu",
							"lxedri",
							"hwegen",
							"hrvtew",
							"oajbob",
							"ctuiom",
							"eitsos",
							"syidtt",
							"miqnuh",
							"znrnhl"
										);
		//Roll the Dice: For each die, pick one of the six faces at random
		for(int i = 0; i < 16; i++) {
			letters.add(dice.get(i).charAt(rand.nextInt(6)));
		}
		
		//Permute Faces: randomize position of selected faces
		int size = letters.size();
		int idx1;
		int idx2;
		char c;
		for ( int i = 0; i < letters.size(); i++) {
			idx1 = i;
			idx2 = (rand.nextInt(size-i)+i);
			c = letters.get(idx1);
			letters.set(idx1, letters.get(idx2));
			letters.set(idx2, c);
		}
		return letters;
	}
	
	
	//sorts through the solution space and creates a list of unique words
	private void createUniqueWords() {
		uniqueWords = new ArrayList<String>();
		Set keys = solution.keySet();
		Iterator keyIterator = keys.iterator();
		while(keyIterator.hasNext()) {
			List<Integer> path = (List<Integer>) keyIterator.next();
			String tmp = solution.get(path);
			if (!uniqueWords.contains(tmp)) {
				uniqueWords.add(tmp);
			}
		}
		//sort the unique words
		Collections.sort(uniqueWords);
		
		
	}
}
