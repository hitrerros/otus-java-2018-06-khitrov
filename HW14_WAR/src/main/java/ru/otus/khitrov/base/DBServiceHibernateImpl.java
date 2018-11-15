package ru.otus.khitrov.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.otus.khitrov.base.dao.UserDaoHib;
import ru.otus.khitrov.base.dataSets.AddressDataSet;
import ru.otus.khitrov.base.dataSets.DataSet;
import ru.otus.khitrov.base.dataSets.PhoneDataSet;
import ru.otus.khitrov.base.dataSets.UserDataSet;

import org.springframework.cache.annotation.Cacheable;
import java.io.File;
import java.util.List;
import java.util.function.Function;

@Service
public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;

    @Autowired
    public DBServiceHibernateImpl(){

        Configuration configuration = new Configuration()
                .addAnnotatedClass(UserDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .addAnnotatedClass(AddressDataSet.class);
  //              .configure(new File("hibernate.cfg.xml"));
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost/otus:5432");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "1");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }


    public <T extends DataSet>  long save(T dataSet) {
        return runInSession(session -> {
            UserDaoHib dao = new UserDaoHib(session);
            return  dao.save((UserDataSet) dataSet);
        });
    }


    public <T extends DataSet> T read(long id) {
        return runInSession(session -> {
            UserDaoHib dao = new UserDaoHib(session);
            return (T) dao.read(id);
        });
    }

    public <T extends DataSet> T readByName(String name) {
        return null;
    }


    public <T extends DataSet> List<T> readAll() {

        return runInSession(session -> {
            UserDaoHib dao = new UserDaoHib(session);
            return (List<T>) dao.readAll();
        });
    }

    @Override
    public <T extends DataSet> long getCount() throws Exception {

        return runInSession(session -> {
            UserDaoHib dao = new UserDaoHib(session);
            return dao.getCount();
        });

    }


    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
