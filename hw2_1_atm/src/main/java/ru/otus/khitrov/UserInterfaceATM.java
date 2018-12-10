package ru.otus.khitrov;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;


public class UserInterfaceATM {

    private DeviceATM boxDeviceATM = new DeviceATM();
    private static final String ATM_COMMANDS = "QBWRH";

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

        boxDeviceATM.receive(nom, notesNum);

    }

    private void withdrawalView() {
        System.out.print("Amount: ");
        int money = new Scanner(System.in).nextInt();

        boxDeviceATM.withdrawal(money);

    }

    private void getBalanceView() {
        System.out.println("Balance: " + boxDeviceATM.getBalance());
    }

    public static void main(String... args) {
        new UserInterfaceATM().startUI();
    }

    public static void showHelp() {
        System.out.println("B - balance");
        System.out.println("R - receive");
        System.out.println("W - withdrawal");
        System.out.println("Q - quit");
        System.out.println("H - help");

    }
}
