package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.PairIdInstance;
import model.TSPInstance;
import model.TSPSolution;

import java.util.List;

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
