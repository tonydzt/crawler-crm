package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class BusiType extends DataDictionary<BusiType> {
    private static final long serialVersionUID = 1L;
    public static final String CHITM = "10";
    public static final String WORLDTM = "20";
    public static final String COPYRIGHT = "30";
    public static final String PATENT = "40";

    public BusiType() {
    }

    public BusiType(String typeid) {
        setId(typeid);
    }

    public BusiType(Item item) {
        load(item);
    }


    public boolean isChiTM() {
        return isType("10");
    }

    public boolean isWorldTM() {
        return isType("20");
    }

    public boolean isCopyright() {
        return isType("30");
    }

    public boolean isPatent() {
        return isType("40");
    }
}
