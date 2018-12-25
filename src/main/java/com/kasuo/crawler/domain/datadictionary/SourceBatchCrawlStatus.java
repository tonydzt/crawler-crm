package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceBatchCrawlStatus
        extends DataDictionary<SourceBatchCrawlStatus> {
    private static final long serialVersionUID = 1L;
    public static final String PAUSE = "0";
    public static final String NORMAL = "1";

    public SourceBatchCrawlStatus() {
    }

    public SourceBatchCrawlStatus(String typeid) {
        setId(typeid);
    }

    public SourceBatchCrawlStatus(Item item) {
        load(item);
    }


    public boolean isPause() {
        return isType("0");
    }

    public boolean isNormal() {
        return isType("1");
    }
}
