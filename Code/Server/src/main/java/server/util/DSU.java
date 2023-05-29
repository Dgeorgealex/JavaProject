package server.util;

/**
 * DSU data structure used to compute a MST
 */
public class DSU {
    private final int n;
    private int[] t;
    private int[] sz;
    public DSU(int n) {
        this.n = n;
        this.t = new int[n];
        this.sz = new int[n];
        for(int i=0; i<n; i++){
            t[i] = i;
            sz[i] = 1;
        }
    }

    public int getN() {
        return n;
    }
    public int[] getT() {
        return t;
    }
    public void setT(int[] t) {
        this.t = t;
    }

    // we will implement small to large merging
    public boolean union(int x, int y){
        x = find(x);
        y = find(y);
        if(x == y)
            return false;

        if(sz[x] < sz[y]){
            int temp = x;
            x = y;
            y = temp;
        }

        sz[x] = sz[x] + sz[y];
        t[y] = x;
        return true;
    }

    public int find(int x){
        if(x!=t[x])
            t[x] = find(t[x]);
        return t[x];
    }
}
