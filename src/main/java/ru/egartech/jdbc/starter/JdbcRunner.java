package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
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
}
