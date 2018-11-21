package ru.otus.khitrov.base;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.khitrov.base.dataSets.DataSet;
import ru.otus.khitrov.cache.CacheHelper;

import java.util.List;

public interface DBService {

    <T extends DataSet> long  save(T dataSet) throws Exception;

    <T extends DataSet> T read(long id) throws Exception;

    <T extends DataSet> T readByName(String name);

    <T extends DataSet> List<T> readAll() throws Exception;

    <T extends DataSet> long getCount()  throws Exception;

    void shutdown() throws Exception;

}
