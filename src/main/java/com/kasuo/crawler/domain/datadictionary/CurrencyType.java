package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.core.DataDictionary;

public class CurrencyType extends DataDictionary<CurrencyType> {
    private static final long serialVersionUID = 1L;
    public static final String CNY = "CNY";
    public static final String USD = "USD";
    public static final String HKD = "HKD";

    public CurrencyType(String typeid) {
        setId(typeid);
    }

    public static final String EUR = "EUR";


    public static final String GBP = "GBP";


    public static final String JPY = "JPY";


    public static final String KRW = "KRW";


    public static final String CAD = "CAD";


    public static final String AUD = "AUD";


    public static final String CHF = "CHF";


    public static final String SGD = "SGD";


    public static final String MYR = "MYR";


    public static final String IDR = "IDR";


    public static final String VND = "VND";


    public static final String THB = "THB";


    public static final String PHP = "PHP";


    public static final String UNCERTAINTY = "n";


    public boolean isCNY() {
        return isType("CNY");
    }

    public boolean isUSD() {
        return isType("USD");
    }

    public boolean isHKD() {
        return isType("HKD");
    }

    public boolean isEUR() {
        return isType("EUR");
    }

    public boolean isGBP() {
        return isType("GBP");
    }

    public boolean isJPY() {
        return isType("JPY");
    }

    public boolean isKRW() {
        return isType("KRW");
    }

    public boolean isUncertainty() {
        return isType("n");
    }
}
