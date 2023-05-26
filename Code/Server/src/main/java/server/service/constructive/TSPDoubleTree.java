package server.service.constructive;

import server.model.TSPInstance;
import server.util.CostEdge;
import server.util.DSU;

import java.util.ArrayList;
import java.util.List;

public class TSPDoubleTree {
    private final int n;
    private List<Integer>[] tree;
    private CostEdge[] edges;
    private DSU dsu;

    public TSPDoubleTree(TSPInstance tspInstance) {
         n = tspInstance.getN();
         tree = new ArrayList[n];

         for(int i=0;i<n;i++)
             tree[i] = new ArrayList<>();

         dsu = new DSU(n);
         edges = new CostEdge[n * (n-1) / 2];
    }

    public long solve(){
            
    }
}
