package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class Gender extends DataDictionary<Gender> {
    private static final long serialVersionUID = 1L;
    public static final String UNKNOW = "0";
    public static final String MALE = "1";
    public static final String FEMALE = "2";

    public Gender() {
    }

    public Gender(String typeid) {
        setId(typeid);
    }

    public Gender(Item item) {
        load(item);
    }


    public boolean isMale() {
        return isType("1");
    }

    public boolean isFemale() {
        return isType("2");
    }

    public boolean isUnknow() {
        return isType("0");
    }
}
