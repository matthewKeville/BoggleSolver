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
public class Game{

    private Board board;
    private Board prevBoard;
    private ClassicSolver boardSolver;
    private List<String> userWords; 
    private List<String> solution;
    private List<String> prevSolution;
    private boolean timed;
    private int size;
    private long timeStart;
    private int durationInSeconds;
    private int duration;    
    private final int maxSize = 30;
    private final int minSize = 4;

    public Game() {
      userWords = new ArrayList<String>();  //store user words
      solution = new ArrayList<String>();
      //board creation
      size = 4;
      boardSolver = new ClassicSolver("src/res/corncob_lowercase.txt");
      shakeAndSolve(size);
      //state management
      timed = true;
      timeStart = System.currentTimeMillis();
      durationInSeconds = 30;
      duration = 1000*durationInSeconds;    

    }

    //clear the terminal  | only works in terminal ...
    public void clrScreen(){
        System.out.println("\033\143");
    } 

    //given a list of valid response strings 
    //query user input until a valid response is given by the user
    //and return the valid response
    public String queryResponse(List<String> responses,String options) {
        clrScreen();
        System.out.println(options);
        Scanner scan = new Scanner(System.in);
        String ans = scan.next();
        while (!responses.contains(ans)) {
            clrScreen();
            System.out.println("Invalid Option");
            System.out.println("Please Select one below");
            System.out.println(options);
            ans = scan.next().substring(0,1);
        }
        return ans;
    }
    
    //randomize the boggle board and solve it
    public void shakeAndSolve(int size){
        board = new ClassicBoard(size);	
		boardSolver.solve(board); //numeric paths
        solution = boardSolver.createUniqueWords(); //character representations
    }
    
    public void renderPlayScreen() {
        String header = "[1] rotate left\t [2] rotate right\t[3] check time\t[4] quit";
        String body = "Words Found\n --------------\n";
        body+=formattedWords(userWords);
        clrScreen();
        System.out.println(header);
        //System.out.println(board.prettyBoardString());
        System.out.println(body);
    
    }

    public void renderInspectScreen() {
        String header = "[1] rotate left\t [2] rotate right\t[3] exit\t";
        String body = "All Words\n --------------\n";
        body+=formattedWords(prevSolution);
        clrScreen();
        System.out.println(header);
        //System.out.println(board.prettyBoardString());
        System.out.println(body);
    
    }

