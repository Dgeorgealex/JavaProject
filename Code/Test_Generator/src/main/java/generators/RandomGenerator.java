package generators;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Point;
import model.TSPInstance;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RandomGenerator {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);

        ObjectMapper mapper = new ObjectMapper();
        String name = in.next();
        int n = in.nextInt();

        double min = in.nextDouble();
        double max = in.nextDouble();

        Point[] p = new Point[n];
        for(int i=0;i<n;i++)
            p[i] = new Point();

        for(int i=0;i<n;i++) {
            double x = min + (max - min) * Math.random();
            double y = min + (max - min) * Math.random();
            p[i].setX(x);
            p[i].setY(y);
        }

        TSPInstance tspInstance = new TSPInstance(name, n, p);
        try {
            mapper.writeValue(new File("TSPRandomJSON/" + name + ".json"), tspInstance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
