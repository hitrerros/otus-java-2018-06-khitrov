package ru.otus.khitrov.db;

import ru.otus.khitrov.db.dataSets.DataSet;

import java.util.List;

public interface DBService {

    <T extends DataSet> long save(T dataSet) throws Exception;

    <T extends DataSet> T read(long id) throws Exception;

    <T extends DataSet> T readByName(String name);

    <T extends DataSet> List<T> readAll() throws Exception;

    <T extends DataSet> long getCount() throws Exception;

    void shutdown() throws Exception;

}
