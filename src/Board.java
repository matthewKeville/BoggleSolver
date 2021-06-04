import java.util.List;
/*
 * a boggle board is a square board of characters
 * 
 * As of JDK 8 interfaces support default implementations
 *  
 *  
 */
public interface Board {
	public String getType();
    public String getBoardString();
    public int getSize();
    public List<Character> getLetters();
    public void setLetters(List<Character> newLetters);
        
}
