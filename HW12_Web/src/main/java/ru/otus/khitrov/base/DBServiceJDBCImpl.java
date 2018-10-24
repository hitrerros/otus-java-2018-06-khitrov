package ru.otus.khitrov.base;

import ru.otus.khitrov.base.dao.UserDaoJDBC;
import ru.otus.khitrov.base.dataSets.DataSet;
import ru.otus.khitrov.base.dataSets.UserDataSet;

import java.sql.Connection;
import java.util.List;

public class DBServiceJDBCImpl implements DBService {

    private final Connection connection;
    public DBServiceJDBCImpl() {
        connection = ConnectionHelper.getConnection();
    }

    public <T extends DataSet> long save(T dataSet) throws Exception {

        UserDaoJDBC dao = new UserDaoJDBC( connection );
        long resId = dao.save ((UserDataSet) dataSet);
        if (resId != 0 )
        {
            connection.commit();
            return resId;
        }

        return 0;
    }

    public <T extends DataSet> T read(long id) throws Exception {
        UserDaoJDBC dao = new UserDaoJDBC( connection );
        return (T) dao.read(id);
    }

    public <T extends DataSet> T readByName(String name) {
        return null;
    }

    public <T extends DataSet>  List<T> readAll() throws Exception {
        UserDaoJDBC dao = new UserDaoJDBC( connection );
        return (List<T>) dao.readAll();
    }

    @Override
    public <T extends DataSet> long getCount() throws Exception {
        UserDaoJDBC dao = new UserDaoJDBC( connection );
        return dao.getCount();
    }

    public void shutdown() throws Exception {
        connection.close();
    }
}
