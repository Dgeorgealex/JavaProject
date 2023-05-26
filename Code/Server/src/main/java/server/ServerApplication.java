package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.model.TSPInstance;
import server.service.constructive.TSPNearestNeighbour;
import server.service.exact.TSPJGraphT;
import server.service.improvement.TSPGeneticAlgorithm;
import server.service.improvement.TSPSimulatedAnnealing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        try{
            //reading Instance
            String jsonContent = new String(Files.readAllBytes(Paths.get("instances/test_15.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            TSPInstance tspInstance = objectMapper.readValue(jsonContent, TSPInstance.class);
            System.out.println(tspInstance);

            //nearest neighbour
            TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour(tspInstance);
            System.out.println(tspNearestNeighbour.solve());

            //genetic algorithm
            TSPGeneticAlgorithm tspGeneticAlgorithm = new TSPGeneticAlgorithm(tspInstance);
            System.out.println(tspGeneticAlgorithm.solve());


            //simulated annealing
            TSPSimulatedAnnealing tspSimulatedAnnealing = new TSPSimulatedAnnealing(tspInstance);
            System.out.println(tspSimulatedAnnealing.solve());

            //JGraphT exact
            TSPJGraphT tspjGraphT = new TSPJGraphT(tspInstance);
            System.out.println(tspjGraphT.solve());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //SpringApplication.run(ServerApplication.class, args);
    }

}