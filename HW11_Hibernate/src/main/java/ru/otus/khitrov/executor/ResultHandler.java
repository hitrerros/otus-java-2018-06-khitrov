package ru.otus.khitrov.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler {
    void handle(ResultSet result) throws SQLException;
}
