package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import server.model.TSPInstance;
import server.service.TSPSolver;
import server.service.constructive.TSPChristofides;
import server.service.improvement.TSPGeneticAlgorithm;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ChristofidesTest {
    @Test
    public void testTime() {
        TSPInstance tspInstance;
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("instances/pcb3038.tsp.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            tspInstance = objectMapper.readValue(jsonContent, TSPInstance.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        TSPSolver tspSolver = new TSPChristofides(tspInstance);

        long start = System.currentTimeMillis();
        tspSolver.solve();
        long end = System.currentTimeMillis();

        long elapsedTimeSeconds = (end - start) / 1000;
        System.out.println(elapsedTimeSeconds + " seconds");
        assert(elapsedTimeSeconds < 300);
    }
    @Test
    public void testValue() {
        TSPInstance tspInstance;
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("instances/pcb3038.tsp.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            tspInstance = objectMapper.readValue(jsonContent, TSPInstance.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        TSPSolver tspSolver = new TSPChristofides(tspInstance);

        long minimum = tspSolver.solve();
        assert(2 * minimum / 3 < 137694);
    }
}
