package ru.otus.khitrov;

class Cell {

    private final int nominal;
    private int numberOfNotes = 0;
    private int amount = 0;

    Cell(  int nominal ){
        this.nominal = nominal;
    }

    public void addNotes( int numberOfNotes  ) {
        amount += numberOfNotes * nominal;
        this.numberOfNotes += numberOfNotes;
    }

    public void withdrawNotes( int numberOfNotes ){
        amount -= numberOfNotes * nominal;
        this.numberOfNotes -= numberOfNotes;
        System.out.println("Nominal: " + nominal + " number of banknotes: " + numberOfNotes );
    }

    public int getAmount(){
        return amount;
    }

    public int getNominal(){
        return nominal;
    }

    public int getNotesToCoverAmount( int amount ) {

        int i = amount / nominal;
        if (i > 0) {
           return  ( i > numberOfNotes ? numberOfNotes : i );
        }

        return 0;
    }



}
