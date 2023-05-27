package server.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static HikariDataSource ds;
    private Database(){}

    public static Connection getConnection(){
        if(ds == null)
            createConnection();

        try{
            return ds.getConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private static void createConnection(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/genetic");
        config.setUsername("postgres");
        config.setPassword("student");
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);
    }
}
