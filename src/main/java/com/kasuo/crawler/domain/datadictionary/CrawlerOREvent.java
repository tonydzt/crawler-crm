package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CrawlerOREvent extends DataDictionary<CrawlerOREvent> {
    private static final long serialVersionUID = 1L;
    public static final String VERIFYCODE = "1";
    public static final String LOGIN = "2";
    public static final String DECLINE = "3";
    public static final String ESERVER = "4";

    public CrawlerOREvent() {
    }

    public CrawlerOREvent(String typeid) {
        setId(typeid);
    }

    public CrawlerOREvent(Item item) {
        load(item);
    }


    public static final String ENETWORK = "5";


    public static final String ETIMEOUT = "6";


    public static final String EDOM = "7";


    public static final String ESTOP = "8";


    public static final String CRASH = "9";


    public boolean isVerifyCode() {
        return isType("1");
    }

    public boolean isLogin() {
        return isType("2");
    }

    public boolean Decline() {
        return isType("3");
    }

    public boolean isCrash() {
        return isType("9");
    }


    public boolean isServerError() {
        return isType("4");
    }


    public boolean isNetworkError() {
        return isType("5");
    }


    public boolean isTimeoutError() {
        return isType("6");
    }


    public boolean isDOMError() {
        return isType("7");
    }


    public boolean iStopError() {
        return isType("8");
    }
}
