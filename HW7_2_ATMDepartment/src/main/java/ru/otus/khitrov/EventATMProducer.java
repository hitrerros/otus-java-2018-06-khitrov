package ru.otus.khitrov;

import java.util.ArrayList;
import java.util.List;

public class EventATMProducer {

 public EventATMProducer(){
 }

    private final List<DeviceATM> listeners = new ArrayList<>();

    public void addListener(DeviceATM listener) {
        listeners.add(listener);
    }

    public void removeListener(DeviceATM listener) {
        listeners.remove(listener);
    }

    public void undoEvent( ) {
        listeners.forEach(listener -> listener.onUndoState());
    }

    public void balanceEvent() {

        int total = 0;

        for ( DeviceATM listener : listeners )  total +=   listener.onShowBalance();
        System.out.println("Total balance:" + total );
    }
}
