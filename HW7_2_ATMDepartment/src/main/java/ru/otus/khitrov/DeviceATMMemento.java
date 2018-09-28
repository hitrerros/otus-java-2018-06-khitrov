package ru.otus.khitrov;

public class DeviceATMMemento {

    private final DeviceATMState state;

    public DeviceATMMemento( DeviceATMState state ){
        this.state = state;
    }

    public DeviceATMState getSavedState() {
        return state;
    }
}
