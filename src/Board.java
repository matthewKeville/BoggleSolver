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
	private List<Character> letters; //the faces on the current board	
	private Map<List<Integer>,String> solution;	//index paths and corresponding words
	private List<String> uniqueWords;			//list of all unique words
    private int size;	
	
	//Constructs a Board with random initialization
	public Board(int size) {
		this.letters = generate(size);
		this.uniqueWords = null;
		this.solution = null;
        this.size = size;
	}
	
	//Constructs a board from a a set of letters
    //old cons , needs to be redone
	public Board(String boardString) {
		letters = new ArrayList<Character>();
		int stringLength = boardString.length();
		for (int i = 0; i < stringLength; i++) {
			letters.add(boardString.charAt(i));
		}
		this.uniqueWords = null;
		this.solution = null;
        this.size = 4;
	}
	
	//returns the Letters list of the board
	public List<Character> getLetters(){
		return letters;
	}

    public String printBoardString(){
        String result="";
        for (char x : letters) {
          result+=x; 
        }
        return result;
    }	

	//print the board in a 4x4 grid
    //old print needs to be redone ..
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

    public String prettyBoardString(){
        String topBase =  "┌────";
        String midBase =   "| ──┼"; 
        String botBase =  "└────";
        

     
        String midExtend = "───┼";
        String Extend    = "────";
        
        String topEnd =  "───┐";
        String midEnd =   "── |"; 
        String botEnd =  "───┘";

        int extension = this.size - 2; 
        String top = topBase;
        String mid = midBase;
        String bot = botBase;
        for (int i =0; i < extension; i++){
            top += Extend;
            mid += midExtend;
            bot += Extend;
        }
        top+=topEnd;
        mid+=midEnd;
        bot+=botEnd;
        //String top =   "┌───────────────┐";
        //String mid = "| ──┼───┼───┼── |";
        //String bot =   "└───────────────┘"; 

        String board = "" + top;
        for (int i = 0; i < this.size; i++) {

            String rowString = "|";
            for (int j = 0; j < this.size; j++) { 
                String let = Character.toString(letters.get((i*this.size)+j));
                if (let.equals("q")) {
                    rowString+=" Qu";
                }
                else{
                    rowString+=" ";
                    rowString+=let;
                    rowString+=" ";
                }
                if (i!=0 || i!=this.size-1){
                    rowString+="|";
                }
            }
            board+="\n";
            board+=rowString;
            board+="\n";
            if (i!=this.size -1){
                board+=mid;
            }
        
        }
        board+=bot;
        return board;
    }

    //rotate the board 90 degrees clockwise/
    // l00 l01 l02
    // l03 l10 l04
    // l05 l06 l07
    //each board has i innner layers, in which we make 4 substitutions
    //this is probably a bad solution...
    //but it works
    // there are size // 2 inner layers with more than 1 element
    //those we swap the 4 sides for as many inner layers as there are
    //we load each side into a buffer and then swap
    public void rotate() {
        // integer division is the number of inner layers that need to rotate
        int innerRotations = size /  2;
        char[] swapNorth = new char[size];
        char[] swapEast = new char[size];
        char[] swapSouth = new char[size];
        char[] swapWest = new char[size];

        //for each layer
        for (int i=0; i < innerRotations; i++) { 
            //fill swap buffer
            System.out.println("\n Layer" + i);
            for (int k=0; k < size-(i*2); k++) {
                swapNorth[k] = letters.get(i+(i*size)+k);
                swapEast[k] = letters.get( (size-1)*(i+1)+size*k );
                swapSouth[k] = letters.get( (size-i)*size-(1+i)-k);
                swapWest[k] =  letters.get( (size-1)*(size-i)-(size*k));

            }
            //System.out.println("North" + Arrays.toString(swapNorth)); 
            //System.out.println("East" + Arrays.toString(swapEast)); 
            //System.out.println("South" + Arrays.toString(swapSouth));  
            //System.out.println("West" + Arrays.toString(swapWest)); 

            //put North in East, East in South ...
            for (int k=0; k < size-(i*2); k++) {
                letters.set((size-1)*(i+1)+size*k,swapNorth[k]); //put North in East
                letters.set( (size-i)*size-(1+i)-k,swapEast[k]); //put East in South
                letters.set( (size-1)*(size-i)-(size*k),swapSouth[k]); //put South in West
                letters.set(i+(i*size)+k,swapWest[k]);
            }
        }
    }

    //remove	
	//assigns the board a solution and creates a the unique words list
	public void setSolution(Map<List<Integer>,String> solution) {
		this.solution = solution;
		createUniqueWords();
	}
	
    //move to BoardSolver
	/**@precondition setSolution has been called
	 */
	public void printUniqueWords() {
		for (String s: uniqueWords) {
			System.out.println(s);
		}
	}

    //remove	
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
	private List<Character> generate(int size){
		        //4x4 boggle
		List<String> original = 
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
        //5x5 boggle
        List<String> big = 
            Arrays.asList(
                            "qbzjxk",
                            "touoto",
                            "ovwrgr",
                            "aaafsr",
                            "aumeeg",
                            "hhlrdo",
                            "nhdthc",
                            "lhnrod",
                            "afaisr",
                            "yifasr",
                            "telpci",
                            "ssnseu",
                            "riyprh",
                            "dordln",
                            "ccwnst",
                            "ttotem",
                            "sctiep",
                            "eandnn",
                            "mnneag",
                            "uotown",
                            "aeaeee",
                            "yifpsr",
                            "eeeema",
                            "ititie",
                            "etilic"
                                            );
        List<String> dice = new ArrayList<String>();
        switch(size) {
            case 4:
                dice = original;
                break;
            case 5:
                dice = big; 
                break;
            case 6:
                break;
            default:
                if (size < 4) {
                    System.out.println("Error Boggle Boards must be atleast size 4");
                } else {
                    dice = extendedDice(size);
                }
                break;
        }

        Random rand = new Random();
		List<Character> letters = new ArrayList<Character>();

		//Roll the Dice: For each die, pick one of the six faces at random
		for(int i = 0; i < dice.size(); i++) {
			letters.add(dice.get(i).charAt(rand.nextInt(6)));
		}
		
		//Permute Faces: randomize position of selected faces
        //size is used here , this might be an issue...
		int idx1;
		int idx2;
		char c;
		for ( int i = 0; i < letters.size(); i++) {
			idx1 = i;
			idx2 = (rand.nextInt(letters.size()-i)+i);
			c = letters.get(idx1);
			letters.set(idx1, letters.get(idx2));
			letters.set(idx2, c);
		}
		return letters;
	}

    //when boards requested are bigger than actual boggle boards,
    //generate the amount dice required to construct a board
    private List<String> extendedDice(int boardSize){
       int diecount = (int) Math.pow(boardSize,2);
       List<String> dice = new ArrayList<String>(); 
       for ( int i = 0; i < diecount; i++) {
            String die = "";
            for (int j = 0; j < 6; j++ ) {
                Random rand = new Random();
                //lowercase Ascii [97,122]
                die+= Character.toString( (char) rand.nextInt(26)+97);
            }
            dice.add(die);
       } 
       return dice;
    }
	
    //move .. 	
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
