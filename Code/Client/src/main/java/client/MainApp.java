package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class MainApp extends Application {
    static TSPRankerGUI tspRankerGUI;
    private static final int PORT = 8089;
    private static Socket socket = null;
    @Override
    public void start(Stage primaryStage){
        //initialize socket
        try {
            socket = new Socket("localhost", PORT);
            System.out.println("Connected to server via socket...");
            new ReaderThread(socket).start();
        } catch(Exception e){
            e.printStackTrace();
        }

        primaryStage.setOnCloseRequest(event -> {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });

        tspRankerGUI = new TSPRankerGUI(primaryStage);
        tspRankerGUI.showFirstPage();
    }

    static class ReaderThread extends Thread{
        private Socket socket;
        private Scanner scanner;
        public ReaderThread(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                scanner = new Scanner(socket.getInputStream());
                while (true) {
                    if (scanner.hasNextLine()) {
                        String message = scanner.nextLine();
                        System.out.println(message);
                        displayPopup(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void displayPopup(String message) {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Message Received");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            });
        }
    }
}
