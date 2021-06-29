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


/* This class will hold a JLabel to display game time
*/

public class TimerView extends JPanel {

  private JLabel timerLabel;

  public TimerView() {
    super();
    this.setPreferredSize(new Dimension(400,50));
    this.setOpaque(false);
    create();
  }

  private void create() {
    Color oldLace = new Color(.992f,.961f,.902f);
    Color cream = new Color(1.0f,.992f,.816f);

    Font timerFont = new Font("SansSerif",Font.BOLD,40);
    timerLabel = new JLabel("");    
    timerLabel.setFont(timerFont);
    timerLabel.setForeground(Color.white);

    //docs reccomend using gridlayout or borderlayout for single item containers
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    //this label will infrom users about the word they entered
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.PAGE_START; 
    this.add(timerLabel,gbc);

  }

  public void refresh(SinglePlayerViewModel spvm) {
    if (spvm.isTimed()) {
        int time = spvm.getTime();
        int minutes = time / 60;
        int seconds = time % 60;
        String timeString = minutes + ":" + (seconds<10 ? "0" : "") + seconds;
        if (time < 10) {
            timerLabel.setForeground(Color.red);
        } else {
            timerLabel.setForeground(Color.black);
        }

        if (time > 0) {
            timerLabel.setText(timeString);
        } else {
            timerLabel.setText("Game Over");
        }
    } else {
        timerLabel.setText("");
    }
  }

}
