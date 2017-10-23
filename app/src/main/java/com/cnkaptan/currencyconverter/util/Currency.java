package com.cnkaptan.currencyconverter.util;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public enum Currency {
    EUR("EUR"),USD("USD"),JPY("JPY");
    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static String[] toStringArray(){
        final String[] valueArray = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            valueArray[i] = values()[i].getValue();
        }
        return valueArray;
    }

    public static Currency fromValue(String value){
        for(Currency v : values()){
            if( v.getValue().equals(value)){
                return v;
            }
        }
        return null;
    }
}
