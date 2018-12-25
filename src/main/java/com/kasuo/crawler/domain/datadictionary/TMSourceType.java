package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceType
        extends DataDictionary<TMSourceType> {
    private static final long serialVersionUID = 1L;
    public static final String GOV_INSIDE = "1";
    public static final String GOV_WEB = "2";
    public static final String BAITU = "3";
    public static final String MANUAL = "4";
    public static final String OTHER = "9";

    public TMSourceType() {
    }

    public TMSourceType(String typeid) {
        setId(typeid);
    }

    public TMSourceType(Item item) {
        load(item);
    }


    public boolean isGovInside() {
        return isType("1");
    }


    public boolean isGovWeb() {
        return isType("2");
    }


    public boolean isBaitu() {
        return isType("3");
    }


    public boolean isManual() {
        return isType("4");
    }


    public boolean isOther() {
        return isType("9");
    }
}
