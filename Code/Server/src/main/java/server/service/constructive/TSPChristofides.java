package server.service.constructive;

import server.model.Point;
import server.model.TSPInstance;
import server.util.CostEdge;
import server.util.DSU;

import java.util.ArrayList;
import java.util.List;

public class TSPChristofides {
    private final int n;
    private final Point[] points;
    private List<Integer> EulerTour;
    private CostEdge[] edges;
    private DSU dsu;

    public TSPChristofides(TSPInstance tspInstance){
        n = tspInstance.getN();
        points = tspInstance.getPoints();
    }
}
