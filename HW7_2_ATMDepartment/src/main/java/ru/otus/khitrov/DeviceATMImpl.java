package ru.otus.khitrov;

import java.util.*;

public class DeviceATMImpl implements  DeviceATM  {

    protected final String deviceID;
    private Map<Nominals,Cell> storage;
    private DeviceATMStorageOriginator originator;

    private DeviceATMImpl(String deviceID, ConfigurationATM conf ) {
        this.deviceID = deviceID;
        originator = new DeviceATMStorageOriginator();
        storage = Arrays.stream(Nominals.values())
                .filter( x -> Nominals.isNewBanknote(  x, conf )  )
                .collect(LinkedHashMap::new,( x,y ) -> x.put( y, new Cell( y )) ,Map::putAll );

    }

    public static DeviceATM createFactory( String deviceID, ConfigurationATM conf )
    {
        return new DeviceATMImpl( deviceID, conf );
    }


    @Override
    public void encashement(Map<Nominals, Integer> notes) {

        for ( Map.Entry<Nominals,Integer> note : notes.entrySet() ) {
            storage.get( note.getKey() ).addNotes( note.getValue() );
        }

       originator.saveState(  new DeviceATMState( storage )  );
    }

    @Override
    public void receive(int nom, int numOfNotes) throws IllegalArgumentException  {

        Cell cellEntry;
        Nominals nominal  = Nominals.getNominalValue( nom );

        if ( nominal  == null ) throw new IllegalArgumentException( "Wrong nominal");
        if (  numOfNotes < 0 )  throw new IllegalArgumentException( "Invalid number of banknotes");

        if ((cellEntry = storage.get( nominal )) == null)
            throw new  IllegalArgumentException( "This nominal is not supported");

        storage.get( nominal ).addNotes( numOfNotes );
        originator.saveState(  new DeviceATMState( storage )  );
    }

    @Override
    public void withdrawal(int amount) throws IllegalArgumentException {

        int amountToExtract = amount;
        Map <Cell,Integer> notesCache = new HashMap<>();

        for (Map.Entry<Nominals, Cell> entry : storage.entrySet()) {

            Cell cell = entry.getValue();
            int numOfNotesFromCell = cell.getNotesToCoverAmount( amountToExtract );
            if (numOfNotesFromCell > 0){
                amountToExtract -= numOfNotesFromCell * cell.getNominal();
                notesCache.put(cell,numOfNotesFromCell);
            }
      }

        if (amountToExtract == 0){
            for (Map.Entry<Cell,Integer> entry : notesCache.entrySet()) {
                entry.getKey().withdrawNotes(entry.getValue());
            }
        }
        else {
            throw new IllegalArgumentException("Banknotes are insufficient to withdraw this amout");
        }

        originator.saveState(  new DeviceATMState( storage )  );
    }




    @Override
    public int onShowBalance() {
        int balance = 0;
        for (Map.Entry<Nominals, Cell> entry : storage.entrySet()){
            balance += entry.getValue().getAmount();
        }
        return balance;
    }

    @Override
    public void onUndoState() {
          storage = originator.restoreInitialState()
                              .getSavedState()
                              .getState();
    }
}
