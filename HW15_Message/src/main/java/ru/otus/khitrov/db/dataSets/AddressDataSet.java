package ru.otus.khitrov.db.dataSets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    // Hibernate
    public AddressDataSet() {}

    public AddressDataSet(  String street ) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return street;
    }
}
