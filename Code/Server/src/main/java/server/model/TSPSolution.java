package server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties("id")
public class TSPSolution {
    private int id;
    private int instanceId;
    private String userName;
    private String algorithmName;
    private long value;
    public TSPSolution() {
    }
    public TSPSolution(String userName, String algorithmName, long value) {
        this.userName = userName;
        this.algorithmName = algorithmName;
        this.value = value;
    }
    public TSPSolution(int instanceId, String userName, String algorithmName, long value) {
        this.instanceId = instanceId;
        this.userName = userName;
        this.algorithmName = algorithmName;
        this.value = value;
    }
    public TSPSolution(int id, int instanceId, String userName, String algorithmName, long value) {
        this.id = id;
        this.instanceId = instanceId;
        this.userName = userName;
        this.algorithmName = algorithmName;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getInstanceId() {
        return instanceId;
    }
    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAlgorithmName() {
        return algorithmName;
    }
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }
    public static long verifySolution(TSPSolutionCandidate tspSolutionCandidate, TSPInstance tspInstance){
        List<Integer> tour = tspSolutionCandidate.getTour();

        //check if the same size
        if(tour.size() != tspInstance.getN())
            return -1;
        //check if it is a permutation
        Set<Integer> numberSet = new HashSet<>(tour);
        for(int i=0; i < tour.size(); i++)
            if(!numberSet.contains(i))
                return -1;

        long value = 0;
        Point[] points = tspInstance.getPoints();
        for(int i = 0; i<tour.size()-1; i++)
            value = value + points[tour.get(i)].distance(points[tour.get(i+1)]);
        value = value + points[tour.get(0)].distance(points[tour.get(tour.size()-1)]);
        return value;
    }
    @Override
    public String toString() {
        return "TSPSolution{" +
                "id=" + id +
                ", instanceId=" + instanceId +
                ", userName='" + userName + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", value=" + value +
                '}';
    }
}
