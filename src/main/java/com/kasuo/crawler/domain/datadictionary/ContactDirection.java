package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class ContactDirection
        extends DataDictionary<ContactDirection> {
    private static final long serialVersionUID = 1L;
    public static final String CALL_IN = "2";
    public static final String CALL_OUT = "1";
    public static final String MANUAL_OUT = "3";

    public ContactDirection() {
    }

    public ContactDirection(String typeid) {
        setId(typeid);
    }

    public ContactDirection(Item item) {
        load(item);
    }


    public boolean isCallIn() {
        return isType("2");
    }

    public boolean isCallOut() {
        return isType("1");
    }

    public boolean isManualOut() {
        return isType("3");
    }
}
