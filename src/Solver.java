/*

The Solver interace has one method to 
implement : solve()
All rule related procedures will be handled
by the GameMaster class

*/
import java.util.List;
import java.util.Map;
public interface Solver {
  public Map<List<Integer>,String> solve(Board board);
  //@precondition - solve has been called
  public List<String> getUniqueWords();
}
