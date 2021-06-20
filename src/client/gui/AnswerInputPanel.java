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
import java.awt.BorderLayout;

import board.*;
import solver.*;

/* This class will hold a JTextField for submitting answers
   and a JLabel that indicates whether a word doesn't exist
   or if a word is already used
*/

public class AnswerInputPanel extends JPanel {

  public AnswerInputPanel() {
    super();
    this.setPreferredSize(new Dimension(400,50));
    create();
  }

  private void create() {

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);

    //if ill allow for very large boards, I should
    //make JTextField encompass more than 200 chars
    //but cosmetically make it look like only a set amount
    JTextField answerField = new JTextField(20);
    //docs reccomend using gridlayout or borderlayout for single item containers
    this.add(answerField,BorderLayout.CENTER);


  }


}
