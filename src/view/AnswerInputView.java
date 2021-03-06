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
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import model.SinglePlayerModel;


/* This class will hold a JTextField for submitting answers
   and a JLabel that indicates whether a word doesn't exist
   or if a word is already used
*/

public class AnswerInputView extends JPanel {

  private JTextField answerField;
  private JLabel responseLabel;

  public AnswerInputView() {
    super();
    this.setPreferredSize(new Dimension(400,50));
    this.setOpaque(false);
    create();
  }

  private void create() {

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);

    //if ill allow for very large boards, I should
    //make JTextField encompass more than 200 chars
    //but cosmetically make it look like only a set amount
    answerField = new JTextField(20);
    answerField.setVisible(false);
    //docs reccomend using gridlayout or borderlayout for single item containers
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START; 
    this.add(answerField,gbc);
    
    //this label will infrom users about the word they entered
    responseLabel = new JLabel("This is a test");    
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START; 
    this.add(responseLabel,gbc);

  }

  public void refresh(SinglePlayerViewModel spvm) {
    String response = spvm.getResponseLabel();
    System.out.println("updating answer input view");
    responseLabel.setText(response);
    if (spvm.getGameState() == SinglePlayerModel.GameState.GAME) {
        System.out.println("in game state; is answerField displayable" + answerField.isDisplayable());
        //if (!answerField.isDisplayable()) {
            answerField.setVisible(true);
            System.out.println("making answer input visible");
            revalidate();
        //}
    } else if (spvm.getGameState() == SinglePlayerModel.GameState.POSTGAME) {
        //hide answerInput
        answerField.setText("");
        answerField.setVisible(false);
        revalidate();
        //Set Response Label to Time's Up
        responseLabel.setText("Time's Up");
    }
    
    
  }

  //Events

  public void addNewWordListener(ActionListener listenForInput) {
    answerField.addActionListener(listenForInput);
  }

  //Access

  public JTextField getAnswerField() {
    return answerField;
  }

}
