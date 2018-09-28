package ru.otus.khitrov;

import java.util.List;
import java.util.Map;

public interface DeviceATM  {

public void encashement ( Map<Nominals,Integer> notes);
public void receive(int nom, int numOfNotes );
public void withdrawal(int amount);


public int  onShowBalance( );
public void onUndoState();


}
