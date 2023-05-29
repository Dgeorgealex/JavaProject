package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.TSPSolution;
import model.TSPSolutionCandidate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.File;


public class SecondPage {

    private TSPRankerGUI tspRankerGUI;
    private Scene scene;
    private int id;
    private TSPSolution[] solutions;
    private ObservableList<TSPSolution> availableSolutions;
    private TableView solutionTable;
    private TextArea messageTextArea;

    public SecondPage(TSPRankerGUI tspRankerGUI, String instanceName, int id) {
        this.id = id;
        this.tspRankerGUI = tspRankerGUI;
        createScene(instanceName);
    }

    private void createScene(String instanceName) {
        VBox secondPageLayout = new VBox(10);
        secondPageLayout.setPadding(new Insets(10));

        // Title setup
        Label titleLabel = new Label("Solution ranking for instance: " + instanceName);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.RED);

        // Solution table setup
        solutionTable = new TableView<>();
        TableColumn<TSPSolution, String> usernameColumn = new TableColumn<>("Username");
        TableColumn<TSPSolution, String> algorithmColumn = new TableColumn<>("Algorithm");
        TableColumn<TSPSolution, Long> valueColumn = new TableColumn<>("Value");
        Button backButton = new Button("Back");
        Button refreshButton = new Button("Refresh");
        Button sendSolutionButton = new Button("Send Solution");
        Button viewDrawingButton = new Button("View drawing");
        messageTextArea = new TextArea();
        messageTextArea.setEditable(false);
        messageTextArea.setWrapText(true);
        messageTextArea.setPrefRowCount(2);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        algorithmColumn.setCellValueFactory(new PropertyValueFactory<>("algorithmName"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        solutionTable.getColumns().addAll(usernameColumn, algorithmColumn, valueColumn);

        backButton.setOnAction(event -> tspRankerGUI.showFirstPage());

        sendSolutionButton.setOnAction(event ->{
            ObjectMapper objectMapper = new ObjectMapper();
            TSPSolutionCandidate solutionCandidate = null;
            try {
                solutionCandidate = objectMapper.readValue(new File("file.json"), TSPSolutionCandidate.class);
            } catch(Exception e){
                solutionCandidate = null;
                messageTextArea.setText("Error while readint candidate solution from file, please check the format");
            }
            if(solutionCandidate == null)
                return;

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8088/instances/" + id + "/solutions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TSPSolutionCandidate> requestEntity = new HttpEntity<>(solutionCandidate, headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
                messageTextArea.setText("Server response: " + response.getBody());
            } catch (HttpClientErrorException e) {
                messageTextArea.setText("HTTP request failed: " + e.getMessage());
            }
        });

        viewDrawingButton.setOnAction(event-> tspRankerGUI.showThirdPage(instanceName, id));

        secondPageLayout.getChildren().addAll(
                titleLabel,
                solutionTable,
                backButton,
                refreshButton,
                sendSolutionButton,
                viewDrawingButton,
                messageTextArea
        );

        scene = new Scene(secondPageLayout, 600, 600);
    }

    public void getInstanceSolutions(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8088/instances/" + id + "/solutions";
        ResponseEntity<TSPSolution[]> response = null;
        try {
            response = restTemplate.getForEntity(url, TSPSolution[].class);
            messageTextArea.setText("Server response: " + response.getStatusCode());
            solutions = response.getBody();
        } catch (HttpClientErrorException e){
            messageTextArea.setText("Http request failed: " + e.getMessage());
            solutions = null;
        }

        availableSolutions = FXCollections.observableArrayList();
        if(solutions != null)
            availableSolutions.addAll(solutions);

        solutionTable.setItems(availableSolutions);
    }
    public Scene getScene() {
        return scene;
    }


}
