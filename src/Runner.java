import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
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

    //clear the terminal 
    public static void clrScreen(){
        System.out.println("\033\143");
    } 

    public static void printHelp(){
        System.out.println("[h] Print this menu");
        System.out.println("[b] Return to the board");
        System.out.println("[s] Print out the solutions");
        System.out.println("[r] Randomize the board");
        System.out.println("[t] Enter timed mode ");
        System.out.println("[c] change board size");
        System.out.println("[q] Quit");
        
    }

    //randomize the boggle board and solve it
    public static void shakeAndSolve(int size){
        board = new Board(size);	
		boardSolver.solveBoard(board); //numeric paths
        solution = boardSolver.createUniqueWords(); //character representations
    }
    
    public static void renderPlayScreen() {
        String body = "Words Found\n --------------\n";
        Iterator i = (Iterator) userWords.iterator();
        while (i.hasNext()){
            body+= i.next() + "\n";
        }

        clrScreen();
        board.prettyPrintBoard();
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
        
        clrScreen(); 
        board.prettyPrintBoard();
        System.out.println("Game Over");
        System.out.printf("Your Score : %d%n",score);
        double percent =  (double) userWords.size() / (double) solution.size();
        System.out.printf("Percent Of Words Found :  %.2f",percent);
        System.out.println(body);
    }
    
	public static void main(String[] args) {
        //setupt solver
		String filePATH = "src/res/corncob_lowercase.txt";
		boardSolver = new Solver(filePATH);	
        //create and solve new board
        shakeAndSolve(size); 
		//Board board = new Board("hefodeilwvacstql"); //supposed best 4x4 from online post
			
        Scanner scan = new Scanner(System.in);
        boolean running = true;

        List options = new ArrayList<String>();	

        printHelp();
        while(running){ 
                        //dev / free mode
            if (mode==0) {
                //next reads in newest token, but we only want the first character
                String choice = scan.next().substring(0,1);
                clrScreen();
                    switch(choice) {
                        case "q":
                            System.exit(0);
                            break;
                        case "s":
                            for (String s : solution) {
                                    System.out.println(s);
                            }
                            break;
                        case "h":
                            printHelp();
                            break;
                        case "b": 
                            board.prettyPrintBoard();
                            break;
                        case "r":
                            shakeAndSolve(size);
                            board.prettyPrintBoard();
                            break;
                        case "t":
                            mode = 1;
                            System.out.println("You have entered timed mode" +
                                "\n Press Enter to begin!");
                            shakeAndSolve(size);
                            scan.nextLine(); //eat a newline
                        case "c":
                            System.out.println("Pick the new board size:" +
                                "\n[4] 4x4 Original \n[5] 5x5 Big");
                            
                            int newSize = scan.nextInt();
                            clrScreen();
                            while (newSize < 4 && newSize > 6) {
                                System.out.println("Invalide Size");
                                System.out.println("Please Enter an Integer between [4,6]");
                                newSize = scan.nextInt();
                                clrScreen();
                            }
                            System.out.println("Board size set to " + newSize);
                            size = newSize; 
                            shakeAndSolve(size);
                            board.prettyPrintBoard();
                            break;
                    }
            //timed mode -> Upon enter, show rules
            //all input is interpreted as word  submissions until time runs out
            }else{
                timeStart = System.currentTimeMillis();
                while (System.currentTimeMillis() - timeStart < duration) {
                    String ans = scan.nextLine();
                    if (System.currentTimeMillis() - timeStart < duration) {
                            if (solution.contains(ans) && !userWords.contains(ans)){
                                userWords.add(ans);     
                                System.out.println("Nice : added " + ans);                   
                            } else {
                                System.out.println("Not a word");
                            }
                            renderPlayScreen();
                    }
                }
                renderEndGame();
                mode = 0;
            }

    
        }
	 
	}

    
}
