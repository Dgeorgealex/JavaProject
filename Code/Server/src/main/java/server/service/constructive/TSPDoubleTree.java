package server.service.constructive;

import server.model.Point;
import server.model.TSPInstance;
import server.service.TSPSolver;
import server.util.CostEdge;
import server.util.DSU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Double Tree algorithm implementation
 */
public class TSPDoubleTree implements TSPSolver {
    private final int n;
    private final Point[] points;
    /**
     * The minimum spanning tree
     */
    private List<Integer>[] tree;
    /**
     * The euler tour
     */
    private List<Integer> tour;
    /**
     * All the edges in the complete graph to be sorted
     */
    private CostEdge[] edges;
    /**
     * DSU data structure
     */
    private DSU dsu;

    public TSPDoubleTree(TSPInstance tspInstance) {
        n = tspInstance.getN();
        points = tspInstance.getPoints();

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++)
            tree[i] = new ArrayList<>();

        dsu = new DSU(n);
        edges = new CostEdge[n * (n - 1) / 2];
        tour = new ArrayList<>();
    }

    /**
     * DFS to find a Euler Tour
     * @param x Current vertex
     * @param p The partent in the DFS tree
     */
    public void dfs(int x, int p){
        tour.add(x);
        for(Integer it: tree[x])
            if(it != p)
                dfs(it, x);
    }
    @Override
    public long solve() {
        int con = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                int cost = points[i].distance(points[j]);
                edges[con] = new CostEdge(cost, i, j);
                con++;
            }

        Arrays.sort(edges);

        // Compute a MST
        con = 0;
        for (int i = 0; i < n * (n - 1) / 2; i++)
            if(dsu.union(edges[i].getNodeA(), edges[i].getNodeB())) {
                con++;
                tree[edges[i].getNodeA()].add(edges[i].getNodeB());
                tree[edges[i].getNodeB()].add(edges[i].getNodeA());
                if(con == n - 1)
                    break;
            }

        //compute a DFS transversal (in a tree with double edges it is ok)
        dfs(0, -1);
        long ans = 0;
        for(int i=0; i<n-1; i++)
            ans = ans + points[tour.get(i)].distance(points[tour.get(i+1)]);
        ans = ans + points[tour.get(0)].distance(points[tour.get(n-1)]);

        return ans;
    }
}
