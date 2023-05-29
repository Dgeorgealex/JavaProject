package server.service;

import server.model.TSPSolution;

/**
 * A thread that solves a instance of TSP with one of the implemented algorithms
 */
public class TSPSolverRunner extends Thread{
    private int instanceId;
    private String algorithmName;
    private TSPSolver tspSolver;
    private TSPSolution tspSolution;
    public TSPSolution getTspSolution() {
        return tspSolution;
    }

    /**
     * Constructon
     * @param instanceId The instance id
     * @param algorithmName The algorithm name
     * @param tspSolver The algorithm that will be used to solve the instance
     */
    public TSPSolverRunner(int instanceId, String algorithmName, TSPSolver tspSolver) {
        this.instanceId = instanceId;
        this.algorithmName = algorithmName;
        this.tspSolver = tspSolver;
    }

    /**
     * Runs the algorithm and creates a TSP solution object
     */
    @Override
    public void run() {
        long value = tspSolver.solve();
        tspSolution = new TSPSolution(instanceId,"Server", algorithmName, value);
    }
}
