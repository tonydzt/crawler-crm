package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SuccessFlag
        extends DataDictionary<SuccessFlag> {
    private static final long serialVersionUID = 1L;
    public static final String FAILURE = "0";
    public static final String SUCCESS = "1";

    public SuccessFlag() {
    }

    public SuccessFlag(String typeid) {
        setId(typeid);
    }

    public SuccessFlag(Item item) {
        load(item);
    }


    public boolean isSuccess() {
        return isType("1");
    }
}
