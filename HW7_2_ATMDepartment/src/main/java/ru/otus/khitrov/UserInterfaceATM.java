package ru.otus.khitrov;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class UserInterfaceATM {

    private final DeviceATM boxDeviceATM;
    private static final String ATM_COMMANDS = "QBWRH";


    public UserInterfaceATM(DeviceATM deviceATM) {
        this.boxDeviceATM = deviceATM;
    }

    public void startUI() {

        System.out.println("ATM is ready to run...type H for help");
        while (true) {

            System.out.print(">");
            String strInput = new Scanner(System.in).next();
            if (strInput.length() != 1 ||
                    !StringUtils.containsAny(strInput.toUpperCase(), ATM_COMMANDS)) {
                System.out.println("Unknown command");
                continue;
            }

            switch (Character.toUpperCase(strInput.charAt(0))) {
                case 'Q':
                    System.out.println("Quit");
                    return;
                case 'B':
                    this.getBalanceView();
                    continue;
                case 'W':
                    this.withdrawalView();
                    continue;
                case 'R':
                    this.receiveView();
                    continue;
                case 'H':
                    UserInterfaceATM.showHelp();
            }

        }

    }

    private void receiveView() {

        System.out.print("Nominal: ");
        int nom = new Scanner(System.in).nextInt();

        System.out.print("Number of notes: ");
        int notesNum = new Scanner(System.in).nextInt();

        try {
            boxDeviceATM.receive(nom, notesNum);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private void withdrawalView() {
        System.out.print("Amount: ");
        int money = new Scanner(System.in).nextInt();

        try {
            boxDeviceATM.withdrawal(money);
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    private void getBalanceView() {
        System.out.println("Balance: " + boxDeviceATM.onShowBalance());
    }


    public static void showHelp() {
        System.out.println("B - balance");
        System.out.println("R - receive");
        System.out.println("W - withdrawal");
        System.out.println("Q - quit");
        System.out.println("H - help");
    }


}
