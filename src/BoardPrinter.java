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
      this.rotation = 0;
    } 

    public void setBoard(Board board) {
      this.board = board;
      this.rotation = 0;
    }

    public void rotateRight(){
      this.board.rotateRight();
      this.rotation = (this.rotation + 1) % 4;
    }
    
    public void rotateLeft(){
      this.board.rotateLeft();
      this.rotation = (this.rotation - 1) % 4;
    }
    
    //print the board in ASCII art
    public String getPrettyBoardDisplay(){
        List<String> faces = board.getFaces();
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
                String let = faces.get((i*board.getSize())+j);
                if (let.equals("q")) {
                    rowString+=" Qu";
                }
                ////////////////////////////////////////////////////////////////////////////////
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
      List<String> faces = board.getFaces();
      String boardDisplay = "";
      for (int i = 0; i < board.getSize(); i++) {
        String rowString = ""; 
        for (int j = 0; j < board.getSize(); j++) {
            rowString+=faces.get((i*board.getSize())+j);
        }
        rowString+="\n";
        boardDisplay+=rowString;
      }
      return boardDisplay;
    }

}
