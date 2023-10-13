package ru.egartech.jdbc.starter;

import ru.egartech.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {

    public static void main(String[] args) throws SQLException {
        long infoId = 3;
        var deleteDocSql = "DELETE FROM docs d WHERE d.info_id = ?";
        var deleteInfoSql = "DELETE FROM info i WHERE i.id = ?";
// выносим Сonnection и Statement из блока try with resourse чтобы обрабатывать исключения самостоятельно
        Connection connection = null;
        PreparedStatement deleteInfoStatement = null;
        PreparedStatement deleteDocStatement = null;
        try {
            connection = ConnectionManager.open();
            deleteDocStatement = connection.prepareStatement(deleteDocSql);
            deleteInfoStatement = connection.prepareStatement(deleteInfoSql);
// отключаем автоматический Commit
            connection.setAutoCommit(false);

            deleteDocStatement.setLong(1, infoId);
            deleteInfoStatement.setLong(1, infoId);

            deleteDocStatement.executeUpdate();
// прерывание выполнения транзакций (для тестирования)
/*            if (true) {
                throw new RuntimeException("Ooops");
            }*/

            deleteInfoStatement.executeUpdate();
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
            if (deleteInfoStatement != null) {
                deleteInfoStatement.close();
            }
            if (deleteDocStatement != null) {
                deleteDocStatement.close();
            }
        }
    }
}
