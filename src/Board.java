import java.util.List;
/*
 * a boggle board is a square board of characters
 * 
 *  
 *  
 */
public abstract class Board {
    public List<String> faces; //the faces on the board
    public int size; //side length of board 
   
    //////////////////
    //abstract methods
    //////////////////
    
    //every board should be able to produce an identical board 
    public abstract Board clone();

    //////////////////
    //Concrete
    //////////////////

    public int getSize() {
        return this.size;
    }

    public List<String> getFaces() {
        return this.faces;
    }



    //each board has i innner layers, in which we make 4 substitutions
    // there are size // 2 inner layers with more than 1 element
    //those we swap the 4 sides for as many inner layers as there are
    //we load each side into a buffer and then swap
    public void rotateRight() {
        // integer division is the number of inner layers that need to rotate
        int innerRotations = size /  2;  

        String[] swapNorth = new String[size];
        String[] swapEast = new String[size];
        String[] swapSouth = new String[size];
        String[] swapWest = new String[size];

        //for each layer
        for (int i=0; i < innerRotations; i++) {
            //fill swap buffer
            for (int k=0; k < size-(i*2); k++) {
                swapNorth[k] = faces.get(i+(i*size)+k);
                swapEast[k] = faces.get( (size-1)*(i+1)+size*k );
                swapSouth[k] = faces.get( (size-i)*size-(1+i)-k);
                swapWest[k] =  faces.get( (size-1)*(size-i)-(size*k));

            }

           for (int k=0; k < size-(i*2); k++) {
                faces.set((size-1)*(i+1)+size*k,swapNorth[k]); //put North in East
                faces.set( (size-i)*size-(1+i)-k,swapEast[k]); //put East in South
                faces.set( (size-1)*(size-i)-(size*k),swapSouth[k]); //put South in West     
                faces.set(i+(i*size)+k,swapWest[k]);
            }       
        }
        this.faces = faces;
    }           

    //I'm lazy
    public void rotateLeft() {
      for (int i=0; i<3; i++) {
        rotateRight();
      } 
    }     


        
}
