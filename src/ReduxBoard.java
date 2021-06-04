import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ReduxBoard extends ClassicBoard {
    //Constructs a Board with random initialization
	public ReduxBoard(int size) {
        super(size);
        super.type = "Redux";
        mutate();
	}
	
	//Constructs a board from a a set of letters
	public ReduxBoard(String boardString) {
        super(boardString);
        super.type = "Redux";
	}

    //return a duplicate object
    public Board clone() {
       return new ReduxBoard(getBoardString()); 
    }

    //randomly inject special characters into the board
    // number of special characters will come from a normal 
    //distribuition with a mean of 1/4 the number of letters
    // and a standard deviation of 1
    public void mutate() {
        Random rand = new Random();
        //int specialTiles = rand.nextInt(5) + 1;
        double coverage = .20*super.letters.size();
        int specialTiles = (int) (rand.nextGaussian()*(coverage) + coverage);
        while (specialTiles != 0) {
          //pick a random index
          int tile = rand.nextInt(super.letters.size());
          //make sure it doesn't have a special tile
          //subsitute with random special
          Character c = super.letters.get(tile);
          if ( !c.equals('#') && !c.equals('*') ) {
            Character spc = rand.nextInt(2) % 2 == 0 ? '#' : '*';
            super.letters.set(tile,spc);
            specialTiles--;
          }             
 
        }
    }
	
    
}
