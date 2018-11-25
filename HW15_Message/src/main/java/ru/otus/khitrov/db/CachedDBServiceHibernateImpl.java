package ru.otus.khitrov.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.khitrov.db.dao.UserDaoHib;
import ru.otus.khitrov.db.dataSets.AddressDataSet;
import ru.otus.khitrov.db.dataSets.DataSet;
import ru.otus.khitrov.db.dataSets.PhoneDataSet;
import ru.otus.khitrov.db.dataSets.UserDataSet;
import ru.otus.khitrov.cache.CacheHelper;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.MessageSystem;
import ru.otus.khitrov.messageSystem.MessageSystemContext;


import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class CachedDBServiceHibernateImpl implements CachedDBService {

    private final static Logger log = Logger.getLogger(CachedDBServiceHibernateImpl.class.getName());
    private final SessionFactory sessionFactory;

    private Address address;
    private MessageSystemContext context;
    @Autowired
    private  CacheHelper cache;

    public CachedDBServiceHibernateImpl(  MessageSystemContext context ){

        Configuration configuration = new Configuration()
                .addAnnotatedClass(UserDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .addAnnotatedClass(AddressDataSet.class)
                .configure(); // "hibernate.cfg.xml" by default

        sessionFactory = createSessionFactory(configuration);
        this.context = context;
        registerInMessageSystem();
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
            cache.setValue(dataSet);
            return  dao.save((UserDataSet) dataSet);
        });
    }


    public <T extends DataSet> T read(long id) {
        return runInSession(session -> {
            UserDaoHib dao = new UserDaoHib(session);

            T dataSet = (T) cache.getValue(id);

            if (dataSet == null )
                return (T) dao.read(id);
             else
                 log.info("read from cache");
                 return   dataSet ;
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return context.getMessageSystem();
    }

    @Override
    public void registerInMessageSystem() {
        address = context.getDbAddress();
        context.getMessageSystem().addAddressee(address,this);
    }
}
