import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.Random;
import java.io.FileWriter;
public class Runner {
   public static void main(String[] args) {
     Game game = new Game();
     game.start();

     /*
     BoardPrinter bp = new BoardPrinter();
     BoardFactory bf = new BoardFactory();
     bp.setBoard(bf.getInstance(4));
     System.out.println(bp.prettyBoardString());
     bp.rotateRight();
     System.out.println(bp.prettyBoardString());
     bp.rotateLeft();
     System.out.println(bp.printBoardString());
     */     
     /*
     BoardFactory bf = new BoardFactory();
     Board test = bf.getInstance(4,"Redux");
     SolverFactory bsf = new SolverFactory("src/res/corncob_lowercase.txt");
     BoardPrinter bp = new BoardPrinter(test);
     Solver bs = bsf.getInstance("Redux");
     System.out.println(bp.getPrettyBoardDisplay());
     System.out.println(bs.solveWords(test));
     */ 
   } 

}
