package ru.otus.khitrov.executor;

import java.sql.*;

public class PreparedExecutor extends LogExecutor {

    public PreparedExecutor(Connection connection) {
        super(connection);
    }

    public void execUpdate(String update, ExecuteHandler prepare) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(update);
            prepare.accept(stmt);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> T execUpdate(String update,  ExecuteHandler prepare, TResultHandler<T> handler) throws Exception {
        try (PreparedStatement stmt = getConnection().prepareStatement(update,Statement.RETURN_GENERATED_KEYS)) {
            prepare.accept(stmt);
            stmt.execute();
            try (ResultSet result = stmt.getGeneratedKeys()) {
                return handler.handle(result);
            }
        }
    }

    public <T> T execQuery(String query, ExecuteHandler prepare, TResultHandler<T> handler) throws Exception {
        try(PreparedStatement  stmt = getConnection().prepareStatement(query)) {
            prepare.accept(stmt);
            stmt.execute();
            try (ResultSet result = stmt.getResultSet()) {
               return handler.handle(result);
            }
        }
    }

    public <T> T execQuery(String query,  TResultHandler<T> handler) throws Exception {
        try(PreparedStatement  stmt = getConnection().prepareStatement(query)) {
            stmt.execute();
            try (ResultSet result = stmt.getResultSet()) {
                return handler.handle(result);
            }
        }
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
