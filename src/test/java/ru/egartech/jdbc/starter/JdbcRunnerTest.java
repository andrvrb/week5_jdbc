package ru.egartech.jdbc.starter;

import org.junit.jupiter.api.Test;
import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class JdbcRunnerTest {
    @Test
    public void firstTest() throws SQLException {
    String sql = """
                SELECT *
                FROM docs.documents
                """;
    try (var connection = ConnectionManager.open();
         var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
        System.out.println(connection.getSchema());
        System.out.println(connection.getTransactionIsolation());

        var executeResult = statement.executeQuery(sql);
        while (executeResult.next()) {
            System.out.println(executeResult.getLong("id"));
            System.out.println(executeResult.getString("series"));
            System.out.println(executeResult.getString("number"));
            System.out.println(executeResult.getString("name"));
            System.out.println(executeResult.getString("code_type"));
            System.out.println("------");
            }
        }
    }

    @Test
    public void secondTest() throws SQLException {
        String sql = """
                INSERT into docs.documents (series, number, name, code_type)
                values ('3344', '343434', 'справка 1', 100)
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());

            var executeResult = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                var generatedId = generatedKeys.getInt("id");
                System.out.println(generatedId);
            }

        }
    }
}