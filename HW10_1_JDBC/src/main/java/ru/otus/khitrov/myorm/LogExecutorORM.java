package ru.otus.khitrov.myorm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogExecutorORM extends  LogExecutor {

   public LogExecutorORM(Connection connection) throws SQLException {
        super(connection);
        connection.setAutoCommit(false);
    }


    public <T> T execUpdate(String query, TResultHandler<T> handler) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
            connection.commit();
            try (ResultSet result = stmt.getGeneratedKeys()) {
                return handler.handle(result);
            }
        }
    }

    public void execBatch(String...queries) throws SQLException {

        try (Statement stmt = connection.createStatement()) {
            for (String query: queries)  stmt.addBatch(query);
            stmt.executeBatch();
            connection.commit();
      }
    }

 }


