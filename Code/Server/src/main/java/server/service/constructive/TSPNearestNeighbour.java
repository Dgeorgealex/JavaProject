package server.service.constructive;

import server.model.TSPInstance;
import server.model.Point;
import server.service.TSPSolver;

public class TSPNearestNeighbour implements TSPSolver {

    private int n;
    private Point[] points;

    public TSPNearestNeighbour(TSPInstance tspInstance) {
        this.n = tspInstance.getN();
        this.points = tspInstance.getPoints();
    }
    @Override
    public long solve(){
        long ans = 0;
        int current = 0;
        boolean[] visited = new boolean[n];
        visited[0] = true;

        for(int i=1;i<n;i++){
            int minimumDist = -1, next = -1;

            for(int j=0;j<n;j++)
                if(!visited[j] && (minimumDist == -1 || (minimumDist > points[current].distance(points[j])))){
                    minimumDist = points[current].distance(points[j]);
                    next = j;
                }

            ans = ans + minimumDist;
            visited[next] = true;
            current = next;
        }

        ans = ans + points[current].distance(points[0]);

        return ans;
    }
}
