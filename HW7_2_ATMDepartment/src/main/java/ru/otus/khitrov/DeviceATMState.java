package ru.otus.khitrov;

import java.util.*;

public class DeviceATMState {

    private final Map<Nominals, Cell> storage = new LinkedHashMap<>();

    DeviceATMState(Map<Nominals, Cell> storage) {

        for (Map.Entry<Nominals, Cell> cell : storage.entrySet()) {
            this.storage.put(cell.getKey(), Cell.cloneCell(cell.getValue()));
        }

    }

    public Map<Nominals, Cell> getState() {
        Map<Nominals, Cell> tempStorage = new LinkedHashMap<>();

        for (Map.Entry<Nominals, Cell> cell : storage.entrySet()) {
            tempStorage.put(cell.getKey(), cell.getValue());
        }

        return tempStorage;
    }

}
