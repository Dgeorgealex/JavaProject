package server.TSPController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dao.TSPInstanceDAO;
import server.dao.TSPSolutionDAO;
import server.model.TSPInstance;
import server.model.TSPSolution;
import server.model.TSPSolutionCandidate;
import server.service.TSPInstanceSolver;
import server.util.PairIdInstance;

import java.util.List;

@RestController
@RequestMapping("/instances")
public class TSPController {
    TSPInstanceDAO tspInstanceDAO = new TSPInstanceDAO();
    TSPSolutionDAO tspSolutionDAO = new TSPSolutionDAO();

    // REQUESTS FOR TSPInstance
    @PostMapping
    public ResponseEntity<String> createInstance(@RequestBody TSPInstance tspInstance){
        if(tspInstanceDAO.existsByName(tspInstance.getName()))
            return new ResponseEntity<>("A instance with the same name already exists", HttpStatus.CONFLICT);

        tspInstanceDAO.insert(tspInstance);
        System.out.println("Tsp instance added " + tspInstance.getName());
        System.out.println(tspInstance.getId());
        TSPInstanceSolver tspInstanceSolver = new TSPInstanceSolver(tspInstance);
        tspInstanceSolver.start();
        return new ResponseEntity<>("TSP instance created, id:" + tspInstance.getId(), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<PairIdInstance>> getAvailableInstances(){
        List<PairIdInstance> list = tspInstanceDAO.getAvailableInstances();
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TSPInstance> getInstanceById(@PathVariable int id){
        TSPInstance tspInstance = tspInstanceDAO.getById(id);
        if(tspInstance != null)
            return new ResponseEntity<>(tspInstance, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{id}/solutions")
    public ResponseEntity<String> postSolution(@PathVariable int id, @RequestBody TSPSolutionCandidate candidateSolution){
        TSPInstance tspInstance = tspInstanceDAO.getById(id);
        if(tspInstance == null)
            return new ResponseEntity<>("Problem instance not found", HttpStatus.NOT_FOUND);

        long value = TSPSolution.verifySolution(candidateSolution, tspInstance);
        if(value != -1){
            if(candidateSolution.getName().equals("Server"))
                return new ResponseEntity<>("You cannot use Server as a solution name", HttpStatus.UNAUTHORIZED);

            if(tspSolutionDAO.findAfterUsernameAndAlgorithm(candidateSolution.getName(), candidateSolution.getAlgorithmName()))
                return new ResponseEntity<>("A solution with the same pair (name, algorithm) exists, change the name or the algorithm", HttpStatus.UNAUTHORIZED);

            TSPSolution tspSolution = new TSPSolution(tspInstance.getId(), candidateSolution.getName(), candidateSolution.getAlgorithmName(), value);
            tspSolutionDAO.insert(tspSolution);
            return new ResponseEntity<>("Solution valid: " + tspSolution.getId(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Solution is not valid", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}/solutions")
    public ResponseEntity<List<TSPSolution>> getSolutions(@PathVariable int id){
        List<TSPSolution> tspSolutions = tspSolutionDAO.getByInstanceIdSorted(id);
        if(tspSolutions == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(tspSolutions, HttpStatus.OK);
    }
    @GetMapping("/debug")
    public ResponseEntity<List<TSPInstance>> getAllInstances(){
        List<TSPInstance> tspInstances = tspInstanceDAO.getAll();
        if(tspInstances == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(tspInstances, HttpStatus.OK);
    }
    @GetMapping("/solutions/debug")
    public ResponseEntity<List<TSPSolution>> getAllSolutions(){
        List<TSPSolution> tspSolutions = tspSolutionDAO.getAll();
        if(tspSolutions == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(tspSolutions, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstanceById(@PathVariable int id){
        boolean success = tspInstanceDAO.delete(id);
        if(success)
            return new ResponseEntity<>("Instance deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Instance failed to delete", HttpStatus.NOT_FOUND);
    }
}
