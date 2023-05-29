package server.util;

import server.service.improvement.TSPAntColonyOptimization;

import java.util.Random;

/**
 * Ant class - used in ACO
 */
public class Ant {
    /**
     * The importance of pheromones
     */
    private final double ALFA;
    /**
     * The importance of distance
     */
    private final double BETA;
    /**
     * The random factor
     */
    private final double RANDOM_FACTOR;
    private final int n;
    private int currentVertice, currentLength;
    /**
     * The tour found by the ant
     */
    private int tour[];

    private boolean visited[];
    /**
     * The cost of the tour
     */
    private long cost;
    private int[][] graph;
    private double[][] pheromones;
    /**
     * The probability that the ant will go on that edge (proportional to the distance and number of pheromones)
     */
    private double[] probabilities;
    private Random random;

    public Ant(TSPAntColonyOptimization tspAntColonyOptimization){
        n = tspAntColonyOptimization.getN();
        ALFA = tspAntColonyOptimization.getALFA();
        BETA = tspAntColonyOptimization.getBETA();
        RANDOM_FACTOR = tspAntColonyOptimization.getRANDOM_FACTOR();
        graph = tspAntColonyOptimization.getGraph();
        pheromones = tspAntColonyOptimization.getPheromones();
        tour = new int[n];
        visited = new boolean[n];
        probabilities = new double[n];
        random = new Random();
    }
    public long getCost() {
        return cost;
    }
    public int[] getTour() {
        return tour;
    }
    public void computeCost(){
        cost = 0;
        for(int i=0; i<n-1; i++)
            cost = cost + graph[tour[i]][tour[i+1]];
        cost = cost + graph[tour[0]][tour[n-1]];
    }
    public void init(){
        for(int i = 0; i < n; i++)
            visited[i] = false;
        currentVertice = random.nextInt(n);

        currentLength = 1;
        visited[currentVertice] = true;
        tour[0] = currentVertice;
    }

    /**
     * Compute the probabilities
     */
    public void computeProbabilities(){
        double pheromone = 0;
        for(int i=0; i<n; i++)
            if(!visited[i]) {
                if(graph[currentVertice][i] == 0)
                    pheromone = pheromone + 500;
                else
                    pheromone = pheromone + Math.pow(pheromones[currentVertice][i], ALFA) * Math.pow(1.0 / graph[currentVertice][i], BETA);
            }

        for(int i=0; i<n; i++)
            if(visited[i])
                probabilities[i] = 0;
            else{
                double fitness;
                if(graph[currentVertice][i] == 0)
                    fitness = 500;
                else
                    fitness = Math.pow(pheromones[currentVertice][i], ALFA) * Math.pow(1.0 / graph[currentVertice][i], BETA);
                probabilities[i] = fitness / pheromone;
            }
    }

    /**
     * Choose the next vertex that the ant will visit
     */
    public void visitNext(){
        double r = random.nextDouble();
        int nextVertice = -1;
        if(r < RANDOM_FACTOR){ // visiting something random
            int aux = random.nextInt(n - currentLength), count = 0;
            for(int i = 0; i < n; i++)
                if(!visited[i]){
                    if(count == aux) {
                        nextVertice = i;
                        break;
                    }
                    count++;
                }
        }
        else{
            computeProbabilities();
            r = random.nextDouble();
            double total = 0;
            for(int i = 0; i < n; i++){
                total = total + probabilities[i];
                if(total >= r) {
                    nextVertice = i;
                    break;
                }
            }
        }
        visited[nextVertice] = true;
        tour[currentLength] = nextVertice;
        currentVertice = nextVertice;
        currentLength++;
    }
}
