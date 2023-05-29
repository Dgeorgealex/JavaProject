package model;

public class TSPSolution {
    private int instanceId;
    private String userName;
    private String algorithmName;
    private long value;
    public TSPSolution() {
    }
    public TSPSolution(String userName, String algorithmName, long value) {
        this.userName = userName;
        this.algorithmName = algorithmName;
        this.value = value;
    }
    public TSPSolution(int instanceId, String userName, String algorithmName, long value) {
        this.instanceId = instanceId;
        this.userName = userName;
        this.algorithmName = algorithmName;
        this.value = value;
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
                "instanceId=" + instanceId +
                ", userName='" + userName + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", value=" + value +
                '}';
    }
}
