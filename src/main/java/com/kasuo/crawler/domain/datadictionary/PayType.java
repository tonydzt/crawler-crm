package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class PayType
        extends DataDictionary<PayType> {
    private static final long serialVersionUID = 1L;
    public static final String ALL = "1";
    public static final String STAGE = "2";

    public PayType() {
    }

    public PayType(String typeid) {
        setId(typeid);
    }

    public PayType(Item item) {
        load(item);
    }


    public boolean isAll() {
        return isType("1");
    }

    public boolean isStage() {
        return isType("2");
    }
}
