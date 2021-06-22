package client.gui;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.ArrayList;
import board.*;
import solver.*;

public class BoardView extends JPanel {

  private JPanel boardView;
  private List<JLabel> faceLabels;
  private Color oldLace = new Color(.992f,.961f,.902f);
  private Color cream = new Color(1.0f,.992f,.816f);


  public BoardView() {
    super();
    //docs reccomend using gridlayout or borderlayout for single item containers
    GridLayout glone = new GridLayout(1,1);
    this.setLayout(glone);
    this.setPreferredSize(new Dimension(400,400));
    create();
  }

  //transform the view to represent the given board,
  //or board state
  public void setBoard(Board board) {
    clear();
    //add a new set of JLabels to the boardview panel
    createLabels(board); 
    boardView.repaint();
  }

    

  public void clear() {
    boardView.removeAll();
    boardView.revalidate();
    if (faceLabels != null) {
        faceLabels.clear();
    }
  }

  private void createLabels(Board board) {
    //reset or make new JLabel list
    faceLabels = new ArrayList<JLabel>();
    //retrieve data from supplied board
    List<String> faces = board.getFaces();
    //make labels for all faces, add to boardView
    //and faceLabel list
    for (int i = 0; i < faces.size(); i++) {
      Font labelFont = new Font("SansSerif",Font.BOLD,40);
      JLabel label = new JLabel(faces.get(i));
      label.setPreferredSize(new Dimension(2,2));
      label.setForeground(Color.black);
      label.setBackground(oldLace);
      label.setOpaque(true);
      label.setFont(labelFont);
      Border lineBorder = BorderFactory.createLineBorder(Color.black,2,true);
      label.setBorder(lineBorder);
      label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      faceLabels.add(label);
      boardView.add(label); 
    }
  }

  private void create() {
    //set up container for labels

    //can't seem to find  a margin property , so
    //i will have the boardView JPanel nested inside a
    //a JPanel with a border to act as a margin
    //3rd arg is rounded
    Border paddingBorder = BorderFactory.createLineBorder(Color.blue,8,true);
    this.setBorder(paddingBorder);

    GridLayout gl = new GridLayout(4,4); 
    gl.setHgap(4);
    gl.setVgap(4);
    
    boardView = new JPanel(gl);
    boardView.setPreferredSize(new Dimension(400,400));
    boardView.setBackground(Color.blue);
    //add to parent 
    this.add(boardView);
    
  }




}
