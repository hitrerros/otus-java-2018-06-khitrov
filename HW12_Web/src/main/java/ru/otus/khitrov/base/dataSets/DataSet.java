package ru.otus.khitrov.base.dataSets;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract  class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    DataSet( long id){
        this.id = id;
    }
    DataSet() { this.id = 0L; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
