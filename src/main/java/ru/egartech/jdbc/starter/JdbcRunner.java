package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
/*        Long docId = 2L;
        var result = getDocById(docId);
        System.out.println(result);*/
        var result = getDocsRegBetween(LocalDate.of(2023, 9, 4).atStartOfDay(), LocalDateTime.now());
        System.out.println(result);
    }

    private static List<Long> getDocsRegBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id
                FROM docs.document_reg
                WHERE date_reg BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
    // ограничение количества строк, которые может вернуть запрос
            preparedStatement.setFetchSize(50);
    // Устанавливает количество секунд, в течение которых драйвер будет
    // ждать выполнения объекта Statement , до заданного количества секунд
            preparedStatement.setQueryTimeout(10);
    // Устанавливает ограничение на максимальное количество строк, которое может содержать любой объект ResultSet ,
    // сгенерированный этим объектом Statement , до заданного числа
            preparedStatement.setMaxRows(100);

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
        }

        return result;
    }

    private static List<Long> getDocById(Long docId) throws SQLException {
        String sql = """
                SELECT id
                FROM docs.documents
                WHERE id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, docId);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // NULL safe
            }
        }

        return result;
    }
}
