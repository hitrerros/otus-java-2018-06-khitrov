package ru.otus.khitrov;

import java.util.*;
import java.util.function.Consumer;


public class DepartmentATM {

    EventATMProducer eventATMProducer = new EventATMProducer();
    Map<String,DeviceATM> devices = new HashMap<>( );

     public static void main(String...args){
        new DepartmentATM().launchATMDepartment();
     }

     public void doUICommand  (Consumer<String> cmd){

         while (true) {
             System.out.print("Enter ID (Q - for quit): ");
             String deviceID = new Scanner(System.in).next();

             if ( deviceID.length() > 0) {
                 if ("Q".equals(deviceID)) {
                     return;
                 }
             }

             try {
                 cmd.accept(deviceID);
             } catch ( IllegalArgumentException e ) {
                    System.out.println( e.getMessage() );
             }
         }
     }

     private void launchATMDepartment() {
         init_devices( );
         encash_devices( );
         show_balance( );
         run_atm( );
         undo_state( );
         show_balance( );
   }

    private void run_atm() {
        System.out.println("run ATM ");

        doUICommand((deviceID)-> {
            DeviceATM device = devices.get(deviceID);
            if (device == null)
                throw new IllegalArgumentException("Device have not been initialized");
            else {
                new UserInterfaceATM( device ).startUI();
            }
    });
    }

    private void show_balance() {
         eventATMProducer.balanceEvent( );
    }

    private void init_devices() {
         System.out.println("Initialization");
            doUICommand((deviceID)->{
                System.out.print("Choose configuration (E - with 2000/200 banknotes, O - ordinary) ");
                String configParam = new Scanner(System.in).next();

                if (configParam.charAt(0) == 'E') {
                    devices.put( deviceID, DeviceATMImpl.createFactory( deviceID, ConfigurationATM.NEW_BANKNOTES ));
                }
                else if (configParam.charAt(0) == 'O')  {
                    devices.put( deviceID, DeviceATMImpl.createFactory( deviceID, ConfigurationATM.OLD_BANKNOTES ));
                }
            });
     }

     private void encash_devices() {
         System.out.println("Encashement");

         doUICommand((deviceID)-> {
             DeviceATM device = devices.get(deviceID);
             if (device == null)
               throw new IllegalArgumentException("Device have not been initialized");
             else {
                 Map<Nominals,Integer> initNominals = new HashMap<>();

                 while (true){
                    System.out.print("(Nominal,Quantity) (S - save, C - cancel)");
                     String tokens = new Scanner(System.in).next();

                     if (tokens.charAt(0)=='C'){
                         return;
                     }

                     if (tokens.charAt(0)=='S'){
                         if (initNominals.size() > 0){
                             device.encashement( initNominals );
                             eventATMProducer.addListener( device );
                         };
                         return;
                     }

                     String[] tok = tokens.split(",");
                     if (tok.length == 2){
                         Nominals reqNom = Nominals.getNominalValue( Integer.valueOf( tok[0] ) );

                         if (reqNom == null) throw new IllegalArgumentException("Invalid banknote nominal");
                         initNominals.put( reqNom, Integer.valueOf( tok[1] ) );
                     }

                 }

             }

         });

}

     private void undo_state(){
         System.out.println("Recover initial state");
         eventATMProducer.undoEvent();
     }

}
