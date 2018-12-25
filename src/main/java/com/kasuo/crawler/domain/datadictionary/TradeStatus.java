package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class TradeStatus
        extends DataDictionary<TradeStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String WAIT_INFO_CHECK = "3";
    public static final String INFO_CHECKED = "4";

    public TradeStatus() {
    }

    public TradeStatus(String typeid) {
        setId(typeid);
    }

    public TradeStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }


    public boolean isWaitInfoCheck() {
        return isType("3");
    }


    public boolean isInfoChecked() {
        return isType("4");
    }
}
