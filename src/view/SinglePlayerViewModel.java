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
    
    // user answers indexed by size
    //min and max size can be inferred from keySet
    private Map<Integer,List<String>> userAnswersMap;

    // solution indexed by size
    private Map<Integer,List<String>> solutionMap;

    //paths of each word in solution set
    private Map<String,List<List<Integer>>> solution;

    //AnswerInputView
    private String responseLabel;

    //inspect word
    private String inspectedWord;

    //game time
    private int time;

    //is timed?
    private boolean timed;

    //gameState
    private SinglePlayerModel.GameState gameState;


    public SinglePlayerViewModel(List<String> faces, Map<Integer,List<String>> userAnswersMap, String responseLabel,String inspectedWord,boolean timed, int time,SinglePlayerModel.GameState gameState,Map<Integer,List<String>> solutionMap,Map<String,List<List<Integer>>> solution) {
        this.faces = faces;
        this.userAnswersMap = userAnswersMap;
        this.solutionMap = solutionMap;
        this.solution = solution;
        this.responseLabel = responseLabel;     
        this.inspectedWord = inspectedWord;
        this.timed = timed;
        this.time = time;
        this.gameState = gameState;
    }


    public List<String> getFaces() {
        return faces;
    }

    public Map<Integer,List<String>> getUserAnswersMap() {
        return userAnswersMap;
    }


    public Map<Integer,List<String>> getSolutionMap() {
        return solutionMap;
    }

    public Map<String,List<List<Integer>>> getSolution() {
        return solution;
    }

    public String getResponseLabel() {
        return responseLabel;
    }

    public String getInspectedWord() {
        return inspectedWord;
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






}
