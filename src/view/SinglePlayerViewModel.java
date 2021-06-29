package view;
import java.util.List;
import java.util.Map;

import model.SinglePlayerModel;


/* The SinglePlayerViewModel class is an abstraction of the model
   for the view to be passed by the controller. It contains all model info
   relevant to the View. The View only needs to know about the view model,
   not the model itself.
*/

public class SinglePlayerViewModel {

    //BoardView
    //size can be inferred from boardFaces
    private List<String> faces;

    //AnswersView
    //min and max size can be inferred from keySet
    private Map<Integer,List<String>> userAnswersMap;

    //AnswerInputView
    private String responseLabel;

    //game time
    private int time;

    //is timed?
    private boolean timed;

    //gameState
    private SinglePlayerModel.GameState gameState;

    private Map<Integer,List<String>> solutionMap;

    public SinglePlayerViewModel(List<String> faces, Map<Integer,List<String>> userAnswersMap, String responseLabel,boolean timed, int time,SinglePlayerModel.GameState gameState,Map<Integer,List<String>> solutionMap) {
        this.faces = faces;
        this.userAnswersMap = userAnswersMap;
        this.responseLabel = responseLabel;     
        this.timed = timed;
        this.time = time;
        this.gameState = gameState;
        this.solutionMap = solutionMap;
    }


    public List<String> getFaces() {
        return faces;
    }

    public Map<Integer,List<String>> getUserAnswersMap() {
        return userAnswersMap;
    }

    public String getResponseLabel() {
        return responseLabel;
    }

    public int getTime() {
        return time;
    }
    
    public boolean isTimed() {
        return timed;
    }
    
    public SinglePlayerModel.GameState getGameState() {
        return gameState;
    }

    public Map<Integer,List<String>> getSolutionMap() {
        return solutionMap;
    }





}
