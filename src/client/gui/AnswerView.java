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

import board.*;
import solver.*;

public class AnswerView extends JPanel {

  private Color orangeRed = new Color(255,69,0);
  
  private int minSize = 3;
  //words greater than or equal to will be put
  //in the same column of maxSize
  private int maxSize = 7;

  private Map<Integer,JList> jlistBySize;

  public AnswerView() {
    super();
    jlistBySize = new HashMap();
    this.setPreferredSize(new Dimension(400,400));
    this.setBackground(orangeRed);
    this.setOpaque(true);
    this.setLayout(new GridBagLayout());
    create();
  }

  public AnswerView(List<String> answers) {
    super();
    jlistBySize = new HashMap();
    this.setPreferredSize(new Dimension(400,400));
    this.setBackground(Color.RED);
    this.setOpaque(true);
    this.setLayout(new GridBagLayout());
    createFrom(answers);
  }


  public void clear() {
    removeAll();
    revalidate(); //let layout manager know 
    create();//create empty JLists
    repaint(); //update stale display
  }

  public void addAnswer(String ans) {
    //get the jlist  that corresponds with this length and
    //change the backing listModel 
    if ( minSize <= ans.length() && ans.length() < maxSize) {
        //get the jlist
        JList<String> staleJList = jlistBySize.get(ans.length());
        //get the JList model
        DefaultListModel staleModel = (DefaultListModel) staleJList.getModel();
        DefaultListModel newModel = new DefaultListModel();

        //create a new ListModel with answer inserted at the correct position 
        boolean found = false;
        for (int i = 0; i < staleModel.size(); i++) {
            //if the current element is larger then new entry insert
            //there otherwise add existant element at that index to new list
            if (!found) {
                if (ans.compareTo(((String)staleModel.getElementAt(i))) <= 0) {
                    found = true;
                    newModel.addElement(ans);
                }
            }
            newModel.addElement(staleModel.getElementAt(i));
        }
        //if all elements are smaller than new add at end
        if (!found) {
            newModel.addElement(ans);
        }
        //set the model of the JList
        staleJList.setModel(newModel);
        
    } else if ( ans.length() >= maxSize) {
        JList<String> staleJList = jlistBySize.get(7);
        DefaultListModel staleModel = (DefaultListModel) staleJList.getModel();
        DefaultListModel newModel = new DefaultListModel();

        //create a new ListModel with answer inserted at the correct position 
        boolean found = false;
        for (int i = 0; i < staleModel.size(); i++) {
            //if the current element is larger then new entry insert
            //there otherwise add existant element at that index to new list
            if (!found) {
                if (ans.length() < ((String) staleModel.getElementAt(i)).length()) {
                    found = true;     
                } else if (ans.length() == ((String) staleModel.getElementAt(i)).length()) {
                    if ( ans.compareTo(((String)staleModel.getElementAt(i))) <= 0) {
                        found = true;
                    }
                } 
                //if newly found , insert at properly location
                if (found) {
                    newModel.addElement(ans);
                }
            }
            //otherwise insert the defualt element
            newModel.addElement(staleModel.getElementAt(i));
        }
        //if all elements are smaller than new add at end
        if (!found) {
            newModel.addElement(ans);
        }
        staleJList.setModel(newModel);


    }
    
    
  }

  //create maxSize - minSize empty JLists
  private void create() {

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);
    Font labelFont = new Font("Serif",Font.BOLD,16);

    //create the ListModels and JLists
    for (int k = minSize; k <= maxSize; k++)
    {
        List<String> answerSizeList = new ArrayList(); 
        JList sizeList = new JList(new DefaultListModel());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = k - minSize;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(sizeList,gbc);
        jlistBySize.put(k,sizeList);
    }

  }


  //create an AnswerView with an existing answer set
  private void createFrom(List<String> answers) {

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);
    Font labelFont = new Font("Serif",Font.BOLD,16);

    //transform answer list into a map of list of strings indexed by size
    //orderd alphabetically
    Map<Integer,List<String>> answersBySize = answers.stream()
        .filter( (x) -> minSize <= x.length() )
        .filter( (x) -> x.length() <  maxSize ) 
        .sorted( (x,y) -> { return x.compareTo(y); })
        .collect(Collectors.groupingBy(String::length));
    //for all strings greater than maxSize add them to the 7 list
    //ordered alphanumerically
    List<String> maxPlus = answers.stream()
        .filter( (x) -> x.length() >= maxSize)
        .sorted( (x,y) -> { //sort alphanumerically
                            if (x.length() < y.length() ) {
                                return -1;
                            } else if (x.length() == y.length()) {
                                return (x.compareTo(y)<0)?-1:1;
                            } else {
                               return 1;
                            }
        })
        .collect(Collectors.toList());
    answersBySize.put(maxSize,maxPlus);
    //if any keys in range (minSize,maxSize) 
    //were not generated implicitly from data 
    //create dummy entries in the map
    for ( int i = minSize; i <= maxSize; i++ ) {
        if (!answersBySize.containsKey(i)) {
            answersBySize.put(i,new ArrayList<String>());
        }
    }

    System.out.println(answersBySize);

    int i = 0;
    //create the ListModels and JLists
    for (Integer k : answersBySize.keySet() )
    {
        List<String> answerSizeList = answersBySize.get(k); 
        DefaultListModel model = new DefaultListModel();
        answerSizeList.stream()
        .forEach( (x) -> model.addElement(x)); 
        JList sizeList = new JList(model);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        i++;
        this.add(sizeList,gbc);
        jlistBySize.put(k,sizeList);
    }

  }
}
