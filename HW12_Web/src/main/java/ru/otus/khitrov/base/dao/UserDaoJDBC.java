package ru.otus.khitrov.base.dao;

import ru.otus.khitrov.base.dataSets.AddressDataSet;
import ru.otus.khitrov.base.dataSets.DataSet;
import ru.otus.khitrov.base.dataSets.UserDataSet;
import ru.otus.khitrov.executor.PreparedExecutor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC {

    private static final String INSERT_USER       = "insert into user_list (age,name,address_id) values (?,?,?)";
    private static final String SELECT_USER       = "select u.age as age, u.name as name , a.street as address  from user_list as u left join address as a\n" +
            "   on u.address_id = a.id where u.id = ?";
    private static final String SELECT_ALL_USERS  = "select u.id as id, u.age as age, u.name as name , a.street as address  from user_list as u left join address as a\n" +
            "   on u.address_id = a.id;";
    private static final String SELECT_COUNT       = "select count(*) from user_list";

    private final Connection connection;


    public  UserDaoJDBC(Connection connection ) throws  Exception {
        this.connection = connection;
        this.connection.setAutoCommit(false);
    }

    public long save(UserDataSet dataSet) throws  Exception {

        AddressDaoJDBC daoAddress = new AddressDaoJDBC(connection);
        long addressId = daoAddress.save( dataSet.getAddress() );

        PreparedExecutor exec = new PreparedExecutor(connection);

        return exec.execUpdate( INSERT_USER, statement->
                {
                    statement.setInt( 1,    dataSet.getAge() );
                    statement.setString( 2, dataSet.getName() );
                    statement.setLong( 3,   addressId );
                },
                result ->
                {   result.next();
                    return result.getLong(1);
                });


    }

    public UserDataSet read(long id) throws Exception {
        PreparedExecutor exec = new PreparedExecutor(connection);

        return exec.execQuery( SELECT_USER,
                statement ->  statement.setLong(1,id),
                result -> {
                    if (result.next()) {
                        // (String name, int age, AddressDataSet address, Phones are not supported in JDBC
                        return  new UserDataSet(
                                                id,
                                                result.getString("name"),
                                                result.getInt("age"),
                                                new AddressDataSet( result.getString( "address") )
                                                         );
                    }
                    return null;
                }
                );
    }

    public List<DataSet> readAll() throws Exception {

        PreparedExecutor exec = new PreparedExecutor(connection);

        return exec.execQuery( SELECT_ALL_USERS,
                result -> {
                    List<DataSet> resultList = new ArrayList<>();
                    while (result.next()) {
                        // (String name, int age, AddressDataSet address, Phones are not supported in JDBC
                            resultList.add (  new UserDataSet(    result.getLong("id"),
                                            result.getString("name"),
                                            result.getInt("age"),
                                            new AddressDataSet( result.getString( "address")) )
                        );
                    }
                    return resultList;
                }
        );

    }

   public long getCount() throws Exception {
       PreparedExecutor exec = new PreparedExecutor(connection);

       return exec.execQuery( SELECT_COUNT,
               result -> {
                         result.next();
                         return result.getLong(1);
               }
       );


   }

}