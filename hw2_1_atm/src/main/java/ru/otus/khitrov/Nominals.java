package ru.otus.khitrov;

import org.apache.commons.lang3.StringUtils;

public enum Nominals {
    N_5000,
    N_2000,
    N_1000,
    N_500,
    N_200,
    N_100;

    public static Nominals integerToNominal(Integer currNominal ){
        try {
            return Nominals.valueOf(String.join("","N_", String.valueOf(currNominal)));
        } catch (IllegalArgumentException c ) {
            System.out.println("No such nominal");
            return null;
        }
    }

    public static Integer nominalToInteger( Nominals nominals ) {
        return Integer.valueOf(StringUtils.substring(nominals.toString(),2));
    }

}
