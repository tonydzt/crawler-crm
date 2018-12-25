package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class OwnerFlag
        extends DataDictionary<OwnerFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOOWNER = "0";
    public static final String OWNER = "1";

    public OwnerFlag() {
    }

    public OwnerFlag(String typeid) {
        setId(typeid);
    }

    public OwnerFlag(Item item) {
        load(item);
    }


    public boolean isOwner() {
        return isType("1");
    }
}
