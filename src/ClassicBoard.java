import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ClassicBoard implements Board {
	protected List<Character> letters; //the faces on the current board	
    protected int size;	
    protected String type;
	
	//Constructs a Board with random initialization
	public ClassicBoard(int size) {
		this.letters = generate(size);
        this.size = size;
        this.type = "Classic";
	}
	
    public ClassicBoard(String boardString) {
       //infer size
       this.size = (int) Math.sqrt(boardString.length());
       this.letters = new ArrayList<Character>();
       //tranlsate letters 
       for (int i = 0; i<boardString.length(); i++) {
        this.letters.add(boardString.charAt(i));
       }
       //set type
       this.type = "Classic";
    }

    public Board clone() {
       return new ClassicBoard(this.getBoardString()); 
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
	
    
}
