package ru.otus.khitrov.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

    Connection getConnection();

    List<String> getDBMetadata( String tableName ) throws SQLException;

    void prepareTables() throws SQLException;
}
