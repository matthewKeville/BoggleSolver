import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MusicBoard extends Board {

    public MusicBoard() {
        this.size = 4;
        this.faces = generate(size);
    } 
    	
	public MusicBoard(int size) {
		this.faces = generate(size);
        this.size = size;
	}
	
    public MusicBoard(List<String> faces) {
       //infer size
       this.size = (int) Math.sqrt(faces.size());
       this.faces = faces;
    }

    public Board clone() {
       List<String> facesCopy = new ArrayList<String>();
       facesCopy.addAll(this.faces);
       return new MusicBoard(facesCopy); 
    }
	
	//return a list of characters that encode the boggle instance
	private List<String> generate(int size)
    {
        List<String> faces = new ArrayList<String>();
        // a = A, A = A#
        List<String> notes = Arrays.asList("A","A#","B","C","C#","D","D#","E","F","F#","G","G#");
        Random rand = new Random();
        for (int i = 0; i < (int) Math.pow(size,2); i++) {
           faces.add(notes.get(rand.nextInt(12)));
        }
        return faces;

    }
	
	
    
}
