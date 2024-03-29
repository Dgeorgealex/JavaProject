package server.service.exact;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import server.model.Point;
import server.model.TSPInstance;
import server.service.TSPSolver;

/**
 * Finding the exact solution using JGraphT
 */
public class TSPJGraphT implements TSPSolver {
    private final SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
    public TSPJGraphT(TSPInstance tspInstance) {
        int n = tspInstance.getN();
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for(int i=0;i<n;i++)
            graph.addVertex(i);
        Point[] points = tspInstance.getPoints();
        for(int i=0;i<n;i++)
            for(int j=i+1;j<n;j++){
                int distance = points[i].distance(points[j]);
                DefaultWeightedEdge e = graph.addEdge(i, j);
                graph.setEdgeWeight(e, distance);
            }
    }
    @Override
    public long solve(){
        if(graph.vertexSet().size() > 30)
            return 0;
        HamiltonianCycleAlgorithm<Integer, DefaultWeightedEdge> heldKarpTSP = new HeldKarpTSP<>();
        GraphPath<Integer, DefaultWeightedEdge> path = heldKarpTSP.getTour(graph);
        return (long) path.getWeight();
    }
}
