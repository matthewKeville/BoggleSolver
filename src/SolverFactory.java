import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
public class SolverFactory {

    private List<String> words;
    public SolverFactory(String filePath) {
       this.words = wordFileToList(filePath); 
    } 

    public Solver getInstance(String type) {
        switch(type) {
            case "Classic":
                return new ClassicSolver(words);
            case "Redux":
                return new ClassicSolver(words);
            default: 
                return new ClassicSolver(words);
        }
    }



    //reads the words from the file pointed to by wordFilePath
    //into the List words
    private List<String> wordFileToList(String filePath) {
        List<String> words = new ArrayList();
        try {
        Stream<String> rows = Files.lines(Paths.get(filePath));
        words = rows
            .collect(Collectors.toList());
        rows.close();
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        return  words;
    }   


}
