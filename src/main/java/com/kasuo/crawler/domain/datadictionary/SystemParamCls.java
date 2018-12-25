package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SystemParamCls
        extends DataDictionary<SystemParamCls> {
    private static final long serialVersionUID = 1L;
    public static final String DYNAMIC = "1";
    public static final String STATIC = "2";

    public SystemParamCls() {
    }

    public SystemParamCls(String typeid) {
        setId(typeid);
    }

    public SystemParamCls(Item item) {
        load(item);
    }


    public boolean isDynamic() {
        return isType("1");
    }
}
