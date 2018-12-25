package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class CRBusiType
        extends DataDictionary<CRBusiType> {
    private static final long serialVersionUID = 1L;
    public static final String PRODUCTION_REGISTER = "0";
    public static final String SOFTWARE_REGISTER = "1";
    public static final String OTHER = "n";

    public CRBusiType() {
    }

    public CRBusiType(String typeid) {
        setId(typeid);
    }

    public CRBusiType(Item item) {
        load(item);
    }


    public boolean isProductionRegister() {
        return isType("0");
    }

    public boolean isSoftwareRegister() {
        return isType("1");
    }

    public boolean isOther() {
        return isType("n");
    }
}
