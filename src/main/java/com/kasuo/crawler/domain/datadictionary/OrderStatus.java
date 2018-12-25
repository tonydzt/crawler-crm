package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class OrderStatus
        extends DataDictionary<OrderStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String WAIT_PAY_CONFIRM = "3";
    public static final String PAY_CONFIRMED = "5";

    public OrderStatus() {
    }

    public OrderStatus(String typeid) {
        setId(typeid);
    }

    public OrderStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }

    public boolean isWaitPayConfirm() {
        return isType("3");
    }

    public boolean isPayConfirmed() {
        return isType("5");
    }
}
