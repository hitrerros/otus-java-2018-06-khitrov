package ru.otus.khitrov.base.dataSets;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserDataSet user;

    @Column(name = "number")
    private String number;

    // Hibernate
    public PhoneDataSet() {}

    public PhoneDataSet(String number) {
        this.number = number;
      }

    public PhoneDataSet(UserDataSet user,String number)
    {
        this.number = number;
        this.user   = user;
    }



    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return  number;
    }
}
