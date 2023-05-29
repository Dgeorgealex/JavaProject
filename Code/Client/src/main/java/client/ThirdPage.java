package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Point;
import model.TSPInstance;
import model.TSPSolution;
import model.TSPSolutionCandidate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.File;
public class ThirdPage {
    private TSPRankerGUI tspRankerGUI;
    private Scene scene;

    private int id;

    public ThirdPage(TSPRankerGUI tspRankerGUI, String instanceName, int id) {
        this.tspRankerGUI = tspRankerGUI;
        this.id = id;
        createScene(instanceName);
    }

    private int normalize(double min, double max, double p){
        return (int)Math.round(500 * (p - min) / (max - min));
    }

    private void createScene(String instanceName){
        VBox thirdPageLayout = new VBox(10);
        thirdPageLayout.setPadding(new Insets(10));
        thirdPageLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Drawing of TSP instance: " + instanceName);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.RED);

        Canvas canvas = new Canvas(540, 540);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeRect(0, 0, 540, 540);

        //draw stuf
        Point[] points = tspRankerGUI.getTspInstance().getPoints();
        double minX, maxX, minY, maxY;
        minX = maxX = points[0].getX();
        minY = maxY = points[0].getY();
        for(int i=0;i<points.length;i++){
            maxX = Math.max(maxX, points[i].getX());
            maxY = Math.max(maxY, points[i].getY());
            minX = Math.min(minX, points[i].getX());
            minY = Math.min(minY, points[i].getY());
        }
        System.out.println(minX + " " + maxX);
        gc.setFill(Color.BLACK);
        for(int i=0;i<points.length;i++){
            int x = normalize(minX, maxX, points[i].getX());
            int y = normalize(minY, maxY, points[i].getY());
            int radius = 3;
            System.out.println(x + " " + y);
            gc.fillOval(x - radius + 20, y - radius + 20, 2 * radius, 2 * radius);
        }


        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            tspRankerGUI.showSecondPage(instanceName, id);
        });
        thirdPageLayout.getChildren().addAll(
                titleLabel,
                canvas,
                backButton
        );

        scene = new Scene(thirdPageLayout, 650, 650);
    }

    public Scene getScene(){
        return scene;
    }
}
