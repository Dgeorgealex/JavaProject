package model;

import java.util.Arrays;

public class TSPInstance {
    private String name;
    private int n;
    private Point[] points;

    public TSPInstance() {
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

    public TSPInstance(String name, int n, Point[] points) {
        this.name = name;
        this.n = n;
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
