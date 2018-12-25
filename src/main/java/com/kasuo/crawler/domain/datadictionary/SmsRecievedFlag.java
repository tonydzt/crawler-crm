package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class SmsRecievedFlag
        extends DataDictionary<SmsRecievedFlag> {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "2";
    public static final String FAILURE = "1";
    public static final String NO_CONFIRM = "0";

    public SmsRecievedFlag() {
    }

    public SmsRecievedFlag(String typeid) {
        setId(typeid);
    }

    public SmsRecievedFlag(Item item) {
        load(item);
    }


    public boolean isSuccess() {
        return isType("2");
    }

    public boolean isFailure() {
        return isType("1");
    }

    public boolean isNoConfirm() {
        return isType("0");
    }
}
