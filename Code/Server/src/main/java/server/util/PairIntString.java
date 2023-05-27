package server.util;

public class PairIntString {
    private int first;
    private String second;

    public PairIntString() {
    }

    public PairIntString(int first, String second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
