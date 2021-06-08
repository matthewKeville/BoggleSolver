import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.stream.*;
import java.nio.file.Files; //much more succint
import java.nio.file.Paths; //takes advantage of streams
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
public class ClassicSolverNew implements Solver {
	private String wordFilePath;
	private List<String> words;//what is this
    //need to be reset
	private List<String> faces;//the letter the current board
	private Map solution;			//a map of all of the valid boggle paths and there resultant strings

	public ClassicSolverNew(String wordFilePath) {
        super();
		this.faces = new ArrayList<String>();
		this.wordFilePath = wordFilePath;
		this.words = new ArrayList<String>();
		this.solution = new HashMap<List<Integer>,String>();	// a map that holds paths and strings
		loadWordsFromFile();
	}
	
	private void clear() {
		solution.clear();
        faces.clear();
	}
	
	
	/** Executes Boggle Path on each die on the board
	 *  returns a map of the resulting words 
	 * 
	 * @param board
	 * 
	 * @return the solution space for the board map<List<Int,String>
	 */
	public Map<List<Integer>,String> solve(Board board) {
		
		//wipe previous board
		clear();
		
		//apply board instance to the solver
		this.faces = board.getFaces();
        int numDice = faces.size();
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


	
	//given a numerical position on the board, find all possible boggle Paths that start with that position
	//return a list with 2 List<List<Integer>> which is frontier and closed
	protected List<List<List<Integer>>> bogglePath(List<List<Integer>> frontier,List<List<Integer>> closed,int sideLength) {
		
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
	protected List<Integer> getNeighbors(List<Integer> path,int sideLength) {
		int pathLength = path.size();
		int head = path.get(pathLength-1);
        // l , tl , tr , t , r , br , bl , b
		List<Integer> neighborList = Arrays.asList(
          head-1,head-(sideLength+1),head-(sideLength-1),
          head-sideLength,head+1,head+(sideLength+1),
          head+(sideLength-1),head+sideLength);
         
        //remove the neighbors that 
		//are out of bounds, or already used
        //to remove special tiles i.e. #, I can add to the filter 
        neighborList.stream()
            .filter( (x) -> path.contains(x))
            .filter( (x) -> head / sideLength == sideLength-1)
            .filter( (x) -> head / sideLength == sideLength-1)
            .filter( (x) -> head % sideLength == 0)
            .filter( (x) -> head % sideLength == sideLength-1)
            .forEach((x) -> { neighborList.remove(x);});

		return neighborList;
		
	}
	
	//reads the words from the file pointed to by wordFilePath
	//into the List words
	protected void loadWordsFromFile() {
        try {
        Stream<String> rows = Files.lines(Paths.get(wordFilePath));
        words = rows
            .collect(Collectors.toList());
        rows.close();
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
	protected int binaryPartialStringSearch(String searchString, int min, int max) {

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
	protected int binaryStringSearch(String pathString,int min, int max) {
		
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
	protected boolean isWord(String pathString) {
		int index = binaryStringSearch(pathString,0,words.size()-1);
		return (index!=-1)?true:false;
	}
	
	
	//Determines if string is a substring (0,n) of 
	protected boolean isPartialWord(String pathString) {
		int index = binaryPartialStringSearch(pathString,0,words.size()-1);
		return (index!=-1)?true:false;
	}
		
	//ensures the words are atleast 3 letters long
	protected boolean isLegalWord(String word) {
		return (word.length()>2)?true:false;
	}
	
	
	/**converts the numerical path to a string
	 * !the letter q will be substituted with qu
	 * 
	 * @param numPath
	 * @return the string represented by the path
	 */
	private String pathToString(List<Integer> numPath){
		int pathLength = numPath.size();
		String pathString = "";
		for (int i = 0; i < pathLength; i++) {
			String c = this.faces.get(numPath.get(i));
			pathString += c;
		}
		return pathString;
	}
	
	
}
