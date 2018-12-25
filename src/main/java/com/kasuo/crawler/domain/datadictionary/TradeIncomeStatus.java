package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TradeIncomeStatus
        extends DataDictionary<TradeIncomeStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String WAIT_INCOME_CONFIRM = "3";
    public static final String FIRST_INCOME_ARRIVED = "5";
    public static final String SUB_LAST_INCOME_ARRIVED = "7";
    public static final String ALL_INCOME_ARRIVED = "9";

    public TradeIncomeStatus() {
    }

    public TradeIncomeStatus(String typeid) {
        setId(typeid);
    }

    public TradeIncomeStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }


    public boolean isWaitIncomeConfirm() {
        return isType("3");
    }


    public boolean isFirstIncomeArrived() {
        return isType("5");
    }


    public boolean isSubLastIncomeArrived() {
        return isType("7");
    }


    public boolean isAllIncomeArrived() {
        return isType("9");
    }
}
