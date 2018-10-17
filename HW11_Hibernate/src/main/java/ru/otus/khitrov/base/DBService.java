package ru.otus.khitrov.base;

import ru.otus.khitrov.base.dataSets.DataSet;
import ru.otus.khitrov.base.dataSets.UserDataSet;
import java.util.List;

public interface DBService {

    <T extends DataSet> long  save(T dataSet) throws Exception;

    <T extends DataSet> T read(long id) throws Exception;

    <T extends DataSet> T readByName(String name);

    <T extends DataSet> List<T> readAll() throws Exception;

    void shutdown() throws Exception;

}
