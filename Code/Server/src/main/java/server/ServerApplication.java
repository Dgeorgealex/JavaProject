package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.dao.TSPInstanceDAO;
import server.dao.TSPSolutionDAO;
import server.model.TSPInstance;
import server.service.constructive.TSPDoubleTree;
import server.service.constructive.TSPNearestNeighbour;
import server.service.exact.TSPJGraphT;
import server.service.improvement.TSPAntColonyOptimization;
import server.service.improvement.TSPGeneticAlgorithm;
import server.service.improvement.TSPSimulatedAnnealing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class ServerApplication {

    public static void temporaryTestForAllMyAlgorithms(TSPInstance tspInstance){
        //Nearest Neighbour
        TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour(tspInstance);
        System.out.println(tspNearestNeighbour.solve());

        //Genetic Algorithm
        TSPGeneticAlgorithm tspGeneticAlgorithm = new TSPGeneticAlgorithm(tspInstance);
        System.out.println(tspGeneticAlgorithm.solve());

        //Simulated Annealing
        TSPSimulatedAnnealing tspSimulatedAnnealing = new TSPSimulatedAnnealing(tspInstance);
        System.out.println(tspSimulatedAnnealing.solve());

        //JGraphT exact
        TSPJGraphT tspjGraphT = new TSPJGraphT(tspInstance);
        System.out.println(tspjGraphT.solve());

        //TSP doubleTree x2 approximation
        TSPDoubleTree tspDoubleTree = new TSPDoubleTree(tspInstance);
        System.out.println(tspDoubleTree.solve());

        //ACO
        TSPAntColonyOptimization tspAntColonyOptimization = new TSPAntColonyOptimization(tspInstance);
        System.out.println(tspAntColonyOptimization.solve());
    }
    public static void main(String[] args) {
        TSPInstanceDAO tspInstanceDAO = new TSPInstanceDAO();
        TSPSolutionDAO tspSolutionDAO = new TSPSolutionDAO();
        try{
            //reading Instance
            String jsonContent = new String(Files.readAllBytes(Paths.get("instances/test_5.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            TSPInstance tspInstance = objectMapper.readValue(jsonContent, TSPInstance.class);
            System.out.println(tspInstance);
            tspInstanceDAO.insert(tspInstance);
            System.out.println(tspInstanceDAO.getById(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(ServerApplication.class, args);
    }

}
