package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class SourceCrawlTMStatus
        extends DataDictionary<SourceCrawlTMStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String CRAWLING = "1";
    public static final String CRAWLED = "2";

    public SourceCrawlTMStatus() {
    }

    public SourceCrawlTMStatus(String typeid) {
        setId(typeid);
    }

    public SourceCrawlTMStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }

    public boolean isCrawling() {
        return isType("1");
    }

    public boolean isCrawled() {
        return isType("2");
    }
}
