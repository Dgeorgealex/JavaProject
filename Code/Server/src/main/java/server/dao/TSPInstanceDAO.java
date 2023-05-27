package server.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import server.model.TSPInstance;
import server.model.Point;
import server.util.PairIntString;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TSPInstanceDAO {
    public TSPInstanceDAO() {
    }

    // INSERT
    public boolean insert(TSPInstance tspInstance) {
        String sql = "INSERT INTO problem_instance (number_of_nodes, name, points) VALUES (?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, tspInstance.getN());
            statement.setString(2, tspInstance.getName());
            statement.setObject(3, convertPointsToJson(tspInstance.getPoints()), Types.OTHER);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                tspInstance.setId(id);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET SPECIFIC INSTANCE
    public TSPInstance getById(int id) {
        String sql = "SELECT * FROM problem_instance WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractTSPInstance(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TSPInstance> getAll() {
        String sql = "SELECT * FROM problem_instance";
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<TSPInstance> tspInstances = new ArrayList<>();
            while (resultSet.next()) {
                TSPInstance tspInstance = extractTSPInstance(resultSet);
                tspInstances.add(tspInstance);
            }
            return tspInstances;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Verify if an instance with that name exists
    public boolean existsByName(String name) {
        String sql = "SELECT * FROM problem_instance WHERE name = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET AVAILABLE INSTANCES
    public List<PairIntString> getAvailableInstances() {
        String sql = "SELECT id, name FROM problem_instance";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<PairIntString> instances = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                PairIntString instance = new PairIntString(id, name);
                instances.add(instance);
            }
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // UTIL
    private String convertPointsToJson(Point[] points) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(points);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Point[] convertJsonToPoints(String pointsJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(pointsJson, Point[].class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TSPInstance extractTSPInstance(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int n = resultSet.getInt("number_of_nodes");
            String name = resultSet.getString("name");
            String pointsJson = resultSet.getString("points");
            Point[] points = convertJsonToPoints(pointsJson);

            TSPInstance tspInstance = new TSPInstance(name, n, points);
            tspInstance.setId(id);
            return tspInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
