package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {

    public static void main(String[] args) throws SQLException {
        long infoId = 5;
        var deleteDocSql = "DELETE FROM docs d WHERE d.info_id = " + infoId;
        var deleteInfoSql = "DELETE FROM info i WHERE i.id = " + infoId;
        var createTableSql = "CREATE TABLE test9 (id SERIAL PRIMARY KEY);";
// выносим Сonnection и Statement из блока try with resourse чтобы обрабатывать исключения самостоятельно
        Connection connection = null;
        Statement statement = null;
//        PreparedStatement deleteInfoStatement = null;
//        PreparedStatement deleteDocStatement = null;
        try {
            connection = ConnectionManager.open();
// отключаем автоматический Commit (делаем перед выполнением первого запроса)
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(deleteDocSql);
            statement.addBatch(deleteInfoSql);
            statement.addBatch(createTableSql);

            var ints = statement.executeBatch();
// выполняем commit
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
// всегда закрываем соединения если они не null
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}
