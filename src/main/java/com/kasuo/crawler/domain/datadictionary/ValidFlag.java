package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ValidFlag extends DataDictionary<ValidFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOVALID = "0";
    public static final String VALID = "1";

    public ValidFlag() {
    }

    public ValidFlag(String typeid) {
        setId(typeid);
    }

    public ValidFlag(Item item) {
        load(item);
    }


    public boolean isValid() {
        return isType("1");
    }
}
