package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceBatchPriority extends DataDictionary<SourceBatchPriority> {
    private static final long serialVersionUID = 1L;
    public static final String LOW = "1";
    public static final String MIDDLE = "2";
    public static final String HIGH = "3";
    public static final String SPECIAL = "4";

    public SourceBatchPriority() {
    }

    public SourceBatchPriority(String typeid) {
        setId(typeid);
    }

    public SourceBatchPriority(Item item) {
        load(item);
    }


    public boolean isLow() {
        return isType("1");
    }

    public boolean isMiddle() {
        return isType("2");
    }

    public boolean isHigh() {
        return isType("3");
    }

    public boolean isSpecial() {
        return isType("4");
    }
}
