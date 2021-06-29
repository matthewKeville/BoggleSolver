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
import java.util.Arrays;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


//single player view will manage the playing of a single player 
//boggle game .

// words submitted to answer input field will be verified 
// for correctness and lifted into the AnswerView


public class SinglePlayerView extends JPanel{

  private Color gold = new Color(255,215,0);

  private BoardView bv;
  private AnswerView av;
  private AnswerInputView aiv;  
  private GameMenuView gmv;
  private TimerView tv;

  public SinglePlayerView() {
    super();
    bv = new BoardView();
    av = new AnswerView();
    aiv = new AnswerInputView();
    gmv = new GameMenuView();
    tv = new TimerView();
    createAndShow(); 
  }

  public BoardView getBoardView() {
    return this.bv;
  }
    
  public AnswerView getAnswerView() {
    return this.av;
  }
    
  public AnswerInputView getAnswerInputView() {
    return aiv;
  }

  public GameMenuView getGameMenuView() {
    return gmv;
  }

  public TimerView getTimerView() {
    return tv;
  }

  private void createAndShow() {
    //create board view in future, board should be supplied to constructor
    //for purposes now, it is in BoardView
    //Note when using gbl , components can't span multiple rows unless
    //the component is centered in the top left of the grid , (its a bug)
    setLayout(new GridBagLayout());
    setBackground(gold);
    setOpaque(true);
    //Timer View 
    GridBagConstraints c0 = new GridBagConstraints();
    c0.gridx = 0;
    c0.gridy = 0;
    c0.anchor = GridBagConstraints.CENTER;
    add(tv,c0);

    //Board View
    GridBagConstraints c1 = new GridBagConstraints();
    c1.gridx = 0;
    c1.gridy = 1;
    c1.anchor = GridBagConstraints.FIRST_LINE_END;
    add(bv,c1);
    //Answer View
    GridBagConstraints c2 = new GridBagConstraints();
    c2.gridx = 1;
    c2.gridy = 1;
    c2.anchor = GridBagConstraints.FIRST_LINE_START;
    add(av,c2);
    //Answer Input Field
    GridBagConstraints c3 = new GridBagConstraints();
    c3.gridx = 0;
    c3.gridy = 2;
    c3.anchor = GridBagConstraints.FIRST_LINE_START;
    add(aiv,c3);
    //add GameMenu
    GridBagConstraints c4 = new GridBagConstraints();
    c4.gridx = 1;
    c4.gridy = 2;
    c4.anchor = GridBagConstraints.FIRST_LINE_START;
    add(gmv,c4);

    }


}
