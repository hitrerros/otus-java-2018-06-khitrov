package ru.otus.khitrov.myorm;

import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.dataset.DataSet;
import ru.otus.khitrov.reflection.ReflectionHelper;

import java.lang.reflect.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class ORMExecutor {

    private final DBService dbService;
    private static final String INSERT_QUERY = "insert into %s (%s ) values (%s)";
    private static final String SELECT_QUERY = "select * from %s where id = %s ";
    private static final String STORAGE_FIELD  = "STORAGE_TABLE";

    public ORMExecutor(DBService dbService ) {
        this.dbService = dbService;
    }

    private <T extends DataSet> String prepareStringForInsert( T user ) throws Exception {

        StringBuilder lQueryString = new StringBuilder( );
        StringBuilder rQueryString = new StringBuilder( );

        String storageTable = user.getStorageTable();
        List<String> columns = dbService.getDBMetadata( storageTable );
        Class clazz = user.getClass();

        for (Field field: clazz.getDeclaredFields()) {
            String fieldName = field.getName();

            if (STORAGE_FIELD.equals(fieldName)) continue;

            if (!columns.contains( fieldName )){
                throw new IllegalArgumentException("Inconsistence found between object and table");
            }
            else{
                if ("id".equals( fieldName )) continue;
                lQueryString.append(",").append(  field.getName() );
                rQueryString.append(",").append(  wrapIfString(ReflectionHelper.getFieldValue( user, fieldName )) );
            }

        }

        if (!"".equals(lQueryString.toString())
                && !"".equals(rQueryString.toString()) ) {
            lQueryString.deleteCharAt(0);
            rQueryString.deleteCharAt(0);
            return String.format(INSERT_QUERY, storageTable, lQueryString, rQueryString);
        }

        return null;
    }


    public <T extends DataSet>  long save(T user) throws Exception {
        LogExecutorORM exec = new LogExecutorORM(dbService.getConnection());
        String strQuery;

        if ( (strQuery = prepareStringForInsert(user)) == null ){
           throw new IllegalArgumentException("error while parsing table");
        }

        Objects.requireNonNull(strQuery);

        return exec.execUpdate(strQuery, result -> {
            result.next();
            return result.getLong(1);
        } );

    }




    public<T extends DataSet> T load( long id, Class <T> clazz) throws Exception {

        TExecutor exec = new TExecutor(dbService.getConnection());
        T newObject = ReflectionHelper.instantiate(clazz);
        String tableName = newObject.getStorageTable();
        List<String> columns = dbService.getDBMetadata(tableName);

        return exec.execQuery(String.format(SELECT_QUERY, tableName, id),
                result -> {
                   if (result.next()) {
                       for (String column : columns ) {
                           Object value = result.getString(column);
                           setObjectValue(newObject,column,result);
                       }
                    }
                    return newObject;
                });

    }

    private String wrapIfString(Object obj) {
        return  String.class.isAssignableFrom( obj.getClass()) ?
                "\'" + obj.toString() + "\'" :
                obj.toString();
    }

    private <T extends DataSet>void setObjectValue(T newOb, String column, ResultSet result ) {

        Class clazz = newOb.getClass();

        try {

            if ("id".equals(column)) {
               Field fieldId =  clazz.getSuperclass().getDeclaredField("id");
               fieldId.setAccessible(true);
               fieldId.set(newOb,result.getLong("id"));
            }
            else {
                Class<?> type = clazz.getDeclaredField(column).getType();

                if ( String.class.isAssignableFrom( type ))
                      ReflectionHelper.setFieldValue(newOb, column, result.getString(column));
                else
                    ReflectionHelper.setFieldValue(newOb, column, result.getInt(column));
                 }
         }

        catch (NoSuchFieldException  | SQLException | IllegalAccessException  e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

}
