package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMItemStandardFlag extends DataDictionary<TMItemStandardFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NICE = "1";
    public static final String CHINA = "2";
    public static final String NON_STANDARDIZED = "3";

    public TMItemStandardFlag() {
    }

    public TMItemStandardFlag(String typeid) {
        setId(typeid);
    }

    public TMItemStandardFlag(Item item) {
        load(item);
    }


    public boolean isNice() {
        return isType("1");
    }


    public boolean isChina() {
        return isType("2");
    }


    public boolean isNonStandardized() {
        return isType("3");
    }
}
