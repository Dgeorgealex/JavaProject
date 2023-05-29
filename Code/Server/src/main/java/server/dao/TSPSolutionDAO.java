package server.dao;

import server.model.TSPSolution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TSPSolutionDAO {
    public TSPSolutionDAO() {
    }

    //INSERT
    public boolean insert(TSPSolution tspSolution) {
        System.out.println("Is this called?");
        System.out.println(tspSolution);
        String sql = "INSERT INTO solutions (instance_id, user_name, algorithm_name, value) VALUES (?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, tspSolution.getInstanceId());
            statement.setString(2, tspSolution.getUserName());
            statement.setString(3, tspSolution.getAlgorithmName());
            statement.setLong(4, tspSolution.getValue());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                tspSolution.setId(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //GET ALL
    public List<TSPSolution> getAll() {
        String sql = "SELECT * FROM solutions";
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<TSPSolution> tspSolutions = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int instanceId = resultSet.getInt("instance_id");
                String userName = resultSet.getString("user_name");
                String algorithmName = resultSet.getString("algorithm_name");
                long value = resultSet.getLong("value");
                TSPSolution tspSolution = new TSPSolution(id, instanceId, userName, algorithmName, value);
                tspSolutions.add(tspSolution);
            }
            return tspSolutions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //GET BY INSTANCE SORTED
    public List<TSPSolution> getByInstanceIdSorted(int instanceId) {
        String sql = "SELECT * FROM solutions WHERE instance_id = ? ORDER BY CASE WHEN value = 0 THEN 1 ELSE 0 END, value ASC";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, instanceId);
            ResultSet resultSet = statement.executeQuery();

            List<TSPSolution> tspSolutions = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("user_name");
                String algorithmName = resultSet.getString("algorithm_name");
                long value = resultSet.getLong("value");

                TSPSolution tspSolution = new TSPSolution(id, instanceId, userName, algorithmName, value);
                tspSolutions.add(tspSolution);
            }
            return tspSolutions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //FIND AFTER USERNAME AND ALGORITHM
    public boolean findAfterUsernameAndAlgorithm(String userName, String algorithmName) {
        String sql = "SELECT * FROM solutions WHERE user_name = ? AND algorithm_name = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userName);
            statement.setString(2, algorithmName);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
