package client;

import javafx.stage.Stage;
import model.TSPInstance;

public class TSPRankerGUI {

    private Stage primaryStage;
    private TSPInstance tspInstance = null;

    public TSPRankerGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showFirstPage() {
        tspInstance = null;
        FirstPage firstPage = new FirstPage(this);
        firstPage.getAvailableInstances();
        primaryStage.setScene(firstPage.getScene());
        primaryStage.setTitle("TSP Ranker");
        primaryStage.show();
    }

    public void showSecondPage(String instanceName, int id) {
        SecondPage secondPage = new SecondPage(this, instanceName, id);
        secondPage.getInstanceSolutions();
        primaryStage.setTitle("Solution ranking for instance: " + instanceName);
        primaryStage.setScene(secondPage.getScene());
    }

    public void showThirdPage(String instanceName, int id){
        ThirdPage thirdPage = new ThirdPage(this, instanceName, id);
        primaryStage.setTitle("Drawing for instance: " + instanceName);
        primaryStage.setScene(thirdPage.getScene());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public TSPInstance getTspInstance() {
        return tspInstance;
    }

    public void setTspInstance(TSPInstance tspInstance) {
        this.tspInstance = tspInstance;
    }
}
