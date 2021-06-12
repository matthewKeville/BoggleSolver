/*

The Solver interace has one method to 
implement : solve()
All rule related procedures will be handled
by the GameMaster class

*/
import java.util.List;
import java.util.Map;
public interface Solver {
  //return a map < List<Integer>, String > of all valid paths
  public Map<String,List<List<Integer>>> solve(Board board);
  //return a List<String> of all valid words
  public List<String> solveWords(Board board);
}
