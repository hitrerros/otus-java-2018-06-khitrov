package ru.otus.khitrov;

public enum Nominals {

    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    THOUSAND(1000),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    HUNDRED(100);

    private final int nominal;

    Nominals(int nominal) {
        this.nominal = nominal;
    }

    public int getIntValue() {
        return nominal;
    }

    public static boolean isNewBanknote(Nominals nominal, ConfigurationATM conf) {
        return (conf == ConfigurationATM.NEW_BANKNOTES) ||
                !(nominal == Nominals.TWO_HUNDRED || nominal == Nominals.TWO_THOUSAND);
    }

    public static Nominals getNominalValue(int nominal) {
        for (Nominals type : values()) {
            if (type.getIntValue() == nominal) {
                return type;
            }
        }
        return null;

    }

}
