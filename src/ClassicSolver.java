import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
 * 
 * This class solves Boggle Boards, Each instance uses the word file,
 * it was passed at construction
 * 
 *
 *	A boggle board is a 2D array of characters which can be represented by a 1-dimensional array
 *	A 4 x 4 board looks like the following
 *
 *	0	1	2	3
 *	4	5	6	7	
 *	8	9	10	11
 *	12	13	14	15
 *
 *	an index I has the potential following neighbors 
 *	(i-1)	left			(i+1)	right
 *	(i-3)	top right	(i+3)	bot left
 *	(i-4)	top			(i+4)	bot
 *	(i-5)	top left		(i+5)	bot right
 *
 *	a boggle path is valid iff 
 *		the numerical path represents a word under the conditions
 *			 of the faces of the dice
 *			&& the path uses the indices of the board only once
 *
 */
public class ClassicSolver implements Solver {
	private String wordFilePath;
	private List<String> words;//what is this
    private List<String> uniqueWords;
	private List<Character> letters;	//the letter the current board
	private Map solution;			//a map of all of the valid boggle paths and there resultant strings

	public ClassicSolver(String wordFilePath) {
        super();
		this.letters = new ArrayList<Character>();
		this.wordFilePath = wordFilePath;
		this.words = new ArrayList<String>();
        this.uniqueWords = new ArrayList<String>();
		this.solution = new HashMap<List<Integer>,String>();	// a map that holds paths and strings
		loadWordsFromFile();
	}
	
	public void clear() {
		letters.clear();
		solution.clear();
	}
	
	
	/** Executes Boggle Path on each die on the board
	 *  returns a map of the resulting words 
	 * 
	 * 
	 * @param board
	 * 
	 * @return the solution space for the board map<List<Int,String>
	 */
	public Map<List<Integer>,String> solve(Board board) {
		
		//wipe previous board
		clear();
		
		//apply board instance to the solver
		this.letters = board.getLetters();
        int numDice = letters.size();
        int sideLength = (int) Math.sqrt(numDice);
	
		//solve the board
		List frontier = 	new ArrayList<List<Integer>>();
		for (int i = 0; i < numDice; i ++) {
			frontier.add( new ArrayList<Integer>  (Arrays.asList(i)) );
		}
		
		List closed =	 new ArrayList<List<Integer>>();
		List<List<List<Integer>>> result = bogglePath(frontier,closed,sideLength);
		List<List<Integer>> wordPaths = result.get(1);
		
		for(List<Integer> L :wordPaths) {
			String tmp = pathToString(L);
			if (isLegalWord(tmp)) {
				solution.put(L,tmp);
			}
		}
		
		return solution;
	}

