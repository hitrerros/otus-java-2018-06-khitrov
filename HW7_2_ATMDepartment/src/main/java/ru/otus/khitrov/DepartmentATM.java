package ru.otus.khitrov;

import java.util.*;
import java.util.function.Consumer;


public class DepartmentATM {

    EventATMProducer eventATMProducer = new EventATMProducer();
    Map<String, DeviceATM> devices = new HashMap<>();

    public static void main(String... args) {
        new DepartmentATM().launchATMDepartment();
    }

    public void doUICommand(Consumer<String> cmd) {

        while (true) {
            System.out.print("Enter ID (Q - to quit this step): ");
            String deviceID = new Scanner(System.in).next();

            if (deviceID.length() > 0) {
                if ("Q".equals(deviceID)) {
                    return;
                }
            }

            try {
                cmd.accept(deviceID);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void launchATMDepartment() {
        initDevices();
        encashDevices();
        showBalance();
        runATM();
        undoState();
        showBalance();
    }

    private void runATM() {
        System.out.println("run ATM ");

        doUICommand((deviceID) -> {
            DeviceATM device = devices.get(deviceID);
            if (device == null)
                throw new IllegalArgumentException("Device have not been initialized");
            else {
                new UserInterfaceATM(device).startUI();
            }
        });
    }

    private void showBalance() {
        eventATMProducer.balanceEvent();
    }

    private void initDevices() {
        System.out.println("Initialization");
        doUICommand((deviceID) -> {
            System.out.print("Choose configuration (E - with 2000/200 banknotes, O - ordinary) ");
            String configParam = new Scanner(System.in).next();

            if (configParam.charAt(0) == 'E') {
                devices.put(deviceID, DeviceATMImpl.createFactory(deviceID, ConfigurationATM.NEW_BANKNOTES));
            } else if (configParam.charAt(0) == 'O') {
                devices.put(deviceID, DeviceATMImpl.createFactory(deviceID, ConfigurationATM.OLD_BANKNOTES));
            }
        });
    }

    private void encashDevices() {
        System.out.println("Encashement");

        doUICommand((deviceID) -> {
            DeviceATM device = devices.get(deviceID);
            if (device == null)
                throw new IllegalArgumentException("Device have not been initialized");
            else {
                Map<Nominals, Integer> initNominals = new HashMap<>();

                while (true) {
                    System.out.print("(Nominal,Quantity) (S - save, C - cancel)");
                    String tokens = new Scanner(System.in).next();

                    if (tokens.charAt(0) == 'C') {
                        return;
                    }

                    if (tokens.charAt(0) == 'S') {
                        if (initNominals.size() > 0) {
                            device.encashement(initNominals);
                            eventATMProducer.addListener(device);
                        }
                        ;
                        return;
                    }

                    String[] tok = tokens.split(",");
                    if (tok.length == 2) {
                        Nominals reqNom = Nominals.getNominalValue(Integer.valueOf(tok[0]));

                        if (reqNom == null) throw new IllegalArgumentException("Invalid banknote nominal");
                        initNominals.put(reqNom, Integer.valueOf(tok[1]));
                    }

                }

            }

        });

    }

    private void undoState() {
        System.out.println("Recover initial state");
        eventATMProducer.undoEvent();
    }

}
