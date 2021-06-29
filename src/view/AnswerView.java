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
import javax.swing.table.TableModel;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.stream.*;
import java.util.Map;
import java.util.Set;

import model.SinglePlayerModel;

public class AnswerView extends JPanel {

  private Color orangeRed = new Color(255,69,0);
  
  //words greater than or equal to will be put
  //in the same column of maxSize

  private Map<Integer,JList> listModelMap;

  public AnswerView() {
    super();
    this.setPreferredSize(new Dimension(400,400));
    this.setBackground(orangeRed);
    this.setOpaque(true);
    this.setLayout(new GridBagLayout());
  }

  public void refresh(SinglePlayerViewModel spvm) {
    if (spvm.getGameState() == SinglePlayerModel.GameState.GAME ) {
        Map<Integer,List<String>> userAnswersMap = spvm.getUserAnswersMap();
        System.out.println("refreshing AnswersView ");

        int maxSize = 0;
        int minSize = 1000; //arbitrary large value
        //find max and min word sizes
        for (Integer key : userAnswersMap.keySet()) {
            if (key < minSize) {
                minSize = key;
            }
            if (key > maxSize) {
                maxSize = key;
            }
        }

        removeAll();    
        revalidate();

        createEmptyUserAnswerMap(minSize,maxSize);
        updateUserListModels(userAnswersMap);

        repaint();
    //Game Over - add all answers under user answers
    //consider bundling this into a seperate method call to improve readibility
    } else if (spvm.getGameState() == SinglePlayerModel.GameState.POSTGAME) {
        System.out.println("Generating users answers complement");
        System.out.println("solutionmap");
        System.out.println(spvm.getSolutionMap());
        System.out.println("users map");
        System.out.println(spvm.getUserAnswersMap());
        Map<Integer,List<String>> solutionMap = spvm.getSolutionMap();
        Map<Integer,List<String>> usersMap = spvm.getUserAnswersMap();
        //New Map
        Map<Integer,List<String>> usersComplementMap = new HashMap();
        /* Create the complement of the usersMap */
        for (Integer key : solutionMap.keySet()) {
            List<String> solutionWords = solutionMap.get(key);
            List<String> userWords = usersMap.get(key);
            //New intermediate List
            List<String> complementWords = new ArrayList();
            for (String word : solutionWords) {
                if (!userWords.contains(word)) {
                    complementWords.add(word);
                }
            }
            usersComplementMap.put(key,complementWords);
        }
        /* create new JLists for user answers complement */       
        System.out.println("user answers complement");
        System.out.println(usersComplementMap.toString()); 
        createUserAnswersComplementLists(usersComplementMap);
        revalidate();
        repaint();
    //If in pregame state there should be no answer display
    } else if (spvm.getGameState() == SinglePlayerModel.GameState.PREGAME) {
        //clear all
        removeAll();
        revalidate();
        repaint();
    }
    
      
  }

  //with the map of user answers from the model, populate the list
  //models and apply them to the JLists
  private void updateUserListModels(Map<Integer,List<String>> answerMap) {
    for (Integer key : answerMap.keySet()) 
        {
            DefaultListModel updated = new DefaultListModel();
            for ( String str : answerMap.get(key))
            {
                updated.addElement(str);
            }
        listModelMap.get(key).setModel(updated);
        }
  }

  //create JList Map from HashMap of answer lists indexed by size
  private void createEmptyUserAnswerMap(int minAnswerSize, int maxAnswerSize) {
    listModelMap = new HashMap();

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);
    Font labelFont = new Font("Serif",Font.BOLD,16);
    //create the ListModels and JLists
    for (int k = minAnswerSize; k <= maxAnswerSize; k++)
    {
        List<String> answerSizeList = new ArrayList(); 
        JList sizeList = new JList(new DefaultListModel());
        sizeList.setForeground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = k - minAnswerSize;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(sizeList,gbc);
        listModelMap.put(k,sizeList);
    }
  }


  /* Under all the user answer lists, create a new list for each size that
     contains the complement of the corresponding user list
  */
  private void createUserAnswersComplementLists(Map<Integer,List<String>> answerMap) {
    int maxSize = 0;
    int minSize = 1000; //arbitrary large value
    //find max and min word sizes
    for (Integer key : answerMap.keySet()) {
        if (key < minSize) {
            minSize = key;
        }
        if (key > maxSize) {
            maxSize = key;
        }
    }
    //create the ListModels and JLists
    for (int k = minSize; k <= maxSize; k++)
    {
        DefaultListModel solutionListModel = new DefaultListModel();
        for (String str: answerMap.get(k)) {
            solutionListModel.addElement(str); 
        }
        JList sizeList = new JList(solutionListModel);
        sizeList.setForeground(Color.RED);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = k - minSize;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(sizeList,gbc);
    }
  }

 }
