package server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties("id")
public class TSPInstance {
    private int id;
    private String name;
    private int n;
    private Point[] points;
    public TSPInstance() {
    }
    public TSPInstance(String name, int n, Point[] points) {
        this.name = name;
        this.n = n;
        this.points = points;
    }
    public TSPInstance(int id, String name, int n, Point[] points) {
        this.id = id;
        this.name = name;
        this.n = n;
        this.points = points;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    public Point[] getPoints() {
        return points;
    }
    public void setPoints(Point[] points) {
        this.points = points;
    }
    @Override
    public String toString() {
        return "TSPInstance{" +
                "name='" + name + '\'' +
                ", n=" + n +
                ", points=" + Arrays.toString(points) +
                '}';
    }
}
