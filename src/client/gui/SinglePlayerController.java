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
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import board.*;
import solver.*;

//Listens for changes to the model 
//and applys them to the views

public class SinglePlayerController {

  private SinglePlayerView spv;
  private SinglePlayerModel spm;

  public SinglePlayerController(SinglePlayerView spv,SinglePlayerModel spm) {
    super();

    this.spv = spv;
    this.spm = spm;
    //set default model fields 
   
    //set default view in accordance with model
    spv.getAnswerView().createEmptyAnswerMap(spm.getMinAnswerSize(),spm.getMaxAnswerSize()); 

    //update all views to reflect there model
    //updateBoardView(); 


    // When the view says a new word is trying to be added,
    // check the validity of this word. 
    // tell the SinglePlayer Model to update its state
    // tell the SinglePlayer View to  update it visual
    // use the GameMenu Controller to update it's model
    // use the GameMenu Controller to update it's view (response Label)

    spv.getAnswerInputView().addNewWordListener(new ActionListener()
    {
        //adjust to adhere to answer constraints in the model
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
            if (spm.answerExists(newWord)) {
               spv.getAnswerInputView().getResponseLabel().setText("You already found this word!");
               valid = false;
            } 
            //if word is in the answerList
            if (!spm.getSolutionList().contains(newWord)) {
                valid = false;
                spv.getAnswerInputView().getResponseLabel().setText(" Umm no , look again ");            
            } 
            //only add if valid
            if (valid) {
                //spm.addAnser might be out of place, control logic should be in the controller
                spm.addAnswer(newWord);
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
                updateAnswerView();
            }
            //always reset the field
            spv.getAnswerInputView().getAnswerField().setText("");
            //update AnswerView Model
        }
    });

   spv.getGameMenuView().addShakeListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Shake Button Pressed");
            //update the board model // setBoard solves and populates solutionList
            generateNewGame();
            //update the board view
            updateBoardView();
            //tbd update answerModel
            spm.resetUserAnswersMap();
            //tbd update answerView
            updateAnswerView();
        }
    }); 

    spv.getGameMenuView().addRotateLeftListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Rotate Left Button Pressed");
            spm.getBoard().rotateLeft();
            updateBoardView();
        }
    }); 

    spv.getGameMenuView().addRotateRightListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Rotate Right Button Pressed");
            spm.getBoard().rotateRight();
            updateBoardView();
        }
    });

    spv.getGameMenuView().addExitGameListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e) 
        {
            System.out.println("Exit Game Button Pressed");
            spm.getSupport().firePropertyChange("singlePlayerActive",false,true);
        }
    });

  }

  /////////////////////
  //View update
  //////////////////////

  private void updateBoardView() {
    spv.getBoardView().setBoard(spm.getBoard());
  }

  private void updateAnswerView() {
    spv.getAnswerView().updateListModels(spm.getUserAnswersMap()); 
  }

  public void updateAll() {
    updateBoardView();
    updateAnswerView();
  }


  /////////////////////////////////////
  // Called from parent controller
  //////////////////////////////////////
  public void generateNewGame() {
    spm.generateNewGame();
  }

  public void setBoardSize(int newSize) {
    spm.setSize(newSize);
  }

  public void setGameMode(String newMode) {
    spm.setGameMode(newMode); 
  }

  public void setActive(boolean x) {
    spm.setActive(x);
  }

  public SinglePlayerModel getSinglePlayerModel() {
    return spm;
  }

  public SinglePlayerModel getSinglePlayerController() {
    return spm;
  }

    

  
}
