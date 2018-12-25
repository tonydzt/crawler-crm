package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class CrawlOrgFlag
        extends DataDictionary<CrawlOrgFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOCRAWL = "0";
    public static final String CRAWL = "1";

    public CrawlOrgFlag() {
    }

    public CrawlOrgFlag(String typeid) {
        setId(typeid);
    }

    public CrawlOrgFlag(Item item) {
        load(item);
    }


    public boolean isCrawl() {
        return isType("1");
    }
}
