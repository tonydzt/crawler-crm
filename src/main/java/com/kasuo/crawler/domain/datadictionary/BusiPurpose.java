package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class BusiPurpose extends DataDictionary<BusiPurpose> {
    private static final long serialVersionUID = 1L;
    public static final String PURPOSE = "1";
    public static final String NO_PURPOSE = "2";

    public BusiPurpose() {
    }

    public BusiPurpose(String typeid) {
        setId(typeid);
    }

    public BusiPurpose(Item item) {
        load(item);
    }

    public boolean isPurpose() {
        return isType("1");
    }

    public boolean isNoPurpose() {
        return isType("2");
    }
}
