package ru.otus.khitrov.base.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.khitrov.base.dataSets.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

public class UserDaoHib {

    private final Session session;

    public UserDaoHib(Session session) {
        this.session = session;
    }

    public  long save(UserDataSet dataSet) {
        return (long) session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.get(UserDataSet.class, id);
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public long getCount() {
        Query query = session.createSQLQuery(
                "select count(*) from user_list");

       return ((BigInteger) query.getSingleResult()).longValue();

    }

}
