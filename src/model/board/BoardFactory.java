package model.board;
public class BoardFactory {

    public Board getInstance(int size,String type)
    {
        switch(type) {
          case "Classic":
            return new ClassicBoard(size);
          case "Redux":
            return new ReduxBoard(size);
          //case "Music":
          //  return new MusicBoard(size);
          case "Links":
            return new LinksBoard(size);
          default: 
            return new ClassicBoard(size);
        }
    }

    public Board getInstance(int size)
    {
        return new ClassicBoard(size);
    }

    public Board getInstance() {
        return new ClassicBoard(4);
    }




}