    //reduce the solution map to only the unique words on the board
    //throws away paths that are different but yield the same word
    public List<String> createUniqueWords() {
        uniqueWords = new ArrayList<String>();
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

       
	
	//given a numerical position on the board, find all possible boggle Paths that start with that position
	//return a list with 2 List<List<Integer>> which is frontier and closed
	public List<List<List<Integer>>> bogglePath(List<List<Integer>> frontier,List<List<Integer>> closed,int sideLength) {
		
		List<List<Integer>> newFrontier = new ArrayList<List<Integer>>();
		
		//explore the frontier
		for (List<Integer> path : frontier) {
			
			//check the possible physical neighbors for the leading index of the current path
			List<Integer> neighbors = getNeighbors(path,sideLength);
			
			//construct the neighboring paths
			List<List<Integer>> neighborPaths = new ArrayList<List<Integer>>();
			 
			for (int i = 0; i < neighbors.size(); i++) {
				List<Integer> path_i = new ArrayList(path);
				path_i.add(neighbors.get(i));
				//ensure this path might be an English Word
				if (isPartialWord(pathToString(path_i))) {
					neighborPaths.add(path_i);
				}
			}
			//add all of the neighboring paths to the new frontier
			newFrontier.addAll(neighborPaths);
			//add the current path to the closed if its valid
			if(isWord(pathToString(path))){
				closed.add(path);
			}
		}
		
		if(newFrontier.size()!=0) {						//recursive case
			return bogglePath(newFrontier,closed,sideLength);
		}else{											//base case
			List<List<List<Integer>>> end = new ArrayList<List<List<Integer>>>();
			end.add(newFrontier);
			end.add(closed);
			return end;
		}
		
	}
	
	//return the valid neighbors of the path
	public List<Integer> getNeighbors(List<Integer> path,int sideLength) {
		int pathLength = path.size();
		int head = path.get(pathLength-1);
		List<Integer> neighborList = new ArrayList<Integer>();
		neighborList.add(head-1);//left
		neighborList.add(head-(sideLength+1));//top left
		neighborList.add(head-(sideLength-1));//top right
		neighborList.add(head-sideLength);//top
		neighborList.add(head+1);//right
		neighborList.add(head+(sideLength+1));//bot right
		neighborList.add(head+(sideLength-1));//bot left
		neighborList.add(head+sideLength);//bot
		

		//remove the neighbors that 
		//are physically invalid 
		//( not in bounds or tile already used)
	    // -size : up  +size : down
        // +1 : right -1 :  left	
		
		//remove top dir
		if (path.contains(head-(sideLength)) || head / sideLength == 0) {
			neighborList.remove(neighborList.indexOf(head-sideLength));
		}//remove top left dir
		if (path.contains(head-(sideLength+1)) || head / sideLength == 0 || head % sideLength ==0) {
			neighborList.remove(neighborList.indexOf(head-(sideLength+1)));
		}//remove left dir
		if (path.contains(head-1) || head % sideLength == 0) {
			neighborList.remove(neighborList.indexOf(head-1));
		}//remove bot left dir
		if (path.contains(head+(sideLength-1)) || head / sideLength == sideLength-1 || head % sideLength == 0) {
			neighborList.remove(neighborList.indexOf(head+(sideLength-1)));
		}//remove bot dir
		if (path.contains(head+(sideLength)) || head / sideLength == sideLength-1) {
			neighborList.remove(neighborList.indexOf(head+sideLength));
		}//remove bot right dir
		if (path.contains(head+(sideLength+1)) || head / sideLength == sideLength-1 || head % sideLength == sideLength-1) {
			neighborList.remove(neighborList.indexOf(head+(sideLength+1)));
		}//remove right dir
		if (path.contains(head+1) || head % sideLength == sideLength-1) {
			neighborList.remove(neighborList.indexOf(head+1));
		}//remove top right dir
		if (path.contains(head-(sideLength-1)) || head / sideLength == 0 || head % sideLength == sideLength-1) {
			neighborList.remove(neighborList.indexOf(head-(sideLength-1)));
		}
		
		return neighborList;
		
	}
	
	//reads the words from the file pointed to by wordFilePath
	//into the List words
	private void loadWordsFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(wordFilePath));
			String line = reader.readLine();
			while (line != null) {
				//store the current line
				words.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * finds the index of a partial matching string in the words List
	 * 
	 * @return -1 if not found
	 * @return index if found
	 * 
	 */
	public int binaryPartialStringSearch(String searchString, int min, int max) {

		if ( min == max) {
			return -1;
		}
		
		int mid = (max+min)/2;
		String midString = words.get(mid);
		int pathLength = searchString.length();
		int midStringLength = midString.length();
		
		//create partial midString
		String partialMidString;
		if (pathLength > midStringLength) {
			partialMidString = midString;
		}else {
			partialMidString = midString.substring(0, pathLength);
		}
		
		//compare the strings
		int compareValue = searchString.compareTo(partialMidString);
		//recursive case 1
		if ( compareValue <= -1) {
			return binaryPartialStringSearch(searchString,min,mid);
		}
		//recursive case 2
		else if ( compareValue >= 1) {
			return binaryPartialStringSearch(searchString,mid+1,max);
		}
		//base case 2
		else 
			return mid;
	}
	
	
	/**
	 * finds the index of a match in the words List
	 * 
	 * @return -1 if not found
	 * @return index if found
	 */
	public int binaryStringSearch(String pathString,int min, int max) {
		
		//base case
		if ( min == max) {
			return -1;
		} 
		int mid = (max+min)/2;
		String midString = words.get(mid);
		int compareValue = pathString.compareTo(midString);
		//recursive case 1
		if ( compareValue <= -1) {
			return binaryStringSearch(pathString,min,mid);
		}
		//recursive case 2
		else if ( compareValue >= 1) {
			return binaryStringSearch(pathString,mid+1,max);
		}
		//base case 2
		else 
			return mid;
		
	}
	
	//determines if a string is the words list
	public boolean isWord(String pathString) {
		int index = binaryStringSearch(pathString,0,words.size()-1);
		return (index!=-1)?true:false;
	}
	
	
	//Determines if string is a substring (0,n) of 
	public boolean isPartialWord(String pathString) {
		int index = binaryPartialStringSearch(pathString,0,words.size()-1);
		return (index!=-1)?true:false;
	}
		
	//ensures the words are atleast 3 letters long
	public boolean isLegalWord(String word) {
		return (word.length()>2)?true:false;
	}
	
	
	/**converts the numerical path to a string
	 * !the letter q will be substituted with qu
	 * 
	 * @param numPath
	 * @return the string represented by the path
	 */
	public String pathToString(List<Integer> numPath){
		int pathLength = numPath.size();
		String pathString = "";
		for (int i = 0; i < pathLength; i++) {
			char c = this.letters.get(numPath.get(i));
			pathString += c;
			if (c == 'q' ) {		//add the u for the Qu tile
				pathString+='u';
			}
		}
		return pathString;
	}
	
	
}
