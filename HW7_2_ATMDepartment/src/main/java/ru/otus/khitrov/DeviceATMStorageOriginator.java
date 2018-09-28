package ru.otus.khitrov;

import java.util.ArrayList;
import java.util.List;

public class DeviceATMStorageOriginator {

    private final List<DeviceATMMemento> savedStates = new ArrayList<DeviceATMMemento>();

    public void saveState(DeviceATMState state){
        savedStates.add(new DeviceATMMemento( state ));
    }

    public DeviceATMMemento get(int index){
        return savedStates.get(index);
    }

    public DeviceATMMemento restoreInitialState(){
        return savedStates.get(0);
    }
}
