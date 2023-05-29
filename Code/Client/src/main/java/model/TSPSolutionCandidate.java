package model;

import java.util.List;

public class TSPSolutionCandidate {
    private String name;
    private String algorithmName;
    private List<Integer> tour;

    public TSPSolutionCandidate() {
    }

    public TSPSolutionCandidate(String name, String algorithmName, List<Integer> tour) {
        this.name = name;
        this.algorithmName = algorithmName;
        this.tour = tour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public List<Integer> getTour() {
        return tour;
    }

    public void setTour(List<Integer> tour) {
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "TSPSolutionCandidate{" +
                "name='" + name + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", tour=" + tour +
                '}';
    }
}
