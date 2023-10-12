package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        String sql1 = """
                CREATE DATABASE testttt
                ;
                """;
        String sql2 = """
                DROP DATABASE testttt
                ;
                """;
        String sql3 = """
                CREATE schema docs
                ;
                """;
        String sql4 = """
                CREATE TABLE IF NOT EXISTS info (
                    id SERIAL PRIMARY KEY ,
                    data TEXT NOT NULL
                );
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());
            var executeResult = statement.execute(sql4);
            System.out.println(executeResult);
        }
    }
}
