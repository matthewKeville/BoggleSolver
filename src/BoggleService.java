import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.Random;
import java.util.Map;
public class BoggleService {

    private static Board board;
    private static Solver boardSolver;
    private static List<String> solution = new ArrayList<String>();
    private static int mode;  //0 dev , 1 timed
    private static long timeStart = System.currentTimeMillis();
    private static int durationInSeconds = 30;
    private static int duration = 1000*durationInSeconds;    
    private final static int maxSize = 30;
    private final static int minSize = 4;
    
    //randomize the boggle board and solve it
    public static void shakeAndSolve(int size){
        board = new Board(size);	
		boardSolver.solveBoard(board); //numeric paths
        solution = boardSolver.createUniqueWords(); //character representations
    }
    
   
    /* This will have two services, the service calls should allow for
       specification of word file. The two services will be board creation
       and board solving.    
    */ 
	public static void main(String[] args) {
        String wordFilePath = "";
        switch(args[0]) {
                     
            case "make":
                int size = Integer.parseInt(args[1]);            
                board = new Board(size);
                //System.out.println(board.getLetters());
                System.out.println(board.printBoardString());
                break;
            //solve only works for 4x4 as Board(String boardString) 
            //constructor only supports 4x4 board
            case "solve":
                board = new Board(args[1]);
                wordFilePath = args[2];
                boardSolver = new Solver(wordFilePath);
                boardSolver.solveBoard(board);
                System.out.println(boardSolver.createUniqueWords());
                break;
            //provide solution map to std out
            case "solution":
                board = new Board(args[1]);
                wordFilePath = args[2];
                boardSolver = new Solver(wordFilePath);
                //hashmap of paths and words
                Map<List<Integer>, String> solution = boardSolver.solveBoard(board);
                System.out.println(solution);
                break;

            case "pretty":
                board = new Board(args[1]);
                System.out.println(board.prettyBoardString());
                break;
            case "check":
                board = new Board(args[1]);
                String potential = args[2];
                wordFilePath = args[3];
                boardSolver = new Solver(wordFilePath);
                boardSolver.solveBoard(board);
                System.out.println(boardSolver.createUniqueWords().contains(potential));
                break; 
        }
    }
}
