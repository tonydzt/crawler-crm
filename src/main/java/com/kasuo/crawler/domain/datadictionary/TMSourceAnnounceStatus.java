package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceAnnounceStatus extends DataDictionary<TMSourceAnnounceStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String CRAWLING = "1";
    public static final String CRAWLED = "2";
    public static final String PARSING = "3";
    public static final String PARSED = "4";
    public static final String VERIFING = "5";
    public static final String VERIFED = "6";
    public static final String PREPARING = "7";
    public static final String PREPARED = "8";

    public TMSourceAnnounceStatus() {
    }

    public TMSourceAnnounceStatus(String typeid) {
        setId(typeid);
    }

    public TMSourceAnnounceStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }

    public boolean isCrawling() {
        return isType("1");
    }

    public boolean isCrawled() {
        return isType("2");
    }

    public boolean isParsing() {
        return isType("3");
    }

    public boolean isParsed() {
        return isType("4");
    }

    public boolean isVerifing() {
        return isType("5");
    }

    public boolean isVerified() {
        return isType("6");
    }

    public boolean isPreparing() {
        return isType("7");
    }

    public boolean isPrepared() {
        return isType("8");
    }
}
