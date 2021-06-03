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

    private final static int maxSize = 30;
    private final static int minSize = 4;
    
	public static void main(String[] args) {
        String wordFilePath = "";
        Board board;
        ClassicSolver boardSolver;
        switch(args[0]) {
                     
            case "make":
                int size = Integer.parseInt(args[1]);            
                board = new Board(size);
                System.out.println(board.printBoardString());
                break;
            //solve only works for 4x4 as Board(String boardString) 
            //constructor only supports 4x4 board
            case "solve":
                board = new Board(args[1]);
                boardSolver = new ClassicSolver("src/res/corncob_lowercase.txt");
                boardSolver.solve(board);
                System.out.println(boardSolver.createUniqueWords());
                break;
            //provide solution map to std out
            case "solution":
                board = new Board(args[1]);
                boardSolver = new ClassicSolver("src/res/corncob_lowercase.txt");
                //hashmap of paths and words
                Map<List<Integer>, String> solution = boardSolver.solve(board);
                System.out.println(solution);
                break;

            case "pretty":
                board = new Board(args[1]);
                System.out.println(board.prettyBoardString());
                break;
            case "check":
                board = new Board(args[1]);
                String potential = args[2];
                boardSolver = new ClassicSolver("src/res/corncob_lowercase.txt");
                boardSolver.solve(board);
                System.out.println(boardSolver.createUniqueWords().contains(potential));
                break; 
        }
    }
}
