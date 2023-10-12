package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        String sql1 = """
                INSERT into info (data)
                 VALUES ('Document1'), ('Document2'), ('Document3'), ('Document4') 
                ;
                """;
        String sql2 = """
                UPDATE info
                SET data = 'TestTest'
                WHERE id = 4
                RETURNING *
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());
            var executeResult = statement.execute(sql2);
            System.out.println(executeResult);
        }
    }
}
