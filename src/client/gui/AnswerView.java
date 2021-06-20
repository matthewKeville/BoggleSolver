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
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.stream.*;
import java.util.Map;

import board.*;
import solver.*;

public class AnswerView extends JPanel {

  public AnswerView() {
    super();
    this.setPreferredSize(new Dimension(400,400));
    //docs reccomend using gridlayout or borderlayout for single item containers
    GridLayout glone = new GridLayout(1,1);
    glone.setHgap(8);
    this.setLayout(glone);
    create();
  }

  private void create() {

    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);
    Font labelFont = new Font("Serif",Font.BOLD,16);
    //get a 4x4 boggle board

    //String[] columnNames = {"3","4","5","6","7+"};    
    //Transform the giving list of strings into a map of string length and
    //ordered lists of strings for each length
    //List<String> answers = Arrays.asList("cat","cats","corks","frogs","carpets","dogs","puppies","pupiles");
    List<String> answers = Arrays.asList("cat","cats","corks","frogs","carpets","dogs","puppies","pupiles","stupid","stinky","shawn","is","screadjasfds");




    List<String> answersCopy = new ArrayList(answers);
    Map<Integer,List<String>> answerByLength = answersCopy.stream()
    .sorted( (x,y) -> {if (x.length() < y.length()) { 
                                            return -1;
                                           } else if (x.length() == y.length()) {
                                            return  (x.compareTo(y) < 0)?-1:1;  }
                                           else {
                                             return 1;
                                            }})
    .collect(Collectors.groupingBy(String::length));

    System.out.println(answerByLength);

    int numRows = answerByLength.keySet().stream()
      .mapToInt( (x) -> answerByLength.get(x).size())
      .max().getAsInt();
    System.out.println(numRows);

    int numCols = answerByLength.size();
    System.out.println("rows " +  numRows + " cols " + numCols); 



    //JTable jt = new JTable(new AnswerTableModel(data,columnNames));
    JTable jt = new JTable(new AnswerTableModel(answers));
    jt.setPreferredSize(new Dimension(400,400));
    this.add(jt);

  }

  //this model takes in a list of strings and makes a table
  //where the columns are string lenghts 3 - 7+
  // the absence of a string will be replaced with an empty string


  /* 
  */
  class AnswerTableModel extends AbstractTableModel
  {
    
    //private Object[][] data; 
    private Map<Integer,List<String>> data;

    public AnswerTableModel(List<String> answerList) {
        super();
        //this.columnNames = columnNames;
        List<String> answerCopy = new ArrayList(answerList);
        Map<Integer,List<String>> answerByLength = answerCopy.stream()
        .sorted( (x,y) -> {if (x.length() < y.length()) { 
                                            return -1;
                                           } else if (x.length() == y.length()) {
                                            return  (x.compareTo(y) < 0)?-1:1;  }
                                           else {
                                             return 1;
                                            }})
        .collect(Collectors.groupingBy(String::length));
        this.data = answerByLength;
    }

    //abstract table leaves 3 methods to be implemented from TableModel interface

    public int getRowCount() {
      return data.keySet().stream()
      .mapToInt( (x) -> data.get(x).size())
      .max().getAsInt();
    }

    public int getColumnCount() {
      int maxLength = data.keySet().stream().max(Integer::compareTo).get();
      int minLength = data.keySet().stream().min(Integer::compareTo).get();
      return (maxLength - minLength + 1);
    }

    //return the rowth string at column , if no string exists return ""
    public Object getValueAt(int row, int column) {
      int maxLength = data.keySet().stream().max(Integer::compareTo).get();
      int minLength = data.keySet().stream().min(Integer::compareTo).get();
      System.out.println("minLen" + minLength + "maxLen" + maxLength);
      int lengthIndex = column+minLength;
      if (data.containsKey(lengthIndex)) {
        System.out.println(row+ " " + column + "map has it");
        List<String> equalLength = data.get(lengthIndex);
        if ( row >= equalLength.size()) {
            return "";
        } else {
            return equalLength.get(row);
        }
      } else {
        return "";
      } 
    }

  }


}
