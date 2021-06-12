import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ReduxBoard extends ClassicBoard {

    public ReduxBoard() {
        super(4);
        mutate();
    } 

    public ReduxBoard(int size) {
        super(size);
        mutate();
	}
	
	public ReduxBoard(List<String> faces) {
        super(faces);
	}

    @Override
    public Board clone() {
       return new ReduxBoard(this.faces); 
    }

    //randomly inject special characters into the board
    // number of special characters will come from a normal 
    //distribuition with a mean of 1/4 the number of letters
    // and a standard deviation of 1
    public void mutate() {
        Random rand = new Random();
        //int specialTiles = rand.nextInt(5) + 1;
        double coverage = .10*faces.size();
        int specialTiles = (int) (rand.nextGaussian()*(coverage) + coverage);
        while (specialTiles != 0) {
          //pick a random index
          int tile = rand.nextInt(faces.size());
          //make sure it doesn't have a special tile
          //subsitute with random special
          String c = faces.get(tile);
          if ( !c.equals("#") && !c.equals("*") ) {
            String spc = rand.nextInt(2) % 2 == 0 ? "#" : "*";
            faces.set(tile,spc);
            specialTiles--;
          }             
 
        }
    }
	
    
}
