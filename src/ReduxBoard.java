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
        this.type = "Classic";
	}
	
	//Constructs a board from a a set of letters
	public ReduxBoard(String boardString) {
        super(boardString);
	}

    //return a duplicate object
    public Board clone() {
       return new ReduxBoard(getBoardString()); 
    }

    //randomly inject special characters into the board
    public void mutate() {
        Random rand = new Random();
        int specialTiles = rand.nextInt(5) + 1;
        while (specialTiles != 0) {
          //pick a random index
          int tile = rand.nextInt(letters.size())
          //make sure it doesn't have a special tile
          //subsitute with random special
          Character c = letters.get(tile);
          if ( !c.equals('#') && !c.equals('*') ) {
            Character spc = rand.nextInt(2) % 2 == 0 ? '#' : '*';
            letters.set(tile,spc);
            specialTiles--;
          }             
 
        }
    }
	
    
}
