package ru.otus.khitrov.base;


import ru.otus.khitrov.myorm.LogExecutorORM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBServiceConnection implements DBService {

    private final Connection connection;

    public DBServiceConnection()  {
        connection = ConnectionHelper.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public List<String> getDBMetadata( String tableName ) throws SQLException {

        List<String> columns = new ArrayList<>();
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet  rsColumns = meta.getColumns(null, null, tableName, null);
        while (rsColumns.next()) {
            columns.add(rsColumns.getString("COLUMN_NAME"));
        }
        return columns;
    }

    @Override
    public void prepareTables() throws SQLException {

        new LogExecutorORM(connection).execBatch("DROP TABLE IF EXISTS ages",
                "DROP SEQUENCE IF EXISTS myorm",
                "CREATE SEQUENCE myorm START 1",
                "CREATE TABLE ages ( id bigint PRIMARY KEY DEFAULT nextval('myorm'),name VARCHAR,age INTEGER )");

    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed");
    }


}
