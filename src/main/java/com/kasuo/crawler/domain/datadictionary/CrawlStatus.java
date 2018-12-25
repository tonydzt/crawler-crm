package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CrawlStatus
        extends DataDictionary<CrawlStatus> {
    private static final long serialVersionUID = 1L;
    public static final String NOCRAWL = "0";
    public static final String CRAWLING = "1";

    public CrawlStatus() {
    }

    public CrawlStatus(String typeid) {
        setId(typeid);
    }

    public CrawlStatus(Item item) {
        load(item);
    }


    public boolean isCrawling() {
        return isType("1");
    }
}
