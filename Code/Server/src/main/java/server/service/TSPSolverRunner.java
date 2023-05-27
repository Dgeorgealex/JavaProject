package server.service;

import server.model.TSPSolution;

public class TSPSolverRunner extends Thread{
    private int instanceId;
    private String algorithmName;
    private TSPSolver tspSolver;
    private TSPSolution tspSolution;
    public TSPSolution getTspSolution() {
        return tspSolution;
    }
    public TSPSolverRunner(int instanceId, String algorithmName, TSPSolver tspSolver) {
        this.instanceId = instanceId;
        this.algorithmName = algorithmName;
        this.tspSolver = tspSolver;
    }
    @Override
    public void run() {
        long value = tspSolver.solve();
        tspSolution = new TSPSolution(instanceId,"Server", algorithmName, value);
    }
}
