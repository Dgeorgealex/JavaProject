package server.TSPController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dao.TSPInstanceDAO;
import server.dao.TSPSolutionDAO;
import server.model.TSPInstance;

@RestController
@RequestMapping("/instances")
public class TSPController {
    TSPInstanceDAO tspInstanceDAO = new TSPInstanceDAO();
    TSPSolutionDAO tspSolutionDAO = new TSPSolutionDAO();
    @PostMapping
    public ResponseEntity<String> createInstance(@RequestBody TSPInstance tspInstance){
        tspInstanceDAO.insert(tspInstance);
        return new ResponseEntity<>("TSP instance created, id:" + tspInstance.getId(), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TSPInstance> getInstanceById(@PathVariable int id){
        TSPInstance tspInstance = tspInstanceDAO.getById(id);
        if(tspInstance != null){
            return new ResponseEntity<>(tspInstance, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