    public void renderEndGame() { 
        String body = "\n ------- Words Found -------\n";
        body += formattedWords(userWords); 
        Iterator i = (Iterator) userWords.iterator();
        int score = 0;
        while (i.hasNext()){
            String n = (String) i.next();
            int wl = n.length();
            switch (wl) {
                case 3:
                    score+=1;
                    break;
                case 4:
                    score+=1;
                    break;
                case 5:
                    score+=2;
                    break;  
                case 6:
                    score+=3;
                    break;
                case 7:
                    score+=4;
                    break;
                case 8:
                    score+=11;
                    break;
                default:
                    if (wl>8){      
                        score+=11; 
                    }
                    break;
            }
        }
        double percent =  (double) userWords.size() / (double) solution.size();
        clrScreen(); 
        //System.out.println(board.prettyBoardString());
        System.out.println("Game Over");
        System.out.printf("Your Score : %d%n",score);        
        System.out.printf("Percent Of Words Found :  %.2f",percent);
        System.out.println(body);
        System.out.println("Press enter to return to the main menu");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
       
    //given a list of strings 
    //return a string formatted with columns being word length buckets 
    //3   |4    |5     |6      |  7+
    //cat |bark |ships |lights |
    //dog |ship |tight |       |
    public String formattedWords(List<String> words){
        //set up a list of empty string list for each word bin
        List<List<String>> bins = new ArrayList<List<String>>();
        for (int i = 0; i < 5; i++) {
            bins.add(new ArrayList<String>());
        }
        //sort words into bins
        Iterator itty = (Iterator) words.iterator();
        while (itty.hasNext()) {
            String w = (String) itty.next();
            switch (w.length()){
                case 3:
                    bins.get(0).add(w);
                    break;
                case 4:
                    bins.get(1).add(w);
                    break;
                case 5:
                    bins.get(2).add(w);
                    break;
                case 6: 
                    bins.get(3).add(w);
                    break;
                case 7:
                    bins.get(4).add(w);
                    break;
                default:
                    bins.get(4).add(w);
                    break; 
            }    
        }
        //define custom comparator to soft by length then value
        class LengthComp implements Comparator<String> {
            public int compare(String s1, String s2) {
                int res = s1.length() - s2.length(); 
                return res;
            }
        }

        for (int i=0; i<4; i++) {
            Collections.sort(bins.get(i));
        }

        //sort 7+ by alph then size   
        Collections.sort(bins.get(4));
        Collections.sort(bins.get(4),new LengthComp());
        //find the bin with the most words
       int maxndx = 0;
       for (int i = 0; i<5; i++) {
            if (bins.get(i).size() > bins.get(maxndx).size()) {
                maxndx=i; 
            }
       }
       int max = bins.get(maxndx).size();
       String buffer = "3   |4    |5     |6      |7\n";

       for ( int i=0; i < max; i++){
           String row = "";
           for ( int j=0; j < 5; j++) {
                if (bins.get(j).size() != 0) {
                    row+=bins.get(j).remove(0)+" |"; 
                } else {
                    String fill = "";
                    for (int k=0; k < j+3+1; k++) {
                        fill+=" ";
                    }
                    fill+="|";   
                    row+=fill; 
                }
           }
           row+="\n";
           buffer+=row;
        } 
        return buffer;
        
    }

    //todo
    //this method will return the list of plurizations missed by the user
    //hard because there is more than one formula for pluralization
    // s , es, , ies, ous, 
    public List<String> missedPlurals(List<String> user, List<String> solution) {
        List<String> missed = new ArrayList<String>();
        return missed;
    }
    
    //todo
    public List<String> missedSuper(List<String> user, List<String> solution) {
        List<String> missed = new ArrayList<String>();
        for (String w : userWords) {
            String miss = "";
            for (String s : solution) {
                if (1==0) {
                    
                }
            }
        }
        return missed;

    }

    //todo
    public List<String> missedSubs(List<String> user, List<String> solution) {
        List<String> missed = new ArrayList<String>();
        return missed;
    }

    //Launch the main menu
    public void start() {
      Scanner scan = new Scanner(System.in);
      boolean running = true;
      while(running) {
        List<String> menuOptions = Arrays.asList(new String[]{"p","l","i","q"});
                String query =  "Welcome to Keville's Boggle Client\n" +
                                "----------------------------------\n" +
                                "[p] Play\n" +
                                "[l] Inspect Last Game\n" +
                                "[i] Info\n"  +
                                "[q] Quit\n"; 
                String choice = queryResponse(menuOptions,query);
                switch(choice) {
                          case "p":
                            //play();
                            settings();
                            break;
                          //only show if lastGame is not null
                          case "l":
                            if (prevBoard == null) {
                              clrScreen();
                              System.out.println("You must play a game, before using this feature");
                              scan.nextLine();
                            } else {
                              inspectLast();    
                            }
                            break;
                          case "i":
                            clrScreen();
                            System.out.println("The BoggleSolver Project" +
                            " was developed by Matthew Keville");
                            scan.nextLine(); 
                            break;
                          case "q":
                            System.exit(0);
                            break;
        }
      }
    }

    //Adjust the Game Settings
    public void settings() {
      Scanner scan = new Scanner(System.in);
      boolean running = true;
      while(running){ 
      //free mode
       List<String> menuOptions = Arrays.asList(new String[]{"s","m","t","c","v","r"});
       String query = "Current Game Settings\n" +
                      "-------------------\n" + 
                      "Board Size : " + size + "\n" +
                      "Board Density : N/A" + "\n"  +
                      "Time Mode : " + (timed ? "[ON]" : "[OFF]") + "\n" +
                      "-------------------\n" + 
                      "[s] Start\n" +
                      "[m] Mix The Board\n" +
                      "[t] Toggle Timer \n" +
                      "[c] Change board size\n" +
                      "[v] Change Render Size\n" +
                      "[r] Return";
        String choice = queryResponse(menuOptions,query);
        switch(choice) {
                case "s":
                    play();
                    //break out of options loop and 
                    //return to the main menu
                    running = false;
                    break;
                case "m":
                    shakeAndSolve(size);
                    //System.out.println(board.prettyBoardString());
                    break;
                case "t":
                    timed = false;;
                    break;
                case "c":
                    List<String> sizeResponses = new ArrayList<String>();
                    for (int i=0; i < maxSize-minSize; i++){
                        sizeResponses.add(""+(i+minSize));
                    }

                    String sizeQuery = "Pick the new board size" +
                                   "[4] 4x4 Original \n" +
                                   "[5] 5x5 Big      \n" +
                                   "[6] 6x6 Xtra     \n" +
                                   "[n] nxn where " + minSize + " < n < " + maxSize;
                    String newSize = queryResponse(sizeResponses,sizeQuery); 
                    System.out.println("Board size set to " + newSize);
                    size = Integer.parseInt(newSize); 
                    shakeAndSolve(size);
                    //System.out.println(board.prettyBoardString());
                    break;
                case "r":
                    return;
       }
      }
    }

    //Launch a game
	public void play() {
        //setupt solver
		String filePATH = "src/res/corncob_lowercase.txt";
		boardSolver = new ClassicSolver(filePATH);	
        //create and solve new board
        shakeAndSolve(size); 
        Scanner scan = new Scanner(System.in);
        boolean play = true;
        //reset game variables
        userWords.clear();
        timeStart = System.currentTimeMillis();

        while (play && (!timed || System.currentTimeMillis() - timeStart < duration)) {
            renderPlayScreen();
            String ans = scan.nextLine();
            //if ans is numeric than its using the menu
            if (ans.matches("[1-9]")) {
              switch(ans){
                case "1":
                  //board.rotate();
                  renderPlayScreen();
                  break;
                case "2":
                  //board.rotate();
                  //board.rotate();
                  //board.rotate();
                  renderPlayScreen();
                  break;
                 case "3":
                   clrScreen();
                   if (!timed ) {
                     System.out.println("Infinity");
                     scan.nextLine();
                   }
                   else {
                     double timeInSeconds = (System.currentTimeMillis() - timeStart)/((double)1000);
                     int remainingSeconds = ((int) (durationInSeconds - timeInSeconds));
                     System.out.println(remainingSeconds);
                     scan.nextLine();
                   }
                   break;
                 case "4":
                   play = false;
                   break;
                }
            }
            else if (System.currentTimeMillis() - timeStart < duration) {

                    if (solution.contains(ans) && !userWords.contains(ans)){
                        userWords.add(ans);     
                        System.out.println("Nice : added " + ans);                   
                    } else {
                        System.out.println("Not a word");
                    }
                    renderPlayScreen();
            }
            else {

            }
         }
         //Times Up Spongebob
         prevBoard = board;
         prevSolution = solution;
         renderEndGame();
    }

    
    public void inspectLast() {
        boolean viewing = true;
        Scanner scan = new Scanner(System.in);
        while (viewing) {
          renderInspectScreen();
          String ans = scan.nextLine();
          //if ans is numeric than its using the menu
          if (ans.matches("[1-9]")) {
            switch(ans){
              case "1":
                //board.rotate();
                ;
                break;
              case "2":
                ;
                //board.rotate();
                //board.rotate();
                //board.rotate();
                break;
              case "3":
                viewing = false;
                break;
           }
         } else {

         }
       }
       ////// 
    }
    
}
