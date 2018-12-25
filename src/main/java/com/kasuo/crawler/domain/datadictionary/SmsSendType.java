package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SmsSendType
        extends DataDictionary<SmsSendType> {
    private static final long serialVersionUID = 1L;
    public static final String SEND_NOW = "1";
    public static final String SEND_LATER = "2";

    public SmsSendType() {
    }

    public SmsSendType(String typeid) {
        setId(typeid);
    }

    public SmsSendType(Item item) {
        load(item);
    }


    public boolean isSendNow() {
        return isType("1");
    }

    public boolean isSendLater() {
        return isType("2");
    }
}
