package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceAnnounceErrFlag extends DataDictionary<TMSourceAnnounceErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String NO_FOUND_IN_LOCAL = "1";
    public static final String MULTI_CLS = "2";
    public static final String OTHER = "9";

    public TMSourceAnnounceErrFlag() {
    }

    public TMSourceAnnounceErrFlag(String typeid) {
        setId(typeid);
    }

    public TMSourceAnnounceErrFlag(Item item) {
        load(item);
    }


    public boolean isRight() {
        return isType("0");
    }

    public boolean isNoFoundInLocal() {
        return isType("1");
    }

    public boolean isMultiCls() {
        return isType("2");
    }

    public boolean isOther() {
        return isType("9");
    }
}
