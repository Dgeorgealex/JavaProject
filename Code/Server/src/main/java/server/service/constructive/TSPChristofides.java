package server.service.constructive;

import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedPerfectMatching;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import server.model.Point;
import server.model.TSPInstance;
import server.service.TSPSolver;
import server.util.CostEdge;
import server.util.DSU;
import server.util.PairIntInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * The Christofides Algorithm implementation
 */
public class TSPChristofides implements TSPSolver {
    private final int n;
    private final Point[] points;
    /**
     * The euler tour
     */
    private List<Integer> eulerTour;
    private List<PairIntInt>[] myGraph;
    private int edgeCount;
    private boolean[] visitedEdges;
    /**
     * All the edges in the complete graph to be sorted
     */
    private CostEdge[] edges;
    /**
     * DSU data structure
     */
    private DSU dsu;

    public TSPChristofides(TSPInstance tspInstance){
        n = tspInstance.getN();
        points = tspInstance.getPoints();

        myGraph = new ArrayList[n];
        for(int i=0;i<n;i++)
            myGraph[i] = new ArrayList<>();

        dsu = new DSU(n);
        edges = new CostEdge[n * ( n - 1 ) / 2];
        eulerTour = new ArrayList<>();
    }

    /**
     * Using the JGraphT algorithm to find a minimum perfect matching in a complete graph
     * @param oddVertices The vertices that have a odd degree
     */
    private void addMatchingToMyGraph(List<Integer> oddVertices){
        if(oddVertices.size() == 0)
            return;

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for(Integer it : oddVertices)
            graph.addVertex(it);

        for(int i = 0; i < oddVertices.size(); i++)
            for(int j = i + 1; j < oddVertices.size(); j++){
                int distance = points[oddVertices.get(i)].distance(points[oddVertices.get(j)]);
                graph.addEdge(oddVertices.get(i), oddVertices.get(j));
                DefaultWeightedEdge e = graph.getEdge(oddVertices.get(i), oddVertices.get(j));
                graph.setEdgeWeight(e, distance);
            }

        MatchingAlgorithm.Matching<Integer, DefaultWeightedEdge> matching = (new KolmogorovWeightedPerfectMatching<>(graph)).getMatching();
        Set<DefaultWeightedEdge> edgeSet = matching.getEdges();
        for(DefaultWeightedEdge edge: edgeSet){
            int x = graph.getEdgeSource(edge);
            int y = graph.getEdgeTarget(edge);
            edgeCount++;
            myGraph[x].add(new PairIntInt(edgeCount, y));
            myGraph[y].add(new PairIntInt(edgeCount, x));
        }
    }

    /**
     * The Fleury Algorithm of finding a euler tour
     * @param x The current vertex
     */
    private void fleuryAlgorithm(int x){
        while(!myGraph[x].isEmpty()){
            PairIntInt u = myGraph[x].remove(myGraph[x].size() - 1);
            if(!visitedEdges[u.getFirst()]) {
                visitedEdges[u.getFirst()] = true;
                fleuryAlgorithm(u.getSecond());
            }
        }
        eulerTour.add(x);
    }
    @Override
    public long solve(){
        // The begining is the same to the DoubleTree Algorithm
        edgeCount = 0;
        for(int i=0; i<n; i++)
            for(int j=i+1; j<n; j++){
                int cost = points[i].distance(points[j]);
                edges[edgeCount] = new CostEdge(cost, i, j);
                edgeCount++;
            }

        Arrays.sort(edges);

        //compute a MST
        edgeCount = 0;
        for(int i=0;i < n*(n-1)/2; i++)
            if(dsu.union(edges[i].getNodeA(), edges[i].getNodeB())){
                edgeCount++;
                myGraph[edges[i].getNodeA()].add(new PairIntInt(edgeCount, edges[i].getNodeB()));
                myGraph[edges[i].getNodeB()].add(new PairIntInt(edgeCount, edges[i].getNodeA()));
                if(edgeCount == n - 1)
                    break;
            }

        List<Integer> oddVertices = new ArrayList<>();
        for(int i=0; i<n;i++)
            if(myGraph[i].size()%2 == 1)
                oddVertices.add(i);

        addMatchingToMyGraph(oddVertices);

        visitedEdges = new boolean[edgeCount + 1];
        fleuryAlgorithm(0);

        boolean[] is = new boolean[n];
        List<Integer> tour = new ArrayList<>();
        for(Integer it : eulerTour)
            if(!is[it]){
                tour.add(it);
                is[it] = true;
            }

        long cost = 0;
        for(int i=0; i<n-1; i++)
            cost = cost + points[tour.get(i)].distance(points[tour.get(i+1)]);
        cost = cost + points[tour.get(0)].distance(points[tour.get(n-1)]);

        return cost;
    }
}
