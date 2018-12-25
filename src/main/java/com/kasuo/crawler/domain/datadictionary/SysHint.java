package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SysHint
        extends DataDictionary<SysHint> {
    private static final long serialVersionUID = 1L;
    public static final String NOHINT = "0";
    public static final String HINT = "1";

    public SysHint() {
    }

    public SysHint(String typeid) {
        setId(typeid);
    }

    public SysHint(Item item) {
        load(item);
    }


    public boolean hasHint() {
        return isType("1");
    }
}
