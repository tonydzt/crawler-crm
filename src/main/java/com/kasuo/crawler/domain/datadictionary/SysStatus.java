package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class SysStatus
        extends DataDictionary<SysStatus> {
    private static final long serialVersionUID = 1L;
    public static final String NORMAL = "0";
    public static final String CLEANING = "1";

    public SysStatus() {
    }

    public SysStatus(String typeid) {
        setId(typeid);
    }

    public SysStatus(Item item) {
        load(item);
    }


    public boolean isNormal() {
        return isType("0");
    }
}
