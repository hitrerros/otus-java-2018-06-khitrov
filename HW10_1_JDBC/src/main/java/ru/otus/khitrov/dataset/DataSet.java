package ru.otus.khitrov.dataset;

public abstract  class DataSet  {
    protected long id;

    DataSet( long id){
        this.id = id;
    }
    DataSet() { this.id = 0L; }

    public long getId() {
        return id;
    }

    public abstract String getStorageTable();

}
