package ru.otus.khitrov.base.dao;

import ru.otus.khitrov.base.dataSets.AddressDataSet;
import ru.otus.khitrov.executor.PreparedExecutor;

import java.sql.Connection;

public class AddressDaoJDBC {

    private static final String INSERT_ADDRESS    = "insert into address (street) values (?)";
    private final Connection connection;

    public AddressDaoJDBC( Connection connection ) {
        this.connection = connection;
    }

    public long save(AddressDataSet dataSet) throws  Exception {

        PreparedExecutor exec = new PreparedExecutor(connection);

        return  exec.execUpdate( INSERT_ADDRESS, statement-> statement.setString( 1, dataSet.getStreet() ),
                result ->
                { result.next();
                    return result.getLong(1);
                });
    }
}
