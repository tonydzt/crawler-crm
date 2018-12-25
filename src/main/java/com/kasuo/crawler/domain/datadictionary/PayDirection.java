package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class PayDirection
        extends DataDictionary<PayDirection> {
    private static final long serialVersionUID = 1L;
    public static final String INCOME = "1";
    public static final String EXPENSE = "2";

    public PayDirection() {
    }

    public PayDirection(String typeid) {
        setId(typeid);
    }

    public PayDirection(Item item) {
        load(item);
    }


    public boolean isIncome() {
        return isType("1");
    }
}
