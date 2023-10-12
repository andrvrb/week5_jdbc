package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
//        String codeType = "'02' OR '' = ''";
//        String codeType = "'02' OR '' = ''; DROP TABLE docs.documents";
        String codeType = " '02' ";
        var result = getDocsId(codeType);
        System.out.println(result);
    }

    private static List<Long> getDocsId(String codeType) throws SQLException {
        String sql = """
                SELECT id
                FROM docs.documents
                WHERE code_type = %s
                """.formatted(codeType);
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // NULL safe
            }
        }

        return result;
    }
}
