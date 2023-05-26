package server.util;

import server.model.Point;

import java.util.Random;

public class Chromosome {
    private final int n;
    private int[] gene;
    private final Point[] points;
    private long cost;
    private final Random random;

    public int[] getGene() {
        return gene;
    }

    public long getCost() {
        return cost;
    }
    public Chromosome(int n, Point[] points) {
        this.n = n;
        this.points = points;
        this.gene = new int[n];
        random = new Random();
    }
    public void initRandom() {
        gene = new int[n];
        ArrayUtils.generateRandomPermutation(gene);
    }
    private void reverseGene(int x, int y) {
        ArrayUtils.reverseArrayPart(gene, x, y);
    }
    public void mutate2opt() {
        int swaps = 50;
        int searches = 500;
        evaluate();
        for(int i=1; i<=searches; i++){
            int x = random.nextInt(n), y = random.nextInt(n);

            if(x==y)
                continue;

            if(x > y){
                int temp = x;
                x = y;
                y = temp;
            }

            if(x==0 || y == n-1)
                continue;

            long lengthDelta =
                    - points[gene[x-1]].distance(points[gene[x]])
                    - points[gene[y]].distance(points[gene[y+1]])
                    + points[gene[x-1]].distance(points[gene[y]])
                    + points[gene[y+1]].distance(points[gene[x]]);

            if(lengthDelta < 0){
                swaps--;
                reverseGene(x, y);
                evaluate();
                if(swaps == 0)
                    break;
            }
        }
    }
    public void crossOX(Chromosome chromosome){
        int[] p1 = this.gene;
        int[] p2 = chromosome.getGene();
        boolean[] f = new boolean[n];
        int[] child = new int[n];

        int x = random.nextInt(n), y = random.nextInt(n);
        if(x==y)
            return;

        for(int i = x; i <= y; i++) {
            child[i] = p1[i];
            f[p1[i]] = true;
        }

        int next = (y + 1) % n;

        for(int i = y + 1; i < n; i++)
            if(!f[p2[i]]){
                child[next] = p2[i];
                f[p2[i]] = true;
                next++;
                next = next % n;
            }

        for(int i = 0; i <= y; i++)
            if(!f[p2[i]]){
                child[next] = p2[i];
                f[p2[i]] = true;
                next++;
                next = next % n;
            }

        if(random.nextBoolean())
            for(int i = 0; i<n; i++)
                p1[i] = child[i];
        else
            for(int i = 0; i<n; i++)
                p2[i] = child[i];
    }
    public void copyGenes(Chromosome chromosome){
        int[] from = chromosome.getGene();
        for(int i=0;i<n;i++)
            gene[i] = from[i];
    }
    public void evaluate() {
        cost = 0;
        for (int i = 0; i < n - 1; i++) {
            cost += points[gene[i]].distance(points[gene[i + 1]]);
        }
        cost += points[gene[0]].distance(points[gene[n - 1]]);
    }
}
