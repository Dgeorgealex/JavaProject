package generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Point;
import model.TSPInstance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TSPlibToJSON {
    public static void main(String args[]){
        String folderPath = "TSPlib";

        // Create an ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Get all files in the folder
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if(files != null){
            for(File file: files){
                try{
                    Scanner in = new Scanner(file);
                    String name = in.next();
                    System.out.println(name);
                    int n = in.nextInt();
                    System.out.println(n);
                    Point[] p = new Point[n];
                    for(int i=0;i<n;i++)
                        p[i] = new Point();

                    for(int i=0;i<n;i++){
                        int index = in.nextInt();
                        double x = in.nextDouble();
                        double y = in.nextDouble();
                        System.out.println(x + " " + y);
                        p[i].setX(x);
                        p[i].setY(y);
                    }
                    TSPInstance tspInstance = new TSPInstance(name, n, p);
                    mapper.writeValue(new File("TSPlibJSON/" + file.getName() + ".json"), tspInstance);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
