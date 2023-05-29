package server.service.improvement;

import server.model.TSPInstance;
import server.model.Point;
import server.service.TSPSolver;
import server.util.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class TSPSimulatedAnnealing implements TSPSolver {
    private static final double T_MIN = 1e-10;
    private static final double ALFA = 0.99;
    private static final double INIT_TEMPERATURE = 100000;
    private static final int NR_ITERATIONS = 10;
    private final int n;
    private final Point[] points;
    private final Random random;
    public TSPSimulatedAnnealing(TSPInstance tspInstance) {
        this.n = tspInstance.getN();
        this.points = tspInstance.getPoints();
        this.random = new Random();
    }
    private void generateRandomPermutation(int[] permutation) {
        ArrayUtils.generateRandomPermutation(permutation);
    }
    private long evaluatePermutation(int[] permutation){
        long distance = 0;
        for(int i=0;i<n-1;i++)
            distance = distance + points[permutation[i]].distance(points[permutation[i+1]]);

        distance = distance + points[permutation[0]].distance(points[permutation[n-1]]);

        return distance;
    }
    private void reverse(int[] permutation){
        int x = random.nextInt(n), y = random.nextInt(n);
        if(x == y)
            return;
        if(x > y){
            int temp = x;
            x = y;
            y = temp;
        }
        ArrayUtils.reverseArrayPart(permutation, x, y);
    }
    private void transport(int[] permutation){
        int x = random.nextInt(n), y = random.nextInt(n);
        if( x > y ){
            int temp = x;
            x = y;
            y = temp;
        }

        int poz = random.nextInt(n - y + x);
        int[] aux = new int[n];

        for(int i=0; i<n; i++) {
            aux[i] = permutation[i];
            permutation[i] = -1;
        }

        int k = 0;
        for(int i=x ; i<=y; i++){
            permutation[poz + k] = aux[i];
            k++;
        }

        k = 0;
        for(int i = 0; i<n; i++){
            if( k == x )
                k = k + y - x + 1;

            if(permutation[i] == -1){
                permutation[i] = aux[k];
                k++;
            }
        }

    }
    @Override
    public long solve(){
        if( n > 5000 )
            return 0;

        int k = 0;
        long minimum, neighbourValue;
        int[] permutation = new int[n];
        int[] neighbour = new int[n];

        generateRandomPermutation(permutation);
        minimum = evaluatePermutation(permutation);

        do{
            k++;
            generateRandomPermutation(permutation);
            long value = evaluatePermutation(permutation);
            minimum = Math.min(minimum,value);

            double T = INIT_TEMPERATURE;
            while(T > T_MIN){
                for(int i=1; i<=100; i++){
                    int v = random.nextInt(2);

                    neighbour = Arrays.copyOf(permutation, permutation.length);

                    if(v==0)
                        transport(neighbour);
                    else
                        reverse(neighbour);

                    neighbourValue = evaluatePermutation(neighbour);

                    minimum = Math.min(minimum, neighbourValue);

                    boolean accepted = false;
                    if(neighbourValue < value)
                        accepted = true;
                    else if(random.nextDouble() < Math.exp((value - neighbourValue) / T))
                        accepted = true;

                    if(accepted){
                        permutation = Arrays.copyOf(neighbour, neighbour.length);
                        value = neighbourValue;
                    }
                }
                T = T * ALFA;
            }
        } while(k < NR_ITERATIONS);
        return minimum;
    }
}
