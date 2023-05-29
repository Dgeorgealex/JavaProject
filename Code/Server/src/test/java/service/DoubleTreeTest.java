package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import server.model.TSPInstance;
import server.service.TSPSolver;
import server.service.constructive.TSPChristofides;
import server.service.constructive.TSPDoubleTree;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DoubleTreeTest {
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
        TSPSolver tspSolver = new TSPDoubleTree(tspInstance);

        long minimum = tspSolver.solve();
        assert(minimum < 2 * 137694);
    }
}
