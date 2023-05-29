package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.model.TSPInstance;
import server.service.constructive.TSPDoubleTree;
import server.service.constructive.TSPNearestNeighbour;
import server.service.exact.TSPJGraphT;
import server.service.improvement.TSPAntColonyOptimization;
import server.service.improvement.TSPGeneticAlgorithm;
import server.service.improvement.TSPSimulatedAnnealing;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServerApplication {
    private static List<PrintWriter> clients = new ArrayList<>();
    private static ServerSocket serverSocket = null;
    private static int PORT = 8089; //Spring server runs on 8088

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Socket server running, waiting for clients...");

            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(writer);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static synchronized void sendBroadcastMessage(String message){
        for(PrintWriter writer: clients)
            writer.println(message);
    }

}
