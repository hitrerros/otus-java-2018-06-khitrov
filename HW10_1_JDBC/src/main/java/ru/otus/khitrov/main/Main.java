package ru.otus.khitrov.main;

import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.base.DBServiceConnection;
import ru.otus.khitrov.dataset.DataSet;
import ru.otus.khitrov.dataset.UserDataSet;
import ru.otus.khitrov.myorm.ORMExecutor;

class Main {

  public static void main(String... args) throws Exception{
      new Main().run();
  }

    private void run() throws Exception {

        try (DBService dbService =  new DBServiceConnection()) {
             dbService.prepareTables();

             ORMExecutor orm = new ORMExecutor( dbService );

             long userId1 = orm.save(new UserDataSet("Jhonson",14));

             System.out.println("new id " + userId1 );
             long userId2 = orm.save(new UserDataSet("Petroff",34));
             System.out.println("new id " + userId2 );

             DataSet readUser1 = orm.load( userId1, UserDataSet.class );
             System.out.println(readUser1.toString());

             DataSet readUser2 = orm.load( userId2, UserDataSet.class );
             System.out.println(readUser2.toString());


        } catch (IllegalArgumentException e) {
           System.out.println(e.getMessage());
        }
    }

}
