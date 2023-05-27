package server.service.improvement;

import server.model.TSPInstance;
import server.service.TSPSolver;
import server.util.Chromosome;
import server.util.PairDoubleInt;
import server.model.Point;

import java.util.*;
import java.util.stream.Collectors;

public class TSPGeneticAlgorithm implements TSPSolver {
    private static final int POP_SIZE = 200;
    private static final double CP = 0.85;
    private static final int ELITISM = 20;
    private static final int NR_GENERATIONS = 2000;
    private int n;
    private Point[] points;
    private Random random;
    private Chromosome[] chrom;
    private Chromosome[] chromAux;
    private Chromosome best;

    public TSPGeneticAlgorithm(TSPInstance tspInstance) {
        this.n = tspInstance.getN();
        this.points = tspInstance.getPoints();
        this.random = new Random();
        chrom = new Chromosome[POP_SIZE];
        chromAux = new Chromosome[POP_SIZE];
        best = new Chromosome(n, points);

        for (int i = 0; i < POP_SIZE; i++) {
            chrom[i] = new Chromosome(n, points);
            chromAux[i] = new Chromosome(n, points);
        }
    }

    private void shuffle(int[] v) {
        List<Integer> vList = Arrays.stream(v).boxed().collect(Collectors.toList());
        Collections.shuffle(vList);
        for (int i = 0; i < v.length; i++) {
            v[i] = vList.get(i);
        }
    }

    private void addElites(PairDoubleInt[] parent, int[] offspring){
        Arrays.sort(parent, Comparator.comparingDouble((PairDoubleInt p) -> p.getFirst()).reversed());
        for(int i = 0; i < ELITISM; i++)
            offspring[i] = parent[i].getSecond();
    }

    private void selectRoulette(PairDoubleInt[] parent, int[] offspring){
        double F = 0, r;
        double[] q = new double[POP_SIZE + 1];
        for(int i = 0; i < POP_SIZE; i++)
            F += parent[i].getFirst();

        q[0] = 0;
        for(int i = 0; i<POP_SIZE; i++)
            q[i+1] = q[i] + parent[i].getFirst() / F;

        for(int i=ELITISM; i<POP_SIZE; i++){
            r = random.nextDouble();
            for(int j = 1; j <= POP_SIZE; j++)
                if(r < q[j]) {
                    offspring[i] = j - 1;
                    break;
                }
        }
        shuffle(offspring);
    }
    @Override
    public long solve() {
        // Initialize variables
        int[] offspring = new int[POP_SIZE];
        int[] cross = new int[POP_SIZE];
        PairDoubleInt[] parent = new PairDoubleInt[POP_SIZE];
        double r;

        // Initialize population
        for (int i = 0; i < POP_SIZE; i++) {
            chrom[i].initRandom();
            chrom[i].evaluate();
            parent[i] = new PairDoubleInt();
        }

        long minimum = chrom[0].getCost();

        // evaluation
        for(int i = 0; i < POP_SIZE; i++){
            double fitness = (double) 1 / chrom[i].getCost();
            parent[i].setFirst(fitness);
            parent[i].setSecond(i);
            minimum = Math.min(minimum, chrom[i].getCost());
        }

        int t = 0;
        while (t < NR_GENERATIONS) {
            // parent selection
            addElites(parent, offspring);
            selectRoulette(parent, offspring);

            // get the next population
            for(int i=0; i<POP_SIZE; i++)
                chromAux[i].copyGenes(chrom[offspring[i]]);
            for(int i=0; i<POP_SIZE; i++)
                chrom[i].copyGenes(chromAux[i]);

            // crossover
            int k = 0;
            for (int i = ELITISM; i < POP_SIZE; i++) {
                r = random.nextDouble();
                if (r < CP)
                    cross[k++] = i;
            }
            if (k % 2 == 1)
                k--;

            shuffle(cross);
            for(int i=0; i<k; i+=2)
                chrom[i].crossOX(chrom[i+1]);

            // mutation
            double mp = (double)1 / (2 + (double)POP_SIZE / NR_GENERATIONS * t) + 0.05;
            for(int i=ELITISM; i<POP_SIZE; i++) {
                r = random.nextDouble();
                if(r < mp)
                    chrom[i].mutate2opt();
            }

            for(int i=0; i<POP_SIZE; i++){
                chrom[i].evaluate();
                double fitness = (double) 1 / chrom[i].getCost();
                parent[i].setFirst(fitness);
                parent[i].setSecond(i);

                if(minimum > chrom[i].getCost()){
                    minimum = chrom[i].getCost();
                    best.copyGenes(chrom[i]);
                }
            }
            t++;
        }
        return minimum;
    }
}
