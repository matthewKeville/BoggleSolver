package client.gui;
public class ModelChangeEvent {
    private String modelName;
    
    public ModelChangeEvent(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getEventDetails() {
        return "" + modelName + " has changed ";
    }
}
