import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Arrays;
public class Runner {

    private static Board board;
    private static Solver boardSolver;
    private static List<String> userWords = new ArrayList<String>();  //store user words
    private static List<String> solution = new ArrayList<String>();
    private static int mode;  //0 dev , 1 timed
    private static int size = 4;
    private static long timeStart = System.currentTimeMillis();
    private static int durationInSeconds = 30;
    private static int duration = 1000*durationInSeconds;    
    private final static int maxSize = 30;
    private final static int minSize = 4;

    //clear the terminal 
    public static void clrScreen(){
        System.out.println("\033\143");
    } 

    //given a list of valid response strings 
    //query user input until a valid response is given by the user
    //and return the valid response
    public static String queryResponse(List<String> responses,String options) {
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
    public static void shakeAndSolve(int size){
        board = new Board(size);	
		boardSolver.solveBoard(board); //numeric paths
        solution = boardSolver.createUniqueWords(); //character representations
    }
    
    public static void renderPlayScreen() {
        String header = "[1] rotate left\t [2] rotate right\t[3] check time\t[4] quit";
        String body = "Words Found\n --------------\n";
        Iterator i = (Iterator) userWords.iterator();
        while (i.hasNext()){
            body+= i.next() + "\n";
        }

        clrScreen();
        System.out.println(header);
        System.out.println(board.prettyBoardString());
        System.out.println(body);
    
    }

    public static void renderEndGame() { 
        String body = "\n ------- Words Found -------\n";
        Iterator i = (Iterator) userWords.iterator();
        int score = 0;
        while (i.hasNext()){
            String n = (String) i.next();
            int wl = n.length();
            body+= n + "\n";
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
        System.out.println(board.prettyBoardString());
        System.out.println("Game Over");
        System.out.printf("Your Score : %d%n",score);        
        System.out.printf("Percent Of Words Found :  %.2f",percent);
        System.out.println(body);
        System.out.println("Press enter to continue");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }

    //todo
    //this method will return the list of plurizations missed by the user
    //hard because there is more than one formula for pluralization
    // s , es, , ies, ous, 
    public static List<String> missedPlurals(List<String> user, List<String> solution) {
        List<String> missed = new ArrayList<String>();
        return missed;
    }
    
    //todo
    public static List<String> missedSuper(List<String> user, List<String> solution) {
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
    public static List<String> missedSubs(List<String> user, List<String> solution) {
        List<String> missed = new ArrayList<String>();
        return missed;
    }


    
	public static void main(String[] args) {
        //setupt solver
		String filePATH = "src/res/corncob_lowercase.txt";
		boardSolver = new Solver(filePATH);	
        //create and solve new board
        shakeAndSolve(size); 
			
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        while(running){ 
            //free mode
            if (mode==0) {
                List<String> mainResponses = Arrays.asList(new String[]{"q","s","r","t","c","p"});
                String query =  board.prettyBoardString() + "\n" +  
                                "[s] Print out the solutions\n" +
                                "[r] Randomize the board\n" +
                                "[t] Enter timed mode \n" +
                                "[c] change board size\n" +
                                "[q] Quit\n" +
                                "[p] rotate";
                String choice = queryResponse(mainResponses,query);
                switch(choice) {
                        case "q":
                            System.exit(0);
                            break;
                        case "s":
                            for (String s : solution) {
                                    System.out.println(s);
                            }
                            System.out.println("Enter to return to board");
                            scan.nextLine();
                            break;
                        case "r":
                            shakeAndSolve(size);
                            System.out.println(board.prettyBoardString());
                            break;
                        case "t":
                            mode = 1;
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
                            System.out.println(board.prettyBoardString());
                            break;
                        case "p":
                            board.rotate();
                            break;
            }

            //timed mode
            }else{
                shakeAndSolve(size);
                List<String> timedResponses = Arrays.asList(new String[]{"s","r","f"});
                String timedQuery =  "You have entered timed mode\n" +
                                     "Current Board Density : " + solution.size() + "\n" +
                                     "[s] Start\n" +
                                     "[r] Randomize the board\n" +
                                     "[f] Return to free mode\n";
                String timedChoice = queryResponse(timedResponses,timedQuery);
                switch(timedChoice){
                    case "s":
                        boolean play = true;
                        userWords.clear();
                        timeStart = System.currentTimeMillis();
                        renderPlayScreen();
                        while (System.currentTimeMillis() - timeStart < duration && play) {
                            String ans = scan.nextLine();
                            //if ans is numeric than its using the menu
                            if (ans.matches("[1-9]")) {
                                switch(ans){
                                    case "1":
                                        board.rotate();
                                        renderPlayScreen();
                                        break;
                                    case "2":
                                        board.rotate();
                                        board.rotate();
                                        board.rotate();
                                        renderPlayScreen();
                                        break;
                                    case "3":
                                        //....
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
                        renderEndGame();
                        break;
                    case "r":
                        shakeAndSolve(size);
                        break;
                    case "f":
                        mode=0;
                        break; 
              } 
                
            }
    
        }
	 
	}
    
}
