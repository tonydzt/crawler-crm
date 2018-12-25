package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class BusiModel extends DataDictionary<BusiModel> {
    private static final long serialVersionUID = 1L;
    public static final String NORMAL = "1";
    public static final String ASSURE = "2";
    public static final String RISK = "3";

    public BusiModel() {
    }

    public BusiModel(String typeid) {
        setId(typeid);
    }

    public BusiModel(Item item) {
        load(item);
    }


    public boolean isNormal() {
        return isType("1");
    }

    public boolean isAssure() {
        return isType("2");
    }

    public boolean isRisk() {
        return isType("3");
    }
}
