package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @FunctionalInterface
    public interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T dbConnectAndExecute(String sqlCommand, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlCommand)) {
            return sqlExecutor.execute(ps);

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
                throw new ExistStorageException(e.getMessage());
            throw new StorageException(e);
        }
    }
}
