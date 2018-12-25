package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceCrawlTMErrFlag
        extends DataDictionary<SourceCrawlTMErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String OTHER = "9";

    public SourceCrawlTMErrFlag() {
    }

    public SourceCrawlTMErrFlag(String typeid) {
        setId(typeid);
    }

    public SourceCrawlTMErrFlag(Item item) {
        load(item);
    }


    public boolean isRight() {
        return isType("0");
    }

    public boolean isOther() {
        return isType("9");
    }
}
