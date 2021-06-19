package board;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LinksBoard extends ClassicBoard {

    public LinksBoard() {
        super(4);
        mutate();
    } 

    public LinksBoard(int size) {
        super(size);
        mutate();
	}
	
	public LinksBoard(List<String> faces) {
        super(faces);
	}

    @Override
    public Board clone() {
       return new LinksBoard(this.faces); 
    }

    //randomly inject special characters into the board
    // number of special characters will come from a normal 
    //distribuition with a mean of 1/4 the number of letters
    // and a standard deviation of 1
    public void mutate() {
        Random rand = new Random();
        double coverage = .10*faces.size();
        int sideLength = (int) Math.sqrt(faces.size());
        int specialTilesCount = (int) (rand.nextGaussian()*(coverage) + coverage);
        List<String> specialTiles = Arrays.asList("-","|","+"); //more to be added soon

        while (specialTilesCount != 0) {
          //pick a random index
          int tile = rand.nextInt(faces.size());
 
          //dont pick tiles in the outer board
          if (! (tile % sideLength == 0 || 
                tile % sideLength == sideLength-1 ||
                tile / sideLength ==  0 || 
                tile / sideLength ==  sideLength-1))   {
            //make sure it doesn't have a special tile
            //subsitute with random special
            String c = faces.get(tile);
            if ( !specialTiles.contains(c)) {
                String spc = specialTiles.get(rand.nextInt(specialTiles.size()));
                faces.set(tile,spc);
                specialTilesCount--;
            }

          } 
                       
 
        }
    }
	
    
}
