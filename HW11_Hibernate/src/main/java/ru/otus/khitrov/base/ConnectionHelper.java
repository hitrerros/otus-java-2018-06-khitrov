package ru.otus.khitrov.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    static Connection getConnection() {
        try {
            Driver driver = (Driver) Class.forName("org.postgresql.Driver").getConstructor().newInstance();
            DriverManager.registerDriver(driver);

            String url = "jdbc:postgresql://" +  //db type
                    "localhost:" +               //host name
                    "5432/" +                    //port
                    "otus?" +                    //db name
                    "user=postgres&" +           //login
                    "password=1";

            return DriverManager.getConnection(url);
        } catch (SQLException |
                InstantiationException |
                InvocationTargetException |
                NoSuchMethodException |
                IllegalAccessException |
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
