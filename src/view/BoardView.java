package view;

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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

import model.board.*;
import model.SinglePlayerModel; //I dislike this coupling
                                // I need this to access the enums
                                // in the model (game state)

// the BoardView might not need access to the model,
// I could abstract the view by providing some other data structure


public class BoardView extends JPanel {

  private final static String BOARDVIEW = "Board View";
  private final static String BOARDPREVIEW = "Board Pre View";

  private JPanel boardView;
  private JPanel boardPreView;  
    
  private JButton playButton;

  private List<JLabel> faceLabels;
  private Color oldLace = new Color(.992f,.961f,.902f);
  private Color cream = new Color(1.0f,.992f,.816f);

  public BoardView() {
    super();
    //docs reccomend using gridlayout or borderlayout for single item containers
    //Master container (Jpane)
    setLayout(new CardLayout());
    this.setPreferredSize(new Dimension(400,400));
    faceLabels = new ArrayList();
    create();
  }

  //create initial panels
  private void create() {
    //create boardPreView *Fixed
    boardPreView = new JPanel();
    GridBagLayout gbl = new GridBagLayout(); 
    boardPreView.setLayout(gbl);
    boardPreView.setPreferredSize(new Dimension(400,400));
    boardPreView.setBackground(Color.BLUE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    playButton = new JButton("Play"); 
    playButton.setForeground(Color.BLACK);
    boardPreView.add(playButton,gbc);

    //create boardview *Dynamic, just create the panel
    boardView = new JPanel();


    add(boardPreView,BOARDPREVIEW);
    add(boardView,BOARDVIEW); 

  }

  //update BoardView Panel
  private void refreshBoardView(SinglePlayerViewModel spvm) 
  {
    boardView.removeAll();
    boardView.revalidate();

    faceLabels.clear();
    List<String> faces = spvm.getFaces();
    //set up container for labels
    //can't seem to find  a margin property , so
    //i will have the boardView JPanel nested inside a
    //a JPanel with a border to act as a margin
    //3rd arg is rounded
    Border paddingBorder = BorderFactory.createLineBorder(Color.BLUE,8,true);
    this.setBorder(paddingBorder);

    int boardSize = (int) Math.sqrt(faces.size());

    GridLayout gl = new GridLayout(boardSize,boardSize); 
    gl.setHgap(4);
    gl.setVgap(4);
    
    boardView.setLayout(gl);
    boardView.setPreferredSize(new Dimension(400,400));
    boardView.setBackground(Color.blue);

    //reset or make new JLabel list
    faceLabels = new ArrayList<JLabel>();
    //retrieve data from supplied board
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

    boardView.repaint();


  }

   
 
  public void refresh(SinglePlayerViewModel spvm) {
    System.out.println("refreshing Board View");
    refreshBoardView(spvm);

    if (spvm.getGameState() == SinglePlayerModel.GameState.GAME) {
        ((CardLayout) getLayout()).show(this,BOARDVIEW);
    } else if (spvm.getGameState() == SinglePlayerModel.GameState.PREGAME) {
        ((CardLayout) getLayout()).show(this,BOARDPREVIEW);
    }

    //repaint();
    
  }

  public void addPlayListener(ActionListener playListener) {
    playButton.addActionListener(playListener);
  }


}
