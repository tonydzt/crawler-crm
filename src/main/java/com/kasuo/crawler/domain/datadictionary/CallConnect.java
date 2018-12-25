package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class CallConnect
        extends DataDictionary<CallConnect> {
    private static final long serialVersionUID = 1L;
    public static final String CONNECTED = "1";
    public static final String NOT_CONNECTED = "0";

    public CallConnect() {
    }

    public CallConnect(String typeid) {
        setId(typeid);
    }

    public CallConnect(Item item) {
        load(item);
    }


    public boolean isConnected() {
        return isType("1");
    }
}
