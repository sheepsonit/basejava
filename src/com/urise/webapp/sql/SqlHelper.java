package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    @FunctionalInterface
    public interface SqlExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    public static void dbConnectAndExecute(ConnectionFactory connectionFactory, String sqlCommand, SqlExecutor sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlCommand)) {
            sqlExecutor.execute(ps);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
