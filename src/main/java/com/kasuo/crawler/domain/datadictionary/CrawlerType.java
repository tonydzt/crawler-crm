package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CrawlerType extends DataDictionary<CrawlerType> {
    private static final long serialVersionUID = 1L;
    public static final String QXB = "1";
    public static final String TYC = "2";
    public static final String QCC = "3";
    public static final String QCM = "4";

    public CrawlerType() {
    }

    public CrawlerType(String typeid) {
        setId(typeid);
    }

    public CrawlerType(Item item) {
        load(item);
    }


    public boolean isQXB() {
        return isType("1");
    }


    public boolean isTYC() {
        return isType("2");
    }


    public boolean isQCC() {
        return isType("3");
    }


    public boolean isQCM() {
        return isType("4");
    }
}
