/*

  To allow for more customization of board representation 
  the ascii printing that was tied to the board class is 
  be moved into a BoardPrinter class
  The BoardPrinter, keeps track of rotations. This way if a
  future feature is implemented that demonstrates a word path
  we can revert the board back to its intial config, or convert
  the path to the augmented board

   could allow for semi rotations, that is diagnol representation

*/
import java.util.List;
public class BoardPrinter {
    private Board board;
    private int rotation; // 0 - 3 = no , right, 2*right, 3*right

    public BoardPrinter() {};

    public BoardPrinter(Board board){
      this.board = board;
    } 

    public void setBoard(Board board) {
      this.board = board;
    }


    //each board has i innner layers, in which we make 4 substitutions
    // there are size // 2 inner layers with more than 1 element
    //those we swap the 4 sides for as many inner layers as there are
    //we load each side into a buffer and then swap
    public void rotateRight() {
        List<Character> letters = board.getLetters();
        // integer division is the number of inner layers that need to rotate
        int innerRotations = board.getSize() /  2;
        char[] swapNorth = new char[board.getSize()];
        char[] swapEast = new char[board.getSize()];
        char[] swapSouth = new char[board.getSize()];
        char[] swapWest = new char[board.getSize()];

        //for each layer
        for (int i=0; i < innerRotations; i++) {
            //fill swap buffer
            for (int k=0; k < board.getSize()-(i*2); k++) {
                swapNorth[k] = letters.get(i+(i*board.getSize())+k);
                swapEast[k] = letters.get( (board.getSize()-1)*(i+1)+board.getSize()*k );
                swapSouth[k] = letters.get( (board.getSize()-i)*board.getSize()-(1+i)-k);
                swapWest[k] =  letters.get( (board.getSize()-1)*(board.getSize()-i)-(board.getSize()*k));

            }

           for (int k=0; k < board.getSize()-(i*2); k++) {
                letters.set((board.getSize()-1)*(i+1)+board.getSize()*k,swapNorth[k]); //put North in East
                letters.set( (board.getSize()-i)*board.getSize()-(1+i)-k,swapEast[k]); //put East in South
                letters.set( (board.getSize()-1)*(board.getSize()-i)-(board.getSize()*k),swapSouth[k]); //put South in West
                letters.set(i+(i*board.getSize())+k,swapWest[k]);
            }
        }
        board.setLetters(letters);
    }

    //I'm lazy
    public void rotateLeft() {
      for (int i=0; i<3; i++) {
        rotateRight();
      }
    }
    
    //print the board in ASCII art
    public String getPrettyBoardDisplay(){
        List<Character> letters = board.getLetters();
        String topBase =  "┌────";
        String midBase =   "| ──┼";
        String botBase =  "└────";
        String midExtend = "───┼";
        String Extend    = "────";
        String topEnd =  "───┐";
        String midEnd =   "── |";
        String botEnd =  "───┘";
        int extension = board.getSize() - 2;
        String top = topBase;
        String mid = midBase;
        String bot = botBase;
        for (int i =0; i < extension; i++){
            top += Extend;
            mid += midExtend;
            bot += Extend;
        }
        top+=topEnd;
        mid+=midEnd;
        bot+=botEnd;
        String boardDisplay = "" + top;
        for (int i = 0; i < board.getSize(); i++) {
            String rowString = "|";
            for (int j = 0; j < board.getSize(); j++) { 
                String let = Character.toString(letters.get((i*board.getSize())+j));
                if (let.equals("q")) {
                    rowString+=" Qu";
                }
                else{
                    rowString+=" ";
                    rowString+=let;
                    rowString+=" ";
                }
                if (i!=0 || i!=board.getSize()-1){
                    rowString+="|";
                }
            }
            boardDisplay+="\n";
            boardDisplay+=rowString;
            boardDisplay+="\n";
            if (i!=board.getSize() -1){
                boardDisplay+=mid;
            }
        }
        boardDisplay+=bot;
        return boardDisplay;
    }

    public String getBoardDisplay(){
      List<Character> letters = board.getLetters();
      String boardDisplay = "";
      for (int i = 0; i < board.getSize(); i++) {
        String rowString = ""; 
        for (int j = 0; j < board.getSize(); j++) {
            rowString+=Character.toString(letters.get((i*board.getSize())+j));
        }
        rowString+="\n";
        boardDisplay+=rowString;
      }
      return boardDisplay;
    }

}
