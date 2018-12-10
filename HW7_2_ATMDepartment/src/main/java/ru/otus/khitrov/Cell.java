package ru.otus.khitrov;

public class Cell {

    private final Nominals nominal;
    private int numberOfNotes = 0;
    private int amount = 0;

    Cell(Nominals nominal) {
        this.nominal = nominal;
    }

    Cell(Nominals nominal, int numberOfNotes, int amount) {
        this.nominal = nominal;
        this.numberOfNotes = numberOfNotes;
        this.amount = amount;
    }

    public void addNotes(int numberOfNotes) {
        amount += numberOfNotes * nominal.getIntValue();
        this.numberOfNotes += numberOfNotes;
    }

    public void withdrawNotes(int numberOfNotes) {
        amount -= numberOfNotes * nominal.getIntValue();
        this.numberOfNotes -= numberOfNotes;
        System.out.println("Nominal: " + nominal + " number of banknotes: " + numberOfNotes);
    }

    public int getAmount() {
        return amount;
    }

    public int getNominal() {
        return nominal.getIntValue();
    }

    public int getNotesToCoverAmount(int amount) {
        int i = amount / nominal.getIntValue();
        if (i > 0) {
            return (i > numberOfNotes ? numberOfNotes : i);
        }
        return 0;
    }

    public static Cell cloneCell(Cell cell) {
        return new Cell(cell.nominal, cell.numberOfNotes, cell.amount);
    }


}
