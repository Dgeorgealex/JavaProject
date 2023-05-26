package server.service.improvement;

import server.model.Point;
import server.model.TSPInstance;
import server.util.Ant;

public class TSPAntColonyOptimization {
    private final double C = 1.0; // initial value of pheromones
    private final double ALFA = 1.0; // pheromone importance
    private final double BETA = 5.0; // distance priority
    private final double EVAPORATION_RATE = 0.5; // self-explanatory
    private final double RANDOM_FACTOR = 0.01; // probability that the ant does something random
    private final double Q = 500; // total amount of pheromone left by an ant
    private int NR_ANTS;
    private final int NR_ITERATIONS = 100;
    private final int n;
    private final Point[] points;
    private final int[][] graph;
    private double[][] pheromones;
    private final Ant[] ants;

    public TSPAntColonyOptimization(TSPInstance tspInstance) {
        n = tspInstance.getN();
        points = tspInstance.getPoints();
        graph = new int[n][n];
        pheromones = new double[n][n];
        NR_ANTS = Math.min((int) (n * 0.8), 100);
        ants = new Ant[NR_ANTS];
    }

    public int getN() {
        return n;
    }

    public double getALFA() {
        return ALFA;
    }

    public double getBETA() {
        return BETA;
    }

    public double getRANDOM_FACTOR() {
        return RANDOM_FACTOR;
    }

    public int[][] getGraph() {
        return graph;
    }

    public double[][] getPheromones() {
        return pheromones;
    }

    public long solve() {
        long minimum = Long.MAX_VALUE;
        // initialize graph
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                graph[i][j] = points[i].distance(points[j]);

        //initialize pheromones I guess
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                pheromones[i][j] = C;

        //initialize Ants
        for (int i = 0; i < NR_ANTS; i++)
            ants[i] = new Ant(this);

        for (int t = 0; t < NR_ITERATIONS; t++) {
            //init ants
            for (int a = 0; a < NR_ANTS; a++)
                ants[a].init();

            //ants visit nodes
            for (int i = 1; i < n; i++)
                for (int a = 0; a < NR_ANTS; a++)
                    ants[a].visitNext();

            //ants compute tour length
            for (int a = 0; a < NR_ANTS; a++) {
                ants[a].computeCost();
                if (minimum > ants[a].getCost()) {
                    minimum = ants[a].getCost();
                }

            }

            //evaporate
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    pheromones[i][j] = pheromones[i][j] * EVAPORATION_RATE;

            //contribute with pheromones
            for (int a = 0; a < NR_ANTS; a++) {
                int[] tour = ants[a].getTour();
                double contribution = Q / ants[a].getCost();
                for (int i = 0; i < n - 1; i++)
                    pheromones[tour[i]][tour[i + 1]] += contribution;

                pheromones[tour[0]][tour[n - 1]] += contribution;
            }
        }
        return minimum;
    }
}
