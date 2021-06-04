public class BoardFactory {

    public Board getInstance(int size,String type)
    {
        switch(type) {
          case "Classic":
            return new ClassicBoard(size);
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
