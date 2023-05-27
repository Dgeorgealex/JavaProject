package server.util;

public class PairIdInstance {
    private int id;
    private String name;
    public PairIdInstance() {
    }
    public PairIdInstance(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PairInstanceId{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
