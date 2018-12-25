package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceCrawlBTErrFlag
        extends DataDictionary<SourceCrawlBTErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String OTHER = "9";

    public SourceCrawlBTErrFlag() {
    }

    public SourceCrawlBTErrFlag(String typeid) {
        setId(typeid);
    }

    public SourceCrawlBTErrFlag(Item item) {
        load(item);
    }


    public boolean isRight() {
        return isType("0");
    }

    public boolean isOther() {
        return isType("9");
    }
}
