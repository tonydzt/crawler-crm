package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class CustomerType
        extends DataDictionary<CustomerType> {
    private static final long serialVersionUID = 1L;
    public static final String PURPOSE_HIGH = "3";
    public static final String PURPOSE_NORMAL = "1";
    public static final String NO_PURPOSE = "0";

    public CustomerType() {
    }

    public CustomerType(String typeid) {
        setId(typeid);
    }

    public CustomerType(Item item) {
        load(item);
    }


    public boolean isPurposeHeight() {
        return isType("3");
    }


    public boolean isPurposeNormal() {
        return isType("1");
    }


    public boolean isNoPurpose() {
        return isType("0");
    }
}
