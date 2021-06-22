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
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import board.*;
import solver.*;

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

  public void addNewWordListener(ActionListener listenForInput) {
    answerField.addActionListener(listenForInput);
  }

  public JTextField getAnswerField() {
    return answerField;
  }

  public JLabel getResponseLabel() {
    return responseLabel;
  }


}
