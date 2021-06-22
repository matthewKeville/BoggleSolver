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
import java.util.Arrays;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import board.*;
import solver.*;

//Listens for changes to the model 
//and applys them to the views

public class SinglePlayerController {

  private List<String> userAnswers;
  private Board board;
  private BoardFactory bf;
  private SinglePlayerView spv;
  private SolverFactory sf;
  private Solver bs;
  private List<String> solutionList; 

  public SinglePlayerController(SinglePlayerView spv) {
    super();
    this.spv = spv;
    this.bf = new BoardFactory();
    this.board = bf.getInstance(4,"Classic");
    this.spv.getBoardView().setBoard(board);
    
    this.userAnswers = new ArrayList();   
    this.sf = new SolverFactory("src/res/corncob_lowercase.txt");
    this.bs = sf.getInstance("Classic");
    this.solutionList = this.bs.solveWords(this.board);
 
    //Add actionListener to answer input view
    //let anserview (hybrid v-c) know to update 
    spv.getAnswerInputView().addNewWordListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            boolean valid = true;
            System.out.println("Add New Word Attempt");
            //trim leading and trailing ws
            String newWord = spv.getAnswerInputView().getAnswerField().getText(); 
            newWord = newWord.trim();
            //check if there are spaces in between
            if (newWord.contains(" ")) {
               spv.getAnswerInputView().getResponseLabel().setText("Your word can't contain spaces"); 
               valid = false; 
            }
            //check if word is already found
            if (userAnswers.contains(newWord)) {
               spv.getAnswerInputView().getResponseLabel().setText("You already found this word!");
               valid = false;
            } 
            //if word is in the answerList
            if (! solutionList.contains(newWord)) {
                valid = false;
                spv.getAnswerInputView().getResponseLabel().setText(" Umm no , look again ");            
            } 
            //only add if valid
            if (valid) {
                userAnswers.add(newWord);
                spv.getAnswerView().addAnswer(newWord); 
                String response;
                switch (newWord.length()) {
                    //less than 3 shouldn't pass validity check
                    //and isn't supported by the AnswerView
                    //might need to change this depending on the 
                    //game mode
                    case 3:
                        response = "Nice";
                        break;
                    case 4:
                        response = "Nice";
                        break;
                    case 5:
                        response = "Great";
                        break;
                    case 6:
                        response = "Impressive!";
                        break;
                    case 7:
                        response = "Excellent!";
                        break;
                    //7 +
                    default: 
                        response = "Incredible !!!";
                        break;
                }
                spv.getAnswerInputView().getResponseLabel().setText(response);
            }
            //always reset the field
            spv.getAnswerInputView().getAnswerField().setText("");
        }
    });

   spv.getGameMenuView().addShakeListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Shake Button Pressed");
            board = bf.getInstance(4,"Classic");
            spv.getBoardView().setBoard(board);

            solutionList = bs.solveWords(board);
            //clear internal answers
            userAnswers.clear();
            //clear AnswerView
            spv.getAnswerView().clear();
        }
    }); 

    spv.getGameMenuView().addRotateLeftListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Rotate Left Button Pressed");
            board.rotateLeft();
            spv.getBoardView().setBoard(board);
        }
    }); 

    spv.getGameMenuView().addRotateRightListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Rotate Right Button Pressed");
            board.rotateRight();
            spv.getBoardView().setBoard(board);
        }
    });


  }
}
