package ru.otus.khitrov.main;

import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.base.DBServiceHibernateImpl;
import ru.otus.khitrov.base.DBServiceJDBCImpl;
import ru.otus.khitrov.base.dataSets.AddressDataSet;
import ru.otus.khitrov.base.dataSets.DataSet;
import ru.otus.khitrov.base.dataSets.PhoneDataSet;
import ru.otus.khitrov.base.dataSets.UserDataSet;


import java.util.List;
import java.util.stream.Collectors;

public class Main {

 public static void main(String...args) throws Exception {

     DBService dbServiceHib = new DBServiceHibernateImpl();

     System.out.println("Update and read with Hibernate");
     long firstId = dbServiceHib.save( new UserDataSet("Vasechkin",
                                        25,
                                         new AddressDataSet("Red square 1"),
                                         List.of(new PhoneDataSet("123"),
                                                 new PhoneDataSet("234"))));

     long secondId = dbServiceHib.save( new UserDataSet("Petr22off",
             25,
             new AddressDataSet("Saulsberry square 3"),
             List.of(new PhoneDataSet("01"),
                     new PhoneDataSet("02"))));


     DataSet dataSet1 = dbServiceHib.read( firstId );
     System.out.println( dataSet1 );

     DataSet dataSet2 = dbServiceHib.read( secondId );
     System.out.println( dataSet2 );

     DataSet dataSet3 = dbServiceHib.readByName( "Vasechkin" );
     System.out.println( dataSet3 );

     List<DataSet> fullList = dbServiceHib.readAll();
     String result =  fullList.stream().map(Object::toString).collect(Collectors.joining("\n"));
     System.out.println( result );

     dbServiceHib.shutdown();

     System.out.println("Update and read with JDBC");
     DBService dbServiceJDBC = new DBServiceJDBCImpl();

     long thirdId = dbServiceJDBC.save( new UserDataSet("XSdddSS",
             25,
             new AddressDataSet("Red square 1"),
             List.of(new PhoneDataSet("123"))));


     System.out.println( dbServiceJDBC.read( firstId ).toString() );
     System.out.println( dbServiceJDBC.read( thirdId ).toString() );

     System.out.println("Read all with  JDBC");
     List<DataSet> fullListJDBC = dbServiceJDBC.readAll();
     String resultJDBC =  fullListJDBC.stream().map(Object::toString).collect(Collectors.joining("\n"));
     System.out.println( resultJDBC );


     dbServiceJDBC.shutdown();
 }


}
