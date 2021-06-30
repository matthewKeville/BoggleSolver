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

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import model.SinglePlayerModel;

public class AnswerView extends JPanel {

  private Color orangeRed = new Color(255,69,0);
  
  //words greater than or equal to will be put
  //in the same column of maxSize
  private JPanel scrollPanePanel;
  private JScrollPane scrollPane;
  private Map<Integer,JList> listModelMap;
    
  //JLists come and go, so I will capture
  //the actionEvent set by controller and apply
  //it to any new list
  private ListSelectionListener wordSelected;

  public AnswerView() {
    super();
    this.setPreferredSize(new Dimension(400,400));
    //this.setBackground(orangeRed);
    //this.setOpaque(true);
    this.setLayout(new GridBagLayout());
    

    scrollPanePanel = new JPanel();
    scrollPanePanel.setPreferredSize(new Dimension(400,800));
    scrollPanePanel.setBackground(orangeRed);
    scrollPanePanel.setOpaque(true);
    scrollPanePanel.setLayout(new GridBagLayout());
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx=0;
    gbc.gridy=0;
    gbc.weightx=1;
    gbc.weighty=1;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;  
    scrollPane = new JScrollPane(scrollPanePanel);
    this.add(scrollPane,gbc);

    //the graphPanel should be above the scroll pane, but not restrict
    //interaction with the scroll sliders
    
  }

  //under the current design all JLists are obliterated when
  //a new game starts, when the controller calls addAndSet,
  //it applies to actionListener to all active JLists
  //and caches the event to wordSelected which is applied
  //to all new instances if wordSelected != null
  public void addAndSetSelectedListener(ListSelectionListener lsl) {
    //store handler
    wordSelected = lsl;
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

        scrollPanePanel.removeAll();    
        scrollPanePanel.revalidate();

        createEmptyUserAnswerMap(minSize,maxSize);
        updateUserListModels(userAnswersMap);

        scrollPanePanel.repaint();

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

        scrollPanePanel.revalidate();
        scrollPanePanel.repaint();

    //If in pregame state there should be no answer display
    } else if (spvm.getGameState() == SinglePlayerModel.GameState.PREGAME) {
        //clear all
        scrollPanePanel.removeAll();
        scrollPanePanel.revalidate();
        scrollPanePanel.repaint();
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
        //if the controller has determined what to do on index select add stored handler
        if (wordSelected != null) {
            sizeList.addListSelectionListener(wordSelected);
        }
        sizeList.setForeground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = k - minAnswerSize;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        scrollPanePanel.add(sizeList,gbc);
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
        if (wordSelected != null) {
            sizeList.addListSelectionListener(wordSelected);
        }
        sizeList.setForeground(Color.RED);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = k - minSize;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        scrollPanePanel.add(sizeList,gbc);
    }
  }

 }
