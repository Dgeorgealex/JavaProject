package server.service;

import server.ServerApplication;
import server.dao.TSPSolutionDAO;
import server.model.TSPInstance;
import server.service.constructive.TSPChristofides;
import server.service.constructive.TSPDoubleTree;
import server.service.constructive.TSPNearestNeighbour;
import server.service.improvement.TSPAntColonyOptimization;
import server.service.improvement.TSPGeneticAlgorithm;
import server.service.improvement.TSPSimulatedAnnealing;

public class TSPInstanceSolver extends Thread {
    private final TSPSolutionDAO tspSolutionDAO;
    TSPInstance tspInstance;
    public TSPInstanceSolver(TSPInstance tspInstance) {
        tspSolutionDAO = new TSPSolutionDAO();
        this.tspInstance = tspInstance;
    }
    @Override
    public void run() {
        System.out.println("This thread started");
        //I want to solve my instance
        TSPSolverRunner[] threads = new TSPSolverRunner[6]; // momentarily only 5
        threads[0] = new TSPSolverRunner(tspInstance.getId(), "Nearest neighbour", new TSPNearestNeighbour(tspInstance));
        threads[1] = new TSPSolverRunner(tspInstance.getId(), "Double tree", new TSPDoubleTree(tspInstance));
        threads[2] = new TSPSolverRunner(tspInstance.getId(), "Christofides", new TSPChristofides(tspInstance));
        threads[3] = new TSPSolverRunner(tspInstance.getId(),"Simulated Annealing", new TSPSimulatedAnnealing(tspInstance));
        threads[4] = new TSPSolverRunner(tspInstance.getId(), "Genetic Algorithm", new TSPGeneticAlgorithm(tspInstance));
        threads[5] = new TSPSolverRunner(tspInstance.getId(), "Ant Colony Optimization", new TSPAntColonyOptimization(tspInstance));
        for(int i=0; i<6; i++)
            threads[i].start();
        for(int i=0;i<6;i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<6;i++)
            System.out.println(tspSolutionDAO.insert(threads[i].getTspSolution()));
        System.out.println("care sunt sansele?");
        ServerApplication.sendBroadcastMessage("Server solutions for instance " + tspInstance.getName() + " finished computing. Have fun solving!");
    }
}
