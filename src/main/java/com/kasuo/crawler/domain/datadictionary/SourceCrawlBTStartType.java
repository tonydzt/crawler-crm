package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceCrawlBTStartType
        extends DataDictionary<SourceCrawlBTStartType> {
    private static final long serialVersionUID = 1L;
    public static final String AUTO = "0";
    public static final String MANUAL = "1";

    public SourceCrawlBTStartType() {
    }

    public SourceCrawlBTStartType(String typeid) {
        setId(typeid);
    }

    public SourceCrawlBTStartType(Item item) {
        load(item);
    }


    public boolean isAuto() {
        return isType("0");
    }

    public boolean isManual() {
        return isType("1");
    }
}
