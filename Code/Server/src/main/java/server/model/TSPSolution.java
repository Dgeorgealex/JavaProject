package server.model;

public class TSPSolution {
    private int id;
    private int instanceId;
    private String userName;
    private String algorithmName;
    private long value;
    public TSPSolution() {
    }
    public TSPSolution(int id, int instanceId, String userName, String algorithmName, long value) {
        this.id = id;
        this.instanceId = instanceId;
        this.userName = userName;
        this.algorithmName = algorithmName;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getInstanceId() {
        return instanceId;
    }
    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAlgorithmName() {
        return algorithmName;
    }
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "TSPSolution{" +
                "id=" + id +
                ", instanceId=" + instanceId +
                ", userName='" + userName + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", value=" + value +
                '}';
    }
}
