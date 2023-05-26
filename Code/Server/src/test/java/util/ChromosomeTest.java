package util;

import org.junit.jupiter.api.Test;
import server.model.Point;
import server.util.Chromosome;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ChromosomeTest {

    @Test
    public void testEvaluate(){
        int n = 100;
        Point[] p = new Point[n];
        double min = 0, max = 100;
        for(int i=0; i<n; i++){
            double x = min + (max - min) * Math.random();
            double y = min + (max - min) * Math.random();
            p[i] = new Point(x, y);
        }

        Chromosome c = new Chromosome(n, p);
        int[] gene = c.getGene();
        for(int i=0;i<n;i++)
            gene[i] = i;

        int distance = 0;
        for(int i=0; i<n-1; i++)
            distance = distance + p[i].distance(p[i+1]);
        distance = distance + p[0].distance(p[n-1]);

        c.evaluate();
        assertEquals(distance, c.getCost());
    }
}
