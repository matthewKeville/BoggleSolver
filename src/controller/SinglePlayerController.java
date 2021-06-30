package controller;
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
import java.util.Map;
import java.util.HashMap;

import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.audio.*;
import model.ModelChangeListener;
import model.ModelChangeEvent;
import model.SinglePlayerModel;
import view.SinglePlayerView;
import view.SinglePlayerViewModel;


//Listens for changes to the model 
//and applys them to the views

//SPC listens to changes in the model
public class SinglePlayerController implements ModelChangeListener {  //implements PropertyChangeListener{

  private SinglePlayerView spv;
  private SinglePlayerModel spm;
  
  private SinglePlayerViewModel spvm;

  //sound stuff
  private List<AudioEventListener> audioListeners;

  public SinglePlayerController(SinglePlayerView spv,SinglePlayerModel spm) {
    super();


    this.spv = spv;
    this.spm = spm;
    //listen to changes on the spm
    //spm.addPropertyChangeListener(this);
    spm.addModelChangeListener(this);
    //creat the virst view model based of the view
    this.spvm = getViewModel();
    this.audioListeners = new ArrayList();



    updateViews();

    //Add audio sources

    // When the view says a new word is trying to be added,
    // check the validity of this word. 
    // tell the SinglePlayer Model to update its state
    // tell the SinglePlayer View to  update it visual
    // use the GameMenu Controller to update it's model
    // use the GameMenu Controller to update it's view (response Label)

    spv.getAnswerInputView().addNewWordListener(new ActionListener()
    {
        //adjust to adhere to answer constraints in the model
        //  Warning! Response output is not cohesive with the model
        //  it is using arbitray scoring values
        //  the game mdoe and game type should hold
        //  or dictate how differnt word lengths should be processed
        public void actionPerformed(ActionEvent e)
        {
            String newWord = spv.getAnswerInputView().getAnswerField().getText(); 
            //empty the answer field
            spv.getAnswerInputView().getAnswerField().setText("");
            if (newWord != null) {
                SinglePlayerModel.AnswerGrade answerGrade = spm.addAnswerAttempt(newWord);
                switch (answerGrade) {
                    case FAIL:
                        fireAudioEvent(new PlayClipEvent("invalid"));
                        break;
                    case BOTTOM: 
                        fireAudioEvent(new PlayClipEvent("valid_bottom"));
                        break;
                    case LOW:
                        fireAudioEvent(new PlayClipEvent("valid_low"));
                        break;
                    case MEDIUM:
                        fireAudioEvent(new PlayClipEvent("valid_medium"));
                        break;
                    case HIGH:
                        fireAudioEvent(new PlayClipEvent("valid_high"));
                        break;
                    case TOP:
                        fireAudioEvent(new PlayClipEvent("valid_top"));
                        break;
                    default:
                        System.out.println("Invalid AnswerGrade passed to the SPC");
                }
            } else { System.out.println(" but answer was null ");}
        }
    });

   spv.getGameMenuView().addShakeListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Shake Button Pressed");
            spm.shake();
            fireAudioEvent(new PlayClipEvent("shake"));
        }
    }); 

    spv.getGameMenuView().addRotateLeftListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Rotate Left Button Pressed");
            spm.rotateLeft();
            fireAudioEvent(new PlayClipEvent("rotate"));
        }
    }); 

    spv.getGameMenuView().addRotateRightListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Rotate Right Button Pressed");
            spm.rotateRight();
            fireAudioEvent(new PlayClipEvent("rotate"));
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

    spv.getBoardView().addPlayListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Play Button Pressed, starting game");
            spm.play();
        }
    });

    spv.getGameMenuView().addEndListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("End Game Button Pressed");
            spm.end();
        }
    });

    //these are firing twice ... wth
    spv.getAnswerView().addAndSetSelectedListener(new ListSelectionListener() 
    {
        public void valueChanged(ListSelectionEvent e) {
            JList firedFrom = (JList) e.getSource();
            ListModel firedFromModel = firedFrom.getModel();
            String newInspectedWord = (String) firedFromModel.getElementAt(e.getFirstIndex());
            spm.setInspectedWord(newInspectedWord);
        }

    });
            

  }


  //////////////////////////////////////
  // View Update Part Deux (ViewModel)
  /////////////////////////////////////

  //update views, will getViewModel() , check if new ViewModel
  //differs from previous, if it does, it will check how it differs 
  //and update the corresponding views


  //the intent here is to compare the previoius viewModel to the current 
  //and sort out the diffs, but this approach doesn't work because of how coying 
  //works in java. I need to look into implementing a cloning method
  //or implementing hashes and compare old new hash values
  
  //for now, whenever the model changes I will update all views
  public void updateViews() {
    SinglePlayerViewModel oldModel = spvm;
    spvm = getViewModel();
           spv.getBoardView().refresh(spvm);
           spv.getAnswerView().refresh(spvm);            
           spv.getAnswerInputView().refresh(spvm);
           spv.getTimerView().refresh(spvm);
           System.out.println("Game State: " + spvm.getGameState());
  }

  //Construct a view Model from the model
  public SinglePlayerViewModel getViewModel() {
    System.out.println(spm.getBoard() == null);
    return new SinglePlayerViewModel(spm.getBoard().getFaces(),spm.getUserAnswersMap(),spm.getAnswerInputResponse(),spm.getInspectedWord(),
                spm.isTimed(),spm.getTime(),spm.getGameState(),spm.getSolutionMap(),spm.getSolution());
  }

  public void modelChange(ModelChangeEvent mce) {
    System.out.println(mce.getModelName() + " has changed ");
    updateViews(); 
  } 

  //// Audio Player hooks ////
  
  public void addAudioListener(AudioEventListener ael) {
    audioListeners.add(ael);
  }

  //*probably bad design, maybe I don't need event handlers at all
  //* rethink this approach
  //broadcast all addAudio Events to any AudioEventListeners
  public void addAllAudio() {

    String gameSfx = "gameSoundEffects";
    float sfxVolume = .6f;
    float musicVolume = .5f;
    String gameMusic = "gameMusic";


    fireAudioEvent(new AddAudioSourceEvent("invalid",gameSfx,"src/res/sound/badword.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("valid_bottom",gameSfx,"src/res/sound/validwordlow.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("valid_low",gameSfx,"src/res/sound/validwordmedium.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("valid_medium",gameSfx,"src/res/sound/validwordhigh.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("valid_high",gameSfx,"src/res/sound/validwordtall.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("valid_top",gameSfx,"src/res/sound/validwordtop.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("shake",gameSfx,"src/res/sound/shake.wav",sfxVolume));
    fireAudioEvent(new AddAudioSourceEvent("rotate",gameSfx,"src/res/sound/rotate.wav",sfxVolume));

    //invalid is too quiet
    fireAudioEvent(new VolumeClipEvent("invalid",.7f)); 

  }

  public void fireAudioEvent(AudioEvent ae) {
    for (AudioEventListener ael : audioListeners) {
        ael.fireAudioEvent(ae);
    }
  }


  /////////////////////////////////////
  // Called from parent controller
  //////////////////////////////////////

  //instead of wrapping calls to change the model in
  //methods of this controller, why not let the parent
  //controller directrly call methods on the model

  public void setTimed(boolean x) {
    spm.setTimed(x);
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
