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
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.Graphics;
//Extension of awt.graphics for more
//sophisticated rendering (I need line thickness)
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Stroke;


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

  private WordGraph wordGraph;
  private List<Integer> graphPath;

  private List<JLabel> faceLabels;
  private Color oldLace = new Color(.992f,.961f,.902f);
  private Color cream = new Color(1.0f,.992f,.816f);

  public class WordGraph extends JPanel {

    private List<Color> colorGradient;

    public WordGraph() {
        super();
        colorGradient = new ArrayList();
        colorGradient.add(Color.red);
        colorGradient.add(Color.pink);
        colorGradient.add(Color.orange);
        colorGradient.add(Color.yellow);
        colorGradient.add(Color.green);
        colorGradient.add(Color.cyan);
        colorGradient.add(Color.blue);
        colorGradient.add(Color.magenta);
    }
    @Override
    public void paintComponent(Graphics g) {
        System.out.println("WordGraph Repaint Called");
        super.paintComponent(g);
        List<Integer> path = new LinkedList();
        graphWord(graphPath,g);
    }


    // Warning ! this does not consider board rotation 
    //Given a word path draw lines between the centers of the JLabels
    //that represent the point on that path
    private void graphWord(List<Integer> path,Graphics g) 
    {
        int colorIndex = 0;
        if (path.size() != 0) {
            Graphics2D g2d = (Graphics2D) g;
            Stroke stroke = new BasicStroke(4f);
            g2d.setStroke(stroke);
            g2d.setColor(Color.RED);
            Iterator pathIterator = path.iterator();
            JLabel current = faceLabels.get((Integer) pathIterator.next()); 
            while (pathIterator.hasNext()) {
                JLabel next = faceLabels.get( (Integer) pathIterator.next());
                //draw line from current to next
                //g2d.setColor(colorGradient.get(colorIndex % colorGradient.size())); // rainbow
                g2d.drawLine(current.getX()+(current.getWidth()/2),current.getY()+(current.getHeight()/2)
                            ,next.getX()+(current.getWidth()/2),next.getY()+(next.getHeight()/2)); 
                //set  current to next
                current  = next;
                colorIndex++;
            } 
        }
    }
 
  }

  
  

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

    //create boardview just create the panel
    boardView = new JPanel();

    //put the boardView in a JLayered Pane
    //where the WordGraph exists above it
    JLayeredPane boardViewLayers = new JLayeredPane();
    boardViewLayers.setPreferredSize(new Dimension(400,400)); 
    boardView.setBounds(0,0,400,400);
    boardViewLayers.add(boardView,new Integer(2),1);

    wordGraph = new WordGraph();
    wordGraph.setPreferredSize(new Dimension(400,400));
    wordGraph.setOpaque(false);
    wordGraph.setBounds(0,0,400,400);

    graphPath = new LinkedList();
 
    boardViewLayers.add(wordGraph,new Integer(2),0); 


    add(boardPreView,BOARDPREVIEW);
    add(boardViewLayers,BOARDVIEW);

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

    //ensures no path rendering happens outside of POSTGAME 
    if (spvm.getGameState() != SinglePlayerModel.GameState.POSTGAME) { 
        graphPath.clear();
    }

  }

 
  public void refresh(SinglePlayerViewModel spvm) {
    System.out.println("refreshing Board View");

    if (spvm.getGameState() == SinglePlayerModel.GameState.GAME) {
        ((CardLayout) getLayout()).show(this,BOARDVIEW);
    } else if (spvm.getGameState() == SinglePlayerModel.GameState.PREGAME) {
        ((CardLayout) getLayout()).show(this,BOARDPREVIEW);
    }
   
    //graph the inspected word 
    if (spvm.getGameState() == SinglePlayerModel.GameState.POSTGAME) {
        System.out.println(" game state is postgame in boardview udate");
        System.out.println(" inspected word : " + spvm.getInspectedWord() );
        if (!spvm.getInspectedWord().equals("")) {
            List<List<Integer>> paths = spvm.getSolution().get(spvm.getInspectedWord());
            graphPath = paths.get(0); 
            System.out.println("graph path");
            System.out.println(graphPath);
            wordGraph.repaint();
        }
    }

    refreshBoardView(spvm);

    
  }

  public void addPlayListener(ActionListener playListener) {
    playButton.addActionListener(playListener);
  }


}
