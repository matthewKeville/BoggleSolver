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

  public void updateListModels(Map<Integer,List<String>> answerMap) {
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

  public void clear() {
    removeAll();
    revalidate(); //let layout manager know 
    repaint(); //update stale display
  }
    
  //create JList Map from HashMap of answer lists indexed by size
  public void createEmptyAnswerMap(int minAnswerSize, int maxAnswerSize) {
    listModelMap = new HashMap();

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);
    Font labelFont = new Font("Serif",Font.BOLD,16);
    //create the ListModels and JLists
    for (int k = minAnswerSize; k <= maxAnswerSize; k++)
    {
        List<String> answerSizeList = new ArrayList(); 
        JList sizeList = new JList(new DefaultListModel());
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

}
