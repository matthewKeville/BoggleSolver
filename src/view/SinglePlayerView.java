package view;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;


public class SinglePlayerView extends JPanel{

  private SinglePlayerGameView spgv;
  private SinglePlayerMenuView spmv;

  public static final String GAMEVIEW = "game_view";
  public static final String MENUVIEW = "menu_view";

  public SinglePlayerView() {
    super();
    spgv = new SinglePlayerGameView();
    spmv = new SinglePlayerMenuView();
    createAndShow();
  }

  public SinglePlayerGameView getGameView() {
    return spgv;
  }

  public SinglePlayerMenuView getMenuView() {
    return spmv;
  }
     

  private void createAndShow() {
    setLayout(new CardLayout());
    add(spgv,GAMEVIEW);
    add(spmv,MENUVIEW);
    
  }
    
}
