package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CrawlFlag extends DataDictionary<CrawlFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOTSEARCH = "N";
    public static final String SEARCHING = "G";
    public static final String NOFOUND = "0";
    public static final String SUCCESS = "1";
    public static final String ERROR = "E";

    public CrawlFlag() {
    }

    public CrawlFlag(String typeid) {
        setId(typeid);
    }

    public CrawlFlag(Item item) {
        load(item);
    }


    public boolean isNotSearch() {
        return isType("N");
    }


    public boolean isSearching() {
        return isType("G");
    }


    public boolean isSuccess() {
        return isType("1");
    }


    public boolean isNoFound() {
        return isType("0");
    }


    public boolean isError() {
        return isType("E");
    }
}
