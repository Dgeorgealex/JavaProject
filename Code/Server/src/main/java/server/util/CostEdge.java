package server.util;

/**
 * Used for implementing Double Tree and Christofides, can compare two edges
 */
public class CostEdge implements Comparable<CostEdge> {
    private final int cost;
    private final int nodeA;
    private final int nodeB;
    public CostEdge(int cost, int nodeA, int nodeB) {
        this.cost = cost;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }
    public int getCost() {
        return cost;
    }
    public int getNodeA() {
        return nodeA;
    }
    public int getNodeB() {
        return nodeB;
    }
    @Override
    public int compareTo(CostEdge o) {
        return Integer.compare(this.cost, o.getCost());
    }
}
