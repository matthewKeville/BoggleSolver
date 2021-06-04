import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MusicBoard implements Board {
	protected List<Character> letters; //the faces on the current board	
    protected int size;	
    protected String type;
	
	//Constructs a Board with random initialization
	public MusicBoard(int size) {
		this.letters = generate(size);
        this.size = size;
        this.type = "Music";
	}
	
    public MusicBoard(String boardString) {
       //infer size
       this.size = (int) Math.sqrt(boardString.length());
       this.letters = new ArrayList<Character>();
       //tranlsate letters 
       for (int i = 0; i<boardString.length(); i++) {
        this.letters.add(boardString.charAt(i));
       }
       //set type
       this.type = "Music";
    }

    public Board clone() {
       return new MusicBoard(this.getBoardString()); 
    }
	
	//returns the Letters list of the board
	public List<Character> getLetters(){
		return letters;
	}
    //returns the Letters list as a string
    public String getBoardString() {
		String boardString="";
		for ( Character c : letters) {
			boardString+=c;
		}
		return boardString;
	}

    public void setLetters(List<Character> newLetters) {
        this.letters = newLetters;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void setType(String type) {
        this.type = type;
    }

	//return a list of characters that encode the boggle instance
	private List<Character> generate(int size)
    {
        List<Character> letters = new ArrayList<Character>();
        // a = A, A = A#
        List<Character> notes = Arrays.asList('a','A','b','c','C','d','D','e','f','F','g','G');
        Random rand = new Random();
        for (int i = 0; i < (int) Math.pow(size,2); i++) {
           letters.add(notes.get(rand.nextInt(12)));
        }
        return letters;

    }
	
	
    
}
