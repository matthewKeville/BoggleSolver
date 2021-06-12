import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.*;
import java.util.stream.*;
import java.util.function.*; //for predicate
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
public class ReduxSolver implements Solver {

	private List<String> words;//List of legal words

	public ReduxSolver(List<String> words) {
		this.words = words;
	}
	
	
	/** Executes Boggle Path on each die on the board
	 *  returns a map of the resulting words 
	 * 
	 * @param board
	 * 
	 * @return the solution space for the board map<List<Int,String>
	 */
	public Map<String,List<List<Integer>>> solve(Board board) {
		
		//apply board instance to the solver
		List<String> faces = board.getFaces();
        int sideLength = (int) Math.sqrt(faces.size());
	
		//solve the board
		List frontier = new ArrayList<List<Integer>>();
		for (int i = 0; i < faces.size(); i ++) {
            if (!faces.get(i).equals("#")) {
			    frontier.add( new ArrayList<Integer>(Arrays.asList(i)));
            }
		}
		
		List closed = new ArrayList<List<Integer>>();

		List<List<List<Integer>>> result = bogglePath(frontier,closed,faces);
		List<List<Integer>> wordPaths = result.get(1);
        Map<String,List<List<Integer>>> solution = new HashMap();
        //	
        //unpack solution	
        //
        
		for(List<Integer> L : wordPaths) {
            List<String> tmpStrings = pathToString(L,faces);
            List<List<Integer>> paths;
			for (String tmp : tmpStrings) {
                if (isWord(tmp)) {
		            if (solution.containsKey(tmp)) {
                        paths = solution.get(tmp);
                        paths.add(L);
                        solution.put(tmp,paths);
                    } else {
                        paths = new ArrayList();
                        paths.add(L);
                        solution.put(tmp,paths);
                    }
                }
            }
		}
        //Create a solution map that maps words to ways to find
        for (List<Integer> L : wordPaths) {
            
        }
        List<List<Integer>> wayToFind = new ArrayList();

		
		return solution;
	}


    //reduce the solution map to only the unique words on the board
    //throws away paths that are different but yield the same word
    public List<String> solveWords(Board board) {
        Map<String,List<List<Integer>>> solution = solve(board); 
        Set keys = solution.keySet();
        List<String> uniqueWords = new ArrayList<String>(keys);
        //sort the unique words
        Collections.sort(uniqueWords);
        return uniqueWords; 
    }   


	
	//given a numerical position on the board, find all possible boggle Paths that start with that position
	//return a list with 2 List<List<Integer>> which is frontier and closed
	protected List<List<List<Integer>>> bogglePath(List<List<Integer>> frontier,List<List<Integer>> closed,List<String> faces) {
	
        int sideLength = (int) Math.sqrt(faces.size());	
		List<List<Integer>> newFrontier = new ArrayList<List<Integer>>();
		
		//explore the frontier
		for (List<Integer> path : frontier) {
			
			//check the possible physical neighbors for the leading index of the current path
			List<Integer> neighbors = getNeighbors(path,faces);
			
			//construct the neighboring paths
			List<List<Integer>> neighborPaths = new ArrayList<List<Integer>>();
			 
			for (int i = 0; i < neighbors.size(); i++) {
				List<Integer> path_i = new ArrayList(path);
				path_i.add(neighbors.get(i));
				//ensure this path might be an English Word
                List<String> potentials = pathToString(path_i,faces);
                boolean isPartial = false;
                for (String l : potentials) {
				    if (isPartialWord(l)) {
                       isPartial = true; 
				    }
                }
                if (isPartial) {
                    neighborPaths.add(path_i);
                }
			}
			//add all of the neighboring paths to the new frontier
			newFrontier.addAll(neighborPaths);
			//add the current path strings to the closed if its valid
            List<String> closedWords =  pathToString(path,faces);
            boolean isWord = false;
            for (String s : closedWords) {
			    if(isWord(s)){
				  isWord = true;    
                }
            }
            if (isWord) {
                closed.add(path);
            }
		}
		
		if(newFrontier.size()!=0) {						//recursive case
			return bogglePath(newFrontier,closed,faces);
            
		}else{											//base case
			List<List<List<Integer>>> end = new ArrayList<List<List<Integer>>>();
			end.add(newFrontier);
			end.add(closed);
			return end;
		}
		
	}
	
	//return the valid neighbors of the path
	protected List<Integer> getNeighbors(List<Integer> path,List<String> faces) {
        int sideLength = (int) Math.sqrt(faces.size());
		int pathLength = path.size();
		int head = path.get(pathLength-1);
        // l , tl , tr , t , r , br , bl , b
        //Unfortunately Lists created by Arrays.asList are fixed side, 
        //therefore we copy the Arrays.asList List into a new List constructor
		List<Integer> neighborList = new ArrayList<Integer>(Arrays.asList(
          head-1,head-(sideLength+1),head-(sideLength-1),
          head-sideLength,head+1,head+(sideLength+1),
          head+(sideLength-1),head+sideLength)); 

        List<Integer> neighborListCopy = new ArrayList(neighborList);

        //remove the neighbors that 
		//are out of bounds, or already used
        Predicate<Integer> p1 = x -> path.contains(x);
        Predicate<Integer> p2 = x -> x < 0; //to far north
        Predicate<Integer> p3 = x -> x >= sideLength*sideLength; //too far south    
        //too far left
        Predicate<Integer> p4 = x -> head % sideLength == 0 && x % sideLength == sideLength - 1;  
        //too far right
        Predicate<Integer> p5 = x -> head % sideLength == sideLength - 1 && x % sideLength == 0;  //too far right

        //Block tiles
        Predicate<Integer> pb = x -> faces.get(x).equals("#");

        

        Predicate<Integer> p = ((((p1.or(p2)).or(p3)).or(p4)).or(p5)).or(pb);

        neighborListCopy.stream()
            .filter(p) 
            .forEach((x) -> { neighborList.remove(Integer.valueOf(x));}
            );
		return neighborList;
		
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
        if (pathString.length()<3){
            return false;
        }
		int index = binaryStringSearch(pathString,0,words.size()-1);
		return (index!=-1)?true:false;
	}
	
	
	//Determines if string is a substring (0,n) of 
	protected boolean isPartialWord(String pathString) {
		int index = binaryPartialStringSearch(pathString,0,words.size()-1);
		return (index!=-1)?true:false;
	}
		
	
	/**converts the numerical path to a string
	 * Since Redux introduces * character, pathToString 
     * no returns multiple strings
	 * @param numPath
	 * @return the string represented by the path
	 */
	private List<String> pathToString(List<Integer> numPath,List<String> faces){
        List<String> pathStrings = new ArrayList();
        pathStrings.add(""); 

        for (int i = 0; i < numPath.size(); i++) {
            List<String> copy = new ArrayList(pathStrings);
            pathStrings.clear();
            if (faces.get(numPath.get(i)).equals("*")) {
               for (String s : copy) {
                   for (int j = 97; j <= 122; j++) {
                       //only add the string if its potentially a real word
                       if (isPartialWord(s+Character.toString( (char) j))) {
                           pathStrings.add( s + Character.toString((char) j));  
                       }
                    }
               } 
            }
            else {
                for (String s : copy) {
                   pathStrings.add(s+faces.get(numPath.get(i))); 
                }
            }
        }
		return pathStrings;
	}
	
	
}
