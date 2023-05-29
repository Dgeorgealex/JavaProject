package client;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.PairIdInstance;
import model.TSPInstance;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FirstPage {

    private TSPRankerGUI tspRankerGUI;
    private Scene scene;
    private PairIdInstance[] instances;
    private ObservableList<String> availableInstances;
    private ListView<String> testInstancesListView;

    private TextArea messageTextArea;

    public FirstPage(TSPRankerGUI tspRankerGUI) {
        this.tspRankerGUI = tspRankerGUI;
        createScene();
    }

    private void createScene() {
        VBox firstPageLayout = new VBox(10);
        firstPageLayout.setPadding(new Insets(10));

        //Add title
        Label titleLabel = new Label("TSP Ranker");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.RED);

        testInstancesListView = new ListView<>();
        Button refreshButton = new Button("Refresh");
        Button selectInstanceButton = new Button("Select Instance");
        Button addInstanceButton = new Button("Add Instance");
        messageTextArea = new TextArea();
        messageTextArea.setEditable(false);
        messageTextArea.setWrapText(true);
        messageTextArea.setPrefRowCount(2);

        // Handle Select Button action
        selectInstanceButton.setOnAction(event -> {
            String selectedInstance = testInstancesListView.getSelectionModel().getSelectedItem();
            if (selectedInstance != null) {
                // I need to find the selected instance
                int id = -1;
                for (int i = 0; i < instances.length; i++)
                    if (instances[i].getName().equals(selectedInstance))
                        id = instances[i].getId();

                //Make request to get the solution from server and write in file:
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8088/instances/" + id;
                ResponseEntity<TSPInstance> response = null;
                try{
                    response = restTemplate.getForEntity(url, TSPInstance.class);
                    tspRankerGUI.setTspInstance(response.getBody());
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(tspRankerGUI.getTspInstance());
                    Files.write(Paths.get("file.json"), json.getBytes());
                } catch (Exception e){
                    e.printStackTrace();
                    //I should not have exceptions here;
                }

                tspRankerGUI.showSecondPage(selectedInstance, id);
            }
        });

        refreshButton.setOnAction(event -> {
            getAvailableInstances();
            messageTextArea.setText("Instances list refreshed.\n");
        });

        addInstanceButton.setOnAction(event -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TSPInstance tspInstance = null;
            try {
                tspInstance = objectMapper.readValue(new File("file.json"), TSPInstance.class);
            } catch (Exception e) {
                e.printStackTrace();
                tspInstance = null;
                messageTextArea.setText("Error while reading instance from file, please check the format");
            }
            if (tspInstance == null)
                return;
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8088/instances";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TSPInstance> requestEntity = new HttpEntity<>(tspInstance, headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
                messageTextArea.setText("Server response: " + response.getBody());
            } catch (HttpClientErrorException e) {
                messageTextArea.setText("HTTP request failed: " + e.getMessage());
            }
        });

        firstPageLayout.getChildren().addAll(
                titleLabel,
                testInstancesListView,
                refreshButton,
                selectInstanceButton,
                addInstanceButton,
                messageTextArea
        );

        scene = new Scene(firstPageLayout, 600, 600);
    }

    public void getAvailableInstances() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8088/instances";
        ResponseEntity<PairIdInstance[]> response = null;
        try {
            response = restTemplate.getForEntity(url, PairIdInstance[].class);
            messageTextArea.setText("Server response: " + response.getStatusCode());
            instances = response.getBody();
        } catch (HttpClientErrorException e) {
            messageTextArea.setText("HTTP request failed: " + e.getMessage());
            instances = null;
        }

        availableInstances = FXCollections.observableArrayList();
        if (instances != null) {
            for (PairIdInstance instance : instances) {
                availableInstances.add(instance.getName());
            }
        }
        testInstancesListView.setItems(availableInstances);
    }

    public Scene getScene() {
        return scene;
    }
}
