package server.util;

public class PairIntInt {
    private int first;
    private int second;
    public PairIntInt(){
    }
    public PairIntInt(int first, int second) {
        this.first = first;
        this.second = second;
    }
    public int getFirst() {
        return first;
    }
    public void setFirst(int first) {
        this.first = first;
    }
    public int getSecond() {
        return second;
    }
    public void setSecond(int second) {
        this.second = second;
    }
    @Override
    public String toString() {
        return "PairIntInt{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
