package ru.otus.khitrov;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class DeviceATM {

    private Map<Nominals, Cell> storage = new LinkedHashMap<>();

    DeviceATM() {
        for (Nominals currNominal : Nominals.values()) {
            storage.put(currNominal, new Cell(Nominals.nominalToInteger(currNominal)));
        }
    }

    public int getBalance() {
        int balance = 0;
        for (Map.Entry<Nominals, Cell> entry : storage.entrySet()) {
            balance += entry.getValue().getAmount();
        }
        return balance;
    }

    public void receive(int nominal, int numOfNotes) {
        if (numOfNotes < 1) {
            System.out.println("Invalid number of notes");
            return;
        }

        Nominals nom = Nominals.integerToNominal(nominal);

        if (nom != null) {
            storage.get(nom).addNotes(numOfNotes);
        }
    }

    public void withdrawal(int amount) {

        int amountToExtract = amount;
        Map<Cell, Integer> notesCache = new HashMap<>();

        for (Map.Entry<Nominals, Cell> entry : storage.entrySet()) {

            Cell cell = entry.getValue();
            int numOfNotesFromCell = cell.getNotesToCoverAmount(amountToExtract);
            if (numOfNotesFromCell > 0) {
                amountToExtract -= numOfNotesFromCell * cell.getNominal();
                notesCache.put(cell, numOfNotesFromCell);
            }

        }

        if (amountToExtract == 0) {
            for (Map.Entry<Cell, Integer> entry : notesCache.entrySet()) {
                entry.getKey().withdrawNotes(entry.getValue());
            }
        } else {
            System.out.println("Banknotes are insufficient to withdraw this amout");
        }
    }

}


