package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class UserStatus extends DataDictionary<UserStatus> {
    private static final long serialVersionUID = 1L;
    public static final String NORMAL = "1";
    public static final String OFFWORK = "2";
    public static final String DIMISSION = "0";
    public static final String SYSTEM = "9";

    public UserStatus() {
    }

    public UserStatus(String typeid) {
        setId(typeid);
    }

    public UserStatus(Item item) {
        load(item);
    }


    public boolean isNormal() {
        return isType("1");
    }


    public boolean isOffwork() {
        return isType("2");
    }


    public boolean isDimission() {
        return isType("0");
    }


    public boolean isSystem() {
        return isType("9");
    }
}
