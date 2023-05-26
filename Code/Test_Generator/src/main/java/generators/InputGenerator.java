package generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Point;
import model.TSPInstance;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class InputGenerator {

    public static void main(String args[]){
        Scanner in = new Scanner(System.in);

        ObjectMapper mapper = new ObjectMapper();
        String name = in.next();
        int n = in.nextInt();

        Point[] p = new Point[n];
        for(int i=0; i<n; i++)
            p[i] = new Point();

        for(int i=0;i<n;i++){
            double x = in.nextDouble();
            double y = in.nextDouble();
            p[i].setX(x);
            p[i].setY(y);
        }

        TSPInstance tspInstance = new TSPInstance(name, n, p);
        try{
            mapper.writeValue(new File("TSPManualJSON/" + name + ".json"), tspInstance);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
