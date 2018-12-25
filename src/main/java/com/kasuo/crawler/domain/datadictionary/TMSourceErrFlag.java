package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceErrFlag extends DataDictionary<TMSourceErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String BAITU_NODATA = "1";
    public static final String DISACCORD = "2";
    public static final String BAITU_NO_INIT = "3";
    public static final String OTHER = "9";

    public TMSourceErrFlag() {
    }

    public TMSourceErrFlag(String typeid) {
        setId(typeid);
    }

    public TMSourceErrFlag(Item item) {
        load(item);
    }


    public boolean isRight() {
        return isType("0");
    }

    public boolean isBaituNodata() {
        return isType("1");
    }

    public boolean isDisaccord() {
        return isType("2");
    }

    public boolean isNoInit() {
        return isType("3");
    }

    public boolean isOther() {
        return isType("9");
    }
}
