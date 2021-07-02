package model.board;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ClassicBoard extends Board {

    
    public ClassicBoard() {
        this.size = 4;
        this.faces = generate(size);
    }
	
	public ClassicBoard(int size) {
        this.size = size;
        this.faces = generate(size);
	}

    public ClassicBoard(List<String> faces) {
       //infer size
       this.size = (int) Math.sqrt(faces.size());
       this.faces = faces;
    }

    @Override
    public Board clone() {
       List<String> facesCopy = new ArrayList<String>();
       facesCopy.addAll(this.faces);
       return new ClassicBoard(facesCopy);
    }
	

	//return a list of characters that encode the boggle instance
	protected List<String> generate(int size){
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
            case 3:
                dice = extendedDice(size);
                break;
            case 4:
                dice = original;
                break;
            case 5:
                dice = big; 
                break;
            case 6:
                break;
            default:
                if (size < 2) {
                    System.out.println("Error Boggle Boards must be atleast size 3");
                } else {
                    dice = extendedDice(size);
                }
                break;
        }
        Random rand = new Random();

		List<String> faces = new ArrayList<String>();
		//Roll the Dice: For each die, pick one of the six faces at random
		for(int i = 0; i < dice.size(); i++) {
            int pos = rand.nextInt(6);
			faces.add(dice.get(i).substring(pos,pos+1));
		}
		//Permute Faces: randomize position of selected faces
        //size is used here , this might be an issue...
		int idx1;
		int idx2;
        String c = "";
		for ( int i = 0; i < faces.size(); i++) {
			idx1 = i;
			idx2 = (rand.nextInt(faces.size()-i)+i);
			c = faces.get(idx1);
			faces.set(idx1, faces.get(idx2));
			faces.set(idx2, c);
		}
		return faces;
	}

    //when boards requested are bigger than actual boggle boards,
    //generate the amount dice required to construct a board
    protected List<String> extendedDice(int boardSize){
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
