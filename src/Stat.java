import java.io.FileWriter;
import java.io.IOException;
public class Stat {


    //what is the distribution of board density?
    //what is the distribution of board solve time?
    //how does density affect solve time?
    //average density?
    //average solve?
    

    public static void main(String[] args) {
        int dp = 1000;
        //densityTest(dp,4);
        //densityTest(dp,5);
        //densityTest(dp,10);
        //densityTest(dp,100);
        densityTest(dp,1000);
    }


    //analyze the distribuition of board density
    //and the solve time
    public static void densityTest(int dp,int size) {  
        Solver boardSolver = new Solver("src/res/corncob_lowercase.txt");
        try {
            String fileName = "data"+size+".csv";
            FileWriter csvWriter = new FileWriter(fileName);
            for ( int i=0; i< dp; i++ ) {
                Board board = new Board(size);
                long start = System.nanoTime();
                boardSolver.solveBoard(board);
                long finish = System.nanoTime();
                long elapsed = finish - start;               
                int den = boardSolver.createUniqueWords().size();
                //density, elapsed time 
                csvWriter.append(""+den+","+elapsed+"\n");
            }
            csvWriter.flush();
            csvWriter.close();        
        } catch(IOException e) {
            System.out.println("Error");
            System.exit(0);
        } 
    }

    


}
